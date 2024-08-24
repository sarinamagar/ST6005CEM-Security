package com.kjlc.app.services.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Result;
import com.kjlc.app.repository.ResultRepository;
import com.kjlc.app.services.CertificateService;
import com.kjlc.app.services.ResultService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultRepository repository;
    private final CertificateService certificateService;

    @Override
    public Long save(Result result) {
        Result rs;
        if(result.getResultID() != null){
            rs = result;
        }
        else{
            rs = new Result();
        }
        repository.save(rs);
        if(findPassed(result.getUserID()).size() > 15){
            if(certificateService.getCertificateByUserID(result.getUserID()).size() < 1)
            certificateService.generate(result.getUserID());
        }
        return(rs.getResultID());
    }

    @Override
    public List<Result> retrieve(Long testID) {
        return(repository.findByTestId(testID));
    }

    @Override
    public List<Result> retrieve() {
        List<Result> result = repository.findAll();
        return result;
    }

    @Override
    public List<Result> retrieveByUserID(Long userID) {
        List<Result> result = repository.findByUserID(userID);
        return result;
    }

    @Override
    public List<Result> retrieve(Long userID, Long testID) {
        List<Result> result = repository.findByUserID(userID, testID);
        return (result);
    }


    @Override
    @Transactional
    public void addNote(Long id, String note) {
        repository.addNote(note, id);
    }

    @Override
    public Result retrieveByID(Long resultID) {
        Result result = repository.findById(resultID).get();
        return(result);
    }

    @Override
    public Hashtable<String, Integer> metrics(Long userID) {
        Hashtable<String,Integer> metrics = new Hashtable<>();
        List<Result> results = repository.findByUserID(userID);
        OptionalDouble averagePerformance = results.stream()
                                                .mapToDouble(Result :: getScore)
                                                .average();
        OptionalInt bestPerformance = results.stream()
                                            .mapToInt(Result :: getScore)
                                            .max();
        metrics.put("total", results.size());
        metrics.put("averagePerformance", (int) averagePerformance.orElse(0));
        metrics.put("bestPerformance", bestPerformance.orElse(0));
        return(metrics);
    }

    @Override
    public List<Result> findPassed(Long userID) {
        return(repository.findPassedTests(userID));
    }
    
}
