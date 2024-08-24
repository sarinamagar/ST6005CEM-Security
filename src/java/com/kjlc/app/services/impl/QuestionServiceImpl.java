package com.kjlc.app.services.impl;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Question;
import com.kjlc.app.repository.QuestionRepository;
import com.kjlc.app.services.QuestionService;
import com.kjlc.app.utils.ImageUtils;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository repository;

    @Override
    public void save(List<Question> questions , Long testID) {
        Question q;
        for(Question question : questions){
            if(question.getQuestionID() != null){
                q = repository.findById(question.getQuestionID()).get();
            }
            q = new Question();
            q.setQuestionText(question.getQuestionText());
            q.setOptionA(question.getOptionA());
            q.setOptionB(question.getOptionB());
            q.setOptionC(question.getOptionC());
            q.setOptionD(question.getOptionD());
            q.setCorrectOption(question.getCorrectOption());
            q.setTestID(testID);
            q.setImage(question.getImage());
            q.setAudio(question.getAudio());
            q.setPartid(question.getPartid());
            q.setQuestionInEnglish(question.getQuestionInEnglish());
            q.setQuestionInNativeLanguage(question.getQuestionInNativeLanguage());
            q.setOptionAsImage(question.getOptionAsImage());
            repository.save(q);

        }
    }

    @Override
    public List<Question> findByTestID(Long id) {
        List <Question> result = repository.findByTestId(id);
        return result;
    }

    @Override
    public Optional<Question> retrieve(Long id) {
        Optional<Question> result = repository.findById(id);
        return(result);
    }

    @Override
    public void deleteByTestID(Long testID) {
        deleteMedia(testID);
        List<Question> questions = repository.findByTestId(testID);
        for(Question question : questions){
            repository.delete(question);
        }
    }

    @Override
    public void deleteMedia(Long testID) {
        List<Question> questions = repository.findAllMedia(testID);
        for(Question question : questions){
            try{
                if(question.getImage() != null){
                    ImageUtils.delete(question.getImage());
                }
                if(question.getAudio() != null){
                    ImageUtils.delete(question.getAudio());
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(List<Question> questions, Long testID) {
        for(Question question : questions){
            if(question.getQuestionID() != null){
                try{
                    Question originalQuestion = repository.findById(question.getQuestionID()).get();
                    if(originalQuestion.getImage() != null && question.getImage() == null){
                        question.setImage(originalQuestion.getImage());
                    }
                    else{
                        ImageUtils.delete(originalQuestion.getImage());
                    }
                    if(originalQuestion.getOptionAsImage()){
                        question.setOptionAsImage(true);
                        if(originalQuestion.getOptionA() != null  && (question.getOptionA() == null ||question.getOptionA().isBlank())){
                            question.setOptionA(originalQuestion.getOptionA());
                        }
                        else{
                            ImageUtils.delete(originalQuestion.getOptionA());
                        }
                        if(originalQuestion.getOptionB() != null  && (question.getOptionB() == null ||question.getOptionB().isBlank())){
                            question.setOptionB(originalQuestion.getOptionB());
                        }
                        else{
                            ImageUtils.delete(originalQuestion.getOptionB());
                        }
                        if(originalQuestion.getOptionC() != null && (question.getOptionC() == null ||question.getOptionC().isBlank())){
                            question.setOptionC(originalQuestion.getOptionC());
                        }
                        else{
                            ImageUtils.delete(originalQuestion.getOptionC());
                        }
                        if(originalQuestion.getOptionD() != null  && (question.getOptionD() == null ||question.getOptionD().isBlank())){
                            question.setOptionD(originalQuestion.getOptionD());
                        }
                        else{
                            ImageUtils.delete(originalQuestion.getOptionD());
                        }
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                
            }
            //System.out.println(question.getOptionA());
            question.setTestID(testID);
            repository.save(question);
        }
    }
}
