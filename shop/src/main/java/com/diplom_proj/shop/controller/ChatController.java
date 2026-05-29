package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Куда отпровдя.т собщения /app/chat.send
    @MessageMapping("/chat.send")
    // Отправка на канал: /topic/public
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessage) {
        return chatMessage;
    }
}
