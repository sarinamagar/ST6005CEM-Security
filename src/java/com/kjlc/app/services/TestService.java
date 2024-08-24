package com.kjlc.app.services;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.kjlc.app.Entity.Test;
import com.kjlc.app.pojo.TestPojo;

public interface TestService {
    Long save(TestPojo test);
    List<Test> retrieve();
    Optional<Test> retrieve(Long id);
    void delete(Long id);
    List<Test> retrieveActive();
    List<Test> retrieveInactive();
    void deactivate(Long id);
    void activate(Long id);
    Hashtable<String,Integer> metrics();

}
