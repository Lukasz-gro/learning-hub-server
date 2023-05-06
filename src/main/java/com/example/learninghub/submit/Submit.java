package com.example.learninghub.submit;

import com.example.learninghub.problem.Problem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Submit")
@Table(name = "submit")
public class Submit {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    @ToString.Exclude private Problem problem;

}
