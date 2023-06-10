package com.example.learninghub.tag;

import com.example.learninghub.problem.Problem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@Entity(name = "Tag")
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Problem> problems;

}
