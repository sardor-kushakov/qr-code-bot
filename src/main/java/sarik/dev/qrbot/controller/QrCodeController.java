package sarik.dev.qrbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sarik.dev.qrbot.entity.QrCode;
import sarik.dev.qrbot.service.QrCodeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    // QR kodni ID bo'yicha olish
    @GetMapping("/{id}")
    public ResponseEntity<QrCode> getQrCodeById(@PathVariable Long id) {
        Optional<QrCode> qrCode = qrCodeService.getQrCodeById(id);
        return qrCode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Barcha QR kodlarni olish
    @GetMapping
    public ResponseEntity<List<QrCode>> getAllQrCodes() {
        List<QrCode> qrCodes = qrCodeService.getAllQrCodes();
        return ResponseEntity.ok(qrCodes);
    }

    // Yangi QR kod yaratish
    @PostMapping
    public ResponseEntity<QrCode> addQrCode(@RequestBody QrCode qrCode) {
        QrCode newQrCode = qrCodeService.addQrCode(qrCode);
        return ResponseEntity.ok(newQrCode);
    }

    // QR kodni o'chirish
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQrCode(@PathVariable Long id) {
        qrCodeService.deleteQrCode(id);
        return ResponseEntity.noContent().build();
    }
}
