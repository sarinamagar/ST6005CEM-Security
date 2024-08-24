package com.kjlc.app.services;

import java.util.List;

import com.kjlc.app.Entity.Submission;

public interface SubmissionService {
    void save(List<Submission> submission);
    List<Submission> retrieve(Long testID);
    List<Submission> retrieveByUser(Long testID);
    List<Submission> retrieveByResultID(Long resultID);
}
