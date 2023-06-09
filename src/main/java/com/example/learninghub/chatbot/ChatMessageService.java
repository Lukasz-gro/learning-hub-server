package com.example.learninghub.chatbot;

import com.example.learninghub.user.User;
import com.example.learninghub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }


    public List<ChatMessage> getMessagesHistory(Integer userId, Integer problemId) {
        User user = userService.getUser(userId);
        Set<ChatMessage> allMessages = user.getChatMessages();
        return allMessages.stream()
                .filter(chatMessage -> chatMessage.getProblem().getId().equals(problemId))
                .sorted((x, y) -> {
                    if (x.getDate().before(y.getDate())) {
                        return -1;
                    } else if (x.getDate().after(y.getDate())) {
                        return 1;
                    }
                    return 0;
                }).collect(Collectors.toList());
    }
}
