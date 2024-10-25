package sarik.dev.qrbot;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sarik.dev.qrbot.entity.User;
import sarik.dev.qrbot.entity.UserHistory;
import sarik.dev.qrbot.service.UserHistoryService;
import sarik.dev.qrbot.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MyTelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final UserHistoryService userHistoryService;

    // QR kod yaratish uchun flag
    private boolean waitingForQrData = false;

    public MyTelegramBot(UserService userService, UserHistoryService userHistoryService) {
        this.userService = userService;
        this.userHistoryService = userHistoryService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            // Foydalanuvchini bazaga qo'shish yoki tekshirish
            Optional<User> userOptional = userService.getUserById(chatId);
            if (userOptional.isEmpty()) {
                User newUser = new User();
                newUser.setChatId(chatId);
                newUser.setRegistrationDate(LocalDateTime.now());
                userService.addUser(newUser);
                sendMessage(chatId, "Botga hush kelibsiz!");
            }

            if (message.hasText()) {
                String messageText = message.getText();

                if (waitingForQrData) {
                    // Foydalanuvchi ma'lumotni yuborgandan keyin QR yaratish
                    createQrCode(chatId, messageText);
                    waitingForQrData = false;
                } else if (messageText.equals("/start")) {
                    sendMessage(chatId, "Botga hush kelibsiz! QR kod yaratish yoki skan qilish mumkin.");
                } else if (messageText.equals("/help")) {
                    sendMessage(chatId, "Yordam buyruqlari: \n/qrcode_yarat - QR kod yaratish\n/qrcode_skan - QR kod skan qilish.");
                } else if (messageText.equals("/qrcode_yarat")) {
                    waitingForQrData = true;
                    sendMessage(chatId, "QR kod yaratish uchun ma'lumot kiriting (matn yoki URL).");
                } else if (messageText.equals("/qrcode_skan")) {
                    sendMessage(chatId, "Iltimos, QR kodni yuboring.");
                } else {
                    sendMessage(chatId, "Noma'lum buyruq. Yordam uchun /help ni kiriting.");
                }
            } else if (message.hasPhoto()) {
                // QR kod skan qilish uchun yuborilgan rasmni olish
                try {
                    String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();
                    GetFile getFile = new GetFile(fileId);
                    org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(getFile);

                    // Telegramdan rasmni yuklab olish
                    String fileUrl = "https://api.telegram.org/file/bot" + getBotToken() + "/" + telegramFile.getFilePath();
                    InputStream inputStream = new URL(fileUrl).openStream();
                    BufferedImage bufferedImage = ImageIO.read(inputStream);

                    // QR kodni dekodlash
                    String qrData = decodeQr(bufferedImage);
                    sendMessage(chatId, "QR Data: " + qrData);

                    // Foydalanuvchi tarixiga yozish
                    User user = userService.getUserById(chatId).orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
                    UserHistory userHistory = new UserHistory();
                    userHistory.setUser(user);
                    userHistory.setAction("SCAN");
                    userHistory.setActionTime(LocalDateTime.now());
                    userHistoryService.addUserHistory(userHistory);

                } catch (Exception e) {
                    e.printStackTrace();
                    sendMessage(chatId, "QR kodni o'qishda xatolik yuz berdi: " + e.getMessage());
                }
            }
        }
    }

    // QR kodni dekodlash uchun metod
    private String decodeQr(BufferedImage bufferedImage) throws NotFoundException {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }

    // QR kod yaratish logikasi
    private void createQrCode(Long chatId, String qrData) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;

        try {
            // QR kod yaratish
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // QR kodni yuborish
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId.toString());
            sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(pngData), "QR Code"));
            execute(sendPhoto);

            sendMessage(chatId, "QR kod muvaffaqiyatli yaratildi.");

        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(chatId, "QR kod yaratishda xatolik: " + e.getMessage());
        }
    }

    // Foydalanuvchiga xabar yuborish uchun metod
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "iqrfast_bot";  // Bot username
    }

    @Override
    public String getBotToken() {
        return "7700802092:AAGdcqf4r1iegRyrtNVJcQuyZyfetm8azOU";  // Bot token
    }
}
