package com.example.chatserversprint.service;

import com.example.chatserversprint.model.Message;
import com.example.chatserversprint.model.MessageDTO;
import com.example.chatserversprint.model.User;
import com.example.chatserversprint.repository.MessageRepository;
import com.example.chatserversprint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void add(MessageDTO messageDTO) {
        Message message = Message.fromDTO(messageDTO);
        messageRepository.save(message);
    }
    @Transactional(readOnly = true)
    public List<MessageDTO> get(long id) {
        List<Message> messages = messageRepository.findNew(id); // orig was var messages =
        List<MessageDTO> result = new ArrayList<>(); // original was var result = new ArrayList<>(MessageDTO);

        messages.forEach(message -> result.add(message.toDTO()));
        return result;
    }

    // Implement the getBase64FileData method to retrieve the Base64 file data.
    public String getBase64FileData(Long messageId) {
        // Retrieve the message from your data source by messageId.
        Message message = messageRepository.findById(messageId).orElse(null);

        if (message != null) {
            String base64FileData = message.getFileData();

            return base64FileData;
        } else {
            return null; // Message not found
        }
    }
    // Implement the messageService.create method to create a message with specific recipients.
    public void create(MessageDTO messageDTO) {
        // Assuming MessageDTO contains message text and recipient IDs
        String text = messageDTO.getText();
        List<Long> recipientIds = messageDTO.getRecipients();

        // Fetch the user entities based on recipient IDs
        List<User> recipients = userRepository.findAllById(recipientIds);

        // Create a new Message entity
        Message message = new Message();
        message.setText(text);
        message.setRecipients(recipients);

        // Save the message in the database
        messageRepository.save(message);
    }


}
