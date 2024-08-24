package com.kjlc.app.services;
import java.util.List;
import java.util.Optional;

import com.kjlc.app.Entity.Question;

public interface QuestionService {
    void save(List<Question> questions, Long testID);
    void update(List<Question> questions, Long testID);
    Optional<Question> retrieve(Long id);
    List<Question> findByTestID(Long id);
    void deleteByTestID(Long testID);
    void deleteMedia(Long testID);
}
