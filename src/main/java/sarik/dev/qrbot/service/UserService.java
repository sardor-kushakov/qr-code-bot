package sarik.dev.qrbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sarik.dev.qrbot.entity.User;
import sarik.dev.qrbot.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    public void addUser(User user) {
        try {
            Optional<User> existingUser = userRepository.findByChatId(user.getChatId());
            if (existingUser.isPresent()) {
                throw new IllegalStateException("Foydalanuvchi allaqachon mavjud.");
            }
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Foydalanuvchi qo'shishda xatolik: " + e.getMessage());
            throw new IllegalStateException("Foydalanuvchi allaqachon mavjud.", e);
        }
    }

    public void updateUser(Long chatId, User updatedUser) {
        Optional<User> user = userRepository.findByChatId(chatId);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setQrGenerateCount(updatedUser.getQrGenerateCount());
            existingUser.setQrScanCount(updatedUser.getQrScanCount());
            userRepository.save(existingUser);
        } else {
            throw new IllegalStateException("Foydalanuvchi topilmadi.");
        }
    }

    public void deleteUser(Long chatId) {
        Optional<User> user = userRepository.findByChatId(chatId);
        user.ifPresent(userRepository::delete);
    }
}
