package com.diplom_proj.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ChatMessageDTO {
    private String sender;
    private String content;
    private String timestamp;

    public ChatMessageDTO() {
        this.timestamp = LocalDateTime.now().toString();
    }
}
