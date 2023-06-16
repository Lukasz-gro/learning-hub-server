package com.example.learninghub.chatbot;

import com.example.learninghub.course.Course;
import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ProblemService problemService;

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
        chatMessageRepository.save(new ChatMessage(request.getMessage(), new Timestamp(System.currentTimeMillis()),
                request.getIsUser(), request.getMessageType(), user, problem));
    }

    public void deleteLastMessage(String username, Integer problemId) {
        Integer lastMessageId = userService.getUser(username)
                .getChatMessages().stream()
                .filter(chatMessage -> chatMessage.getProblem().getId().equals(problemId))
                .sorted((x, y) -> {
                    if (x.getDate().before(y.getDate())) {
                        return 1;
                    } else if (x.getDate().after(y.getDate())) {
                        return -1;
                    }
                    return 0;
                }).toList().get(0).getId();
        chatMessageRepository.deleteById(lastMessageId);
    }

    public boolean authenticate(HttpServletRequest request, String username) {
        Principal principal = request.getUserPrincipal();
        String requestUser = principal.getName();
        return username.equals(requestUser);
    }

    public boolean authenticate(HttpServletRequest request, String username, Integer problemId) {
        Principal principal = request.getUserPrincipal();
        String requestUser = principal.getName();
        if (!username.equals(requestUser)) {
            return false;
        }
        User user = userService.getUser(username);
        Problem problem = problemService.getProblem(problemId);
        for (Course c : user.getCourses()) {
            if (c.getProblems().stream().map(CourseProblem::getProblem).toList().contains(problem)) {
                return true;
            }
        }
        return false;
    }
}
