package com.example.learninghub;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/* Just for testing
	@Bean
	CommandLineRunner commandLineRunner(ProblemRepository problemRepository) {
		return args -> {
			System.out.println(problemRepository.save(new Problem("test")));
			Optional<Problem> x = problemRepository.findById(1);
			x.ifPresent(problem -> System.out.println(problem.getName()));
		};
	}
	*/

}
