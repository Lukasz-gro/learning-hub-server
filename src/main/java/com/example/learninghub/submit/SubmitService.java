package com.example.learninghub.submit;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SubmitService {

    private final SubmitRepository submitRepository;
    private final ProblemService problemService;
    private final UserService userService;
    private final AtomicInteger maxSubmitId = new AtomicInteger();

    @Autowired
    public SubmitService(SubmitRepository submitRepository, ProblemService problemService, UserService userService) {
        this.submitRepository = submitRepository;
        this.problemService = problemService;
        this.userService = userService;
        maxSubmitId.set(0);
        submitRepository.findTopByOrderByIdDesc().ifPresent(submit -> maxSubmitId.set(submit.getId()));
    }

    public Submit getSubmit(Integer id) {
        return submitRepository.findById(id).orElseThrow();
    }

    public Integer addSubmit(String code, Status status, Integer problemId, Integer userId) {
        Integer submitId = maxSubmitId.incrementAndGet();
        Problem problem = problemService.getProblem(problemId);
        User user = userService.getUser(userId);
        submitRepository.save(new Submit(submitId, code, "", "", status,
                new Timestamp(System.currentTimeMillis()), problem, user));
        return submitId;
    }

    public void updateSubmit(Integer submitId, Status newStatus) {
        if (!submitRepository.existsById(submitId)) {
            throw new NoSuchElementException("No submit with id: " + submitId);
        }
        submitRepository.updateSubmitStatus(submitId, newStatus);
    }

    public void updateErrorMessage(Integer submitId, String errorMessage) {
        if (!submitRepository.existsById(submitId)) {
            throw new NoSuchElementException("No submit with id: " + submitId);
        }
        submitRepository.updateSubmitErrorMessage(submitId, errorMessage);
    }

    public void updateOutput(Integer submitId, String output) {
        if (!submitRepository.existsById(submitId)) {
            throw new NoSuchElementException("No submit with id: " + submitId);
        }
        submitRepository.updateSubmitOutput(submitId, output);
    }

    public boolean authenticate(HttpServletRequest request, Integer submitId) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Submit submit = getSubmit(submitId);
        User requestUser = userService.getUser(username);
        return submit.getUser().equals(requestUser);
    }

    public boolean authenticate(HttpServletRequest request, String username) {
        Principal principal = request.getUserPrincipal();
        String requestUser = principal.getName();
        return username.equals(requestUser);
    }

    public List<Submit> getSubmitHistory(String username, Integer problemId) {
        User user = userService.getUser(username);
        return user.getSubmits().stream()
                .filter(submit -> submit.getProblem().getId().equals(problemId))
                .sorted((x, y) -> {
                    if (x.getDate().before(y.getDate())) {
                        return -1;
                    } else if (x.getDate().after(y.getDate())) {
                        return 1;
                    }
                    return 0;
        }).toList();
    }
}
