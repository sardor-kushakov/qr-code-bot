package sarik.dev.qrbot.service;

import org.springframework.stereotype.Service;
import sarik.dev.qrbot.entity.Message;
import sarik.dev.qrbot.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Xabarni ID bo'yicha topish
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    // Barcha xabarlarni olish
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Yangi xabar qo'shish
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    // Xabarni o'chirish
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
