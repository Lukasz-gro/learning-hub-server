package com.example.learninghub.chatbot;

import com.example.learninghub.problem.Problem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/v1/chat-bot/{username}/{problemId}/history")
    public List<ChatMessage> getMessagesHistory(@PathVariable("username") String username,
                                                @PathVariable("problemId") Integer problemId,
                                                HttpServletRequest request) {
        if (chatMessageService.authenticate(request, username)) {
            return chatMessageService.getMessagesHistory(username, problemId);
        }
        return null;
    }

    @PostMapping("/v1/chat-bot/add-message")
    public void addChatMessage(@RequestBody AddMessageRequest addMessageRequest,
                               HttpServletRequest request) {
        if (chatMessageService.authenticate(request, addMessageRequest.getUsername())) {
            chatMessageService.addChatMessage(addMessageRequest);
        }
    }

    @DeleteMapping("/v1/chat-bot/{username}/{problemId}/delete-last-message")
    public void deleteLastMessage(@PathVariable("username") String username,
                                  @PathVariable("problemId") Integer problemId,
                                  HttpServletRequest request) {
        if (chatMessageService.authenticate(request, username, problemId)) {
            chatMessageService.deleteLastMessage(username, problemId);
        }
    }
}
