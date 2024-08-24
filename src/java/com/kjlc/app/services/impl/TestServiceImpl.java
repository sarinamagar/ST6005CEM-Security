package com.kjlc.app.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Test;
import com.kjlc.app.pojo.TestPojo;
import com.kjlc.app.repository.TestRepository;
import com.kjlc.app.services.TestService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{

    private final TestRepository repository;
    
    @Override
    public Long save(TestPojo test) {
        Test t;
        if(test.getTestID()!=null){
            t = repository.findById(test.getTestID()).get();
        }
        else{
            t = new Test();
        }
        t.setTestName(test.getTestName());
        t.setLevel(test.getLevel());
        t.setDuration(test.getDuration());
        t.setTestDate(Date.valueOf(LocalDate.now()));
        t.setStatus(false);
        repository.save(t);
        return(t.getTestID());
    }

    @Override
    public List<Test> retrieve() {
        List <Test> result = repository.findAll();
        return result;
    }

    @Override
    public Optional<Test> retrieve(Long id) {
        Optional<Test> result = repository.findById(id);
        return result;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        repository.state(false, id);
    }

    @Override
    @Transactional
    public void activate(Long id) {
        repository.state(true, id);
    }

    @Override
    public List<Test> retrieveActive() {
        List<Test> result = repository.findAllActive();
        return result;
    }

    @Override
    public List<Test> retrieveInactive() {
        List<Test> result = repository.findAllInactive();
        return result;
    }

    @Override
    public Hashtable<String, Integer> metrics() {
        Hashtable<String,Integer> metrics = new Hashtable<>();
        metrics.put("total", repository.findAll().size());
        metrics.put("active", repository.findAllActive().size());
        return metrics;
    }
}
