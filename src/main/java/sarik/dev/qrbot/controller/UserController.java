package sarik.dev.qrbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sarik.dev.qrbot.entity.User;
import sarik.dev.qrbot.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Foydalanuvchilarni olish uchun metod
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Foydalanuvchini chatId orqali olish uchun metod
    @GetMapping("/{chatId}")
    public ResponseEntity<User> getUserByChatId(@PathVariable Long chatId) {
        Optional<User> user = userService.getUserById(chatId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Foydalanuvchini qo'shish uchun metod
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli qo'shildi.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Foydalanuvchi allaqachon mavjud.");
        }
    }

    // Foydalanuvchini yangilash uchun metod
    @PutMapping("/{chatId}")
    public ResponseEntity<String> updateUser(@PathVariable Long chatId, @RequestBody User updatedUser) {
        Optional<User> user = userService.getUserById(chatId);
        if (user.isPresent()) {
            userService.updateUser(chatId, updatedUser);
            return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli yangilandi.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Foydalanuvchini o'chirish uchun metod
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long chatId) {
        Optional<User> user = userService.getUserById(chatId);
        if (user.isPresent()) {
            userService.deleteUser(chatId);
            return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli o'chirildi.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
