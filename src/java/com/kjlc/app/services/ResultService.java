package com.kjlc.app.services;

import java.util.Hashtable;
import java.util.List;

import com.kjlc.app.Entity.Result;

public interface ResultService {
    Result retrieveByID(Long resultID);
    Long save(Result result);
    void addNote(Long id, String note);
    List<Result> retrieveByUserID(Long userID);
    List<Result> retrieve(Long testID);
    List<Result> retrieve(Long userID, Long testID);
    List<Result> retrieve();
    List<Result> findPassed(Long UserID);
    Hashtable<String,Integer> metrics(Long userID);
}
