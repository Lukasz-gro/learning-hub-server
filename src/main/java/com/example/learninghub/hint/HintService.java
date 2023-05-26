package com.example.learninghub.hint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HintService {

    private final HintRepository hintRepository;

    @Autowired
    public HintService(HintRepository hintRepository) {
        this.hintRepository = hintRepository;
    }
}
