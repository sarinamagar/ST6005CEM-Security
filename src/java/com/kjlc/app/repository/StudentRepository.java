package com.kjlc.app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.kjlc.app.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select * from student where userid = ?1", nativeQuery=true)
    public Optional<Student> findByUserID(Long id);

    @Query(value = "select distinct batch from student", nativeQuery = true)
    public List<String> findAllBatch();

    @Query(value = "select * from student where first_name = ?1 or last_name = ?1 or batch = ?1", nativeQuery = true)
    public List<Student> find(String search);

    @Modifying
    @Query(value = "UPDATE student SET credit = ? WHERE (`userID` = ?)", nativeQuery = true)
    public void updateCredits(Long credits, Long userID);
}
