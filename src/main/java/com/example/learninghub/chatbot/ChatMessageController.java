package com.example.learninghub.chatbot;

import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/v1/auth/chat-bot/{userId}/{problemId}/history")
    public List<ChatMessage> getMessagesHistory(@PathVariable("userId") Integer userId,
                                                @PathVariable("problemId") Integer problemId) {
        //TODO check token
        return chatMessageService.getMessagesHistory(userId, problemId);
    }

    @PostMapping("v1/auth/chat-bot/add-message")
    public void addChatMessage(@RequestBody AddMessageRequest request) {
        //TODO check token
        chatMessageService.addChatMessage(request);
    }
}
