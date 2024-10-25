package sarik.dev.qrbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sarik.dev.qrbot.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Admin'lar uchun kerakli query'lar bu yerda yoziladi
}
