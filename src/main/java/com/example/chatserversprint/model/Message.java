package com.example.chatserversprint.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "msg_to")
    private String to;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "msg_date")
    private Date date;

    @Column(name = "msg_from", nullable = false)
    private String from;

    @Column(name = "msg_text", nullable = false)
    private String text;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_data")
    private String fileData;

    @ManyToMany // Define the relationship as many-to-many
    @JoinTable(
            name = "message_recipients",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private List<User> recipients; // Add a list of recipients

    // Getter and setter for recipients
    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }


    public static Message fromDTO(MessageDTO dto) {
        Message result = new Message(); // Can be mistake here, original was var result =
        result.setId(dto.getId());
        result.setTo(dto.getTo());
        result.setDate(dto.getDate());
        result.setFrom(dto.getFrom());
        result.setText(dto.getText());
        result.setFileName(dto.getFileName());
        result.setFileData(dto.getFileData());
        return result;
    }

    public MessageDTO toDTO() {
        MessageDTO result = new MessageDTO(); // Can be mistake here, original was var result =
        result.setId(id);
        result.setTo(to);
        result.setDate(date);
        result.setFrom(from);
        result.setText(text);
        result.setFileName(fileName);
        result.setFileData(fileData);
        return result;
    }
}
