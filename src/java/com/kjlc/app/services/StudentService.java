package com.kjlc.app.services;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.kjlc.app.Entity.Student;
import com.kjlc.app.pojo.StudentPojo;

public interface StudentService {
    void save(StudentPojo student);
    void createAccount(StudentPojo student);
    List<Student> retrieve();
    int countBatch();
    Optional<Student> retrieve(Long id);
    Optional<Student> retrieveByUserID(Long id);
    Hashtable<String,Integer> metrics();
    List<String> retrieveAllBatch();
    List<Student> search(String keyword);
    void delete(long id);
    void verify(String token);
    void deductCredit(Long credit);
    void addCredit(Long credit, Long userID);
}
