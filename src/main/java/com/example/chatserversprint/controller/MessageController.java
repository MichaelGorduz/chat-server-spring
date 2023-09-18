package com.example.chatserversprint.controller;

import com.example.chatserversprint.model.FileDTO;
import com.example.chatserversprint.model.MessageDTO;
import com.example.chatserversprint.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("add")
    public ResponseEntity<Void> add(@RequestBody MessageDTO messageDTO) {
        messageService.add(messageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get")
    public List<MessageDTO> get(@RequestParam(required = false, defaultValue = "0") Long from) {
        return messageService.get(from);
    }

    @GetMapping("file")
    public ResponseEntity<FileDTO> file(@RequestParam Long messageId) {
        // Отримайте Base64-рядок файлу з бази даних на основі messageId
        String base64FileData = messageService.getBase64FileData(messageId);

        if (base64FileData != null) {
            // Декодуйте Base64 в byte[]
            byte[] fileData = Base64.getDecoder().decode(base64FileData);

            // Створіть об'єкт FileDTO та встановіть дані файлу
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName("example.pdf");
            fileDTO.setFileData(fileData);

            return new ResponseEntity<>(fileDTO, HttpStatus.OK);
        } else {
            // Обробка, якщо файл не знайдено
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody MessageDTO messageDTO) {
        // Виконайте логіку для створення повідомлення з конкретними адресатами
        messageService.create(messageDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("test")
    public String test() {
        return "it works!";
    }
}
