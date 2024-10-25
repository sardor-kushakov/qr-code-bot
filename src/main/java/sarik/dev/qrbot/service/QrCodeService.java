package sarik.dev.qrbot.service;

import org.springframework.stereotype.Service;
import sarik.dev.qrbot.entity.QrCode;
import sarik.dev.qrbot.repository.QrCodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    public QrCodeService(QrCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    // QR kodni ID bo'yicha topish
    public Optional<QrCode> getQrCodeById(Long id) {
        return qrCodeRepository.findById(id);
    }

    // Barcha QR kodlarni olish
    public List<QrCode> getAllQrCodes() {
        return qrCodeRepository.findAll();
    }

    // Yangi QR kod qo'shish
    public QrCode addQrCode(QrCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

    // QR kodni o'chirish
    public void deleteQrCode(Long id) {
        qrCodeRepository.deleteById(id);
    }
}
