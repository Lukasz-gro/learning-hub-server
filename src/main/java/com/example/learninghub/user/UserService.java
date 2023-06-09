package com.example.learninghub.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
