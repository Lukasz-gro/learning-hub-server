package com.example.learninghub.chatbot;

import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/v1/chat-bot/{userId}/{problemId}/history")
    public List<ChatMessage> getMessagesHistory(@PathVariable("userId") Integer userId,
                                                @PathVariable("problemId") Integer problemId) {
        //TODO check token
        return chatMessageService.getMessagesHistory(userId, problemId);
    }
}
