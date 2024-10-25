package sarik.dev.qrbot.service;

import org.springframework.stereotype.Service;
import sarik.dev.qrbot.entity.Admin;
import sarik.dev.qrbot.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Adminni ID bo'yicha topish
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Barcha adminlarni olish
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Yangi admin qo'shish
    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Adminni o'chirish
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
