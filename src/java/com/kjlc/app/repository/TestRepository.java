package com.kjlc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
    
    @Query(value = "select * from test where status = 1", nativeQuery = true)
    List<Test> findAllActive();

    @Query(value = "select * from test where status = 0", nativeQuery = true)
    List<Test> findAllInactive();

    @Modifying
    @Query(value = "update test set status = ?1 where testid = ?2", nativeQuery = true)
    void state(Boolean state, Long id);
}
