package com.example.learninghub.submit;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemRepository;
import com.example.learninghub.problem.ProblemService;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserRepository;
import com.example.learninghub.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SubmitService {

    private final SubmitRepository submitRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final AtomicInteger maxSubmitId = new AtomicInteger();

    public Submit getSubmit(Integer id) {
        return submitRepository.findById(id).orElseThrow();
    }

    public Integer addSubmit(String code, Status status, Integer problemId, String username) {
        Integer submitId = maxSubmitId.incrementAndGet();
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();
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
        User requestUser = userRepository.findByUsername(username).orElseThrow();
        return submit.getUser().equals(requestUser);
    }

    public boolean authenticate(HttpServletRequest request, String username) {
        Principal principal = request.getUserPrincipal();
        String requestUser = principal.getName();
        return username.equals(requestUser);
    }

    public List<Submit> getSubmitHistory(String username, Integer problemId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getSubmits().stream()
                .filter(submit -> submit.getProblem().getId().equals(problemId))
                .sorted((x, y) -> { // sort descending
                    if (x.getDate().before(y.getDate())) {
                        return 1;
                    } else if (x.getDate().after(y.getDate())) {
                        return -1;
                    }
                    return 0;
        }).toList();
    }
}
