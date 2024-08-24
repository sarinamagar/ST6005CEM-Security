package com.kjlc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long>{
    
    @Query(value = "select * from questions where testid = ?1", nativeQuery=true)
    public List<Question> findByTestId(Long id);

    @Query(value = "delete from questions where testid = ?1", nativeQuery=true)
    public void deleteByTestID(Long id);

    @Query(value = "select * from questions where testid = ?1 and image = 1 or audio = 1", nativeQuery = true)
    public List<Question> findAllMedia(Long id);
}
