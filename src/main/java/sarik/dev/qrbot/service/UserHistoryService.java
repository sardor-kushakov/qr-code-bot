package sarik.dev.qrbot.service;

import org.springframework.stereotype.Service;
import sarik.dev.qrbot.entity.UserHistory;
import sarik.dev.qrbot.repository.UserHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    // Foydalanuvchi tarixini ID bo'yicha topish
    public Optional<UserHistory> getUserHistoryById(Long id) {
        return userHistoryRepository.findById(id);
    }

    // Barcha foydalanuvchi tarixlarini olish
    public List<UserHistory> getAllUserHistories() {
        return userHistoryRepository.findAll();
    }

    // Yangi tarix qo'shish
    public UserHistory addUserHistory(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }
}
