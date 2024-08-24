package com.kjlc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long>{
    
    @Query(value = "select * from submission where testid = ?1", nativeQuery=true)
    public List<Submission> findByTestId(Long id);

    @Query(value = "select * from submission where userid = ?1 and testid = ?2", nativeQuery=true)
    public List<Submission> findByUser(Long userID, Long testID);

    @Query(value = "select * from submission where resultid = ?1", nativeQuery=true)
    public List<Submission> findByResultID(Long id);
}
