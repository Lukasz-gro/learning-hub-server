package com.example.learninghub.submit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmitRepository extends JpaRepository<Submit, Integer> {

    Optional<Submit> findTopByOrderById();

    @Modifying
    @Query("UPDATE Submit s set s.status =:new_status where s.id =:id")
    void updateSubmitStatus(@Param("id") Integer id, @Param("new_status") String newStatus);
}
