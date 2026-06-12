package com.diplom_proj.shop.controller;

import com.diplom_proj.shop.dto.ChatMessageDTO;
import com.diplom_proj.shop.services.RedisChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    private final RedisChatService redisChatService;

    public ChatController(RedisChatService redisChatService) {
        this.redisChatService = redisChatService;
    }

    // Куда отпровдя.т собщения /app/chat.send
    @MessageMapping("/chat.send")
    // Отправка на канал: /topic/public
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessage) {

        // Сохранение истории в Redis
        redisChatService.saveMessage(chatMessage);
        return chatMessage;
    }

//     Передача истории чата по REST API
    @GetMapping("/chat/history")
    public List<ChatMessageDTO> getChatHistory() {
        System.out.println("CHAT___CAGTS");
        return redisChatService.getChatHistory();
    }
}
