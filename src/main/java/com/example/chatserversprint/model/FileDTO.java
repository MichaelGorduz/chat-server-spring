package com.example.chatserversprint.model;

import lombok.Data;

@Data
public class FileDTO {
    private String fileName;
    private long fileSize;
    private String fileUrl;
    private byte[] fileData;
}
