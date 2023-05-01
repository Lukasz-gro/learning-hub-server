package com.example.learninghub.test;

import com.example.learninghub.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

    List<Test> findTestsByProblem(Problem problem);
}
