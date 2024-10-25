package sarik.dev.qrbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sarik.dev.qrbot.entity.UserHistory;
import sarik.dev.qrbot.service.UserHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userhistory")
public class UserHistoryController {

    private final UserHistoryService userHistoryService;

    public UserHistoryController(UserHistoryService userHistoryService) {
        this.userHistoryService = userHistoryService;
    }

    // Foydalanuvchi tarixini ID bo'yicha olish
    @GetMapping("/{id}")
    public ResponseEntity<UserHistory> getUserHistoryById(@PathVariable Long id) {
        Optional<UserHistory> userHistory = userHistoryService.getUserHistoryById(id);
        return userHistory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Barcha foydalanuvchi tarixlarini olish
    @GetMapping
    public ResponseEntity<List<UserHistory>> getAllUserHistories() {
        List<UserHistory> userHistories = userHistoryService.getAllUserHistories();
        return ResponseEntity.ok(userHistories);
    }

    // Yangi foydalanuvchi tarixi qo'shish
    @PostMapping
    public ResponseEntity<UserHistory> addUserHistory(@RequestBody UserHistory userHistory) {
        UserHistory newUserHistory = userHistoryService.addUserHistory(userHistory);
        return ResponseEntity.ok(newUserHistory);
    }
}
