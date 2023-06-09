package com.example.learninghub.chatbot;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ProblemService problemService;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserService userService, ProblemService problemService) {
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.problemService = problemService;
    }


    public List<ChatMessage> getMessagesHistory(String username, Integer problemId) {
        User user = userService.getUser(username);
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

    public void addChatMessage(AddMessageRequest request) {
        User user = userService.getUser(request.getUsername());
        Problem problem = problemService.getProblem(request.getProblemId());
        chatMessageRepository.save(new ChatMessage(request.getMessage(), new Date(new java.util.Date().getTime()),
                request.getIsUser(), user, problem));
    }
}
