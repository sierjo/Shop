//package com.diplom_proj.shop.services;
//
//import com.diplom_proj.shop.dto.ChatMessageDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class RedisChatService {
//    private final RedisTemplate<String, String> redisTemplate;
//
//
//    // Ключ, по которому будет лежать список сообщений в Redis
//    private static final String CHAT_KEY = "chat:history:warehouse";
//
//    //    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    public RedisChatService(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    // Сохранение сообщения
//    public void saveMessage(ChatMessageDTO message) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper(); // Для конверации Java Object <-> JSON
//
//            Map<String, Object> messageMap = new HashMap<>();
//            messageMap.put("sender", message.getSender());
//            messageMap.put("content", message.getContent());
//            messageMap.put("timestamp", message.getTimestamp());
//            // Превращаем объект в JSON-строку
//            String jsonMessage = objectMapper.writeValueAsString(messageMap);
//
//            // Добавление сообщение в конец списка
//            redisTemplate.opsForList().rightPush(CHAT_KEY, jsonMessage);
//
//            // Восстанаыливаются только последние 50 сообщений (индексы от -50 до -1)
//
//            redisTemplate.opsForList().rightPush(CHAT_KEY, jsonMessage);
//            redisTemplate.expire(CHAT_KEY, 50, TimeUnit.MINUTES);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Получение истории
//    public List<ChatMessageDTO> getChatHistory() {
//        ObjectMapper objectMapper = new ObjectMapper(); // Для конвертации Java Object <-> JSON
//        //Получение элементо из списка (от 0 до конца списка)
//        List<String> jsonMessages = redisTemplate.opsForList().range(CHAT_KEY, 0, -1);
//        List<ChatMessageDTO> history = new ArrayList<>();
//
//        if (jsonMessages != null) {
//            for (String json : jsonMessages) {
//                try {
//                    // Конвертация JSON обратно в Java-объект
////                    ChatMessageDTO message = objectMapper.readValue(json, ChatMessageDTO.class);
//                    ChatMessageDTO message = new ChatMessageDTO();
//                    history.add(message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return history;
//    }
//}





package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.ChatMessageDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisChatService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper; // Для конвертации Java Object <-> JSON

    // Ключ  по которому будет лежать список сообщений в Redis
    private static final String CHAT_KEY = "chat:history:warehouse";

    public RedisChatService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // Сохранение сообщения
    public void saveMessage(ChatMessageDTO message) {
        try {
            // Превращаем объект в JSON-строку
            String jsonMessage = objectMapper.writeValueAsString(message);

            // Помещает сообщения в список с конца
            redisTemplate.opsForList().rightPush(CHAT_KEY, jsonMessage);

            // восстанаыливаются только последние 50 сообщений (индексы от -50 до -1)
            redisTemplate.opsForList().trim(CHAT_KEY, -50, -1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Получение истории
    public List<ChatMessageDTO> getChatHistory() {
        // Получение элементов из списка (от 0 до конца)
        List<String> jsonMessages = redisTemplate.opsForList().range(CHAT_KEY, 0, -1);
        List<ChatMessageDTO> history = new ArrayList<>();

        if (jsonMessages != null) {
            for (String json : jsonMessages) {
                try {
                    // JSON обратно в Java-объект
                    ChatMessageDTO message = objectMapper.readValue(json, ChatMessageDTO.class);
                    history.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return history;
    }
}