package com.example.learninghub.submit;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmitRepository extends JpaRepository<Submit, Integer> {

    Optional<Submit> findTopByOrderByIdDesc();

    @Modifying
    @Transactional
    @Query("UPDATE Submit s set s.status = :new_status where s.id = :id")
    void updateSubmitStatus(@Param("id") Integer id, @Param("new_status") Status newStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Submit s set s.error = :error where s.id = :id")
    void updateSubmitErrorMessage(@Param("id") Integer id, @Param("error") String errorMessage);

    @Modifying
    @Transactional
    @Query("UPDATE Submit s set s.output = :output where s.id = :id")
    void updateSubmitOutput(@Param("id") Integer id, @Param("output") String output);
}
