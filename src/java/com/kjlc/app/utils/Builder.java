package com.kjlc.app.utils;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.kjlc.app.Entity.Question;
import com.kjlc.app.Entity.Result;
import com.kjlc.app.Entity.Section;
import com.kjlc.app.Entity.Submission;
import com.kjlc.app.SecurityContext.Context;
import com.kjlc.app.pojo.QuestionPojo;
import com.kjlc.app.services.QuestionService;

import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Builder {
    
    public static List<Question> questionBuilder(QuestionPojo question, String qIDs, List<Long> sectionIDs, String partIndex, List<MultipartFile> icons, List<MultipartFile> audios, List<MultipartFile> optionImgA, List<MultipartFile> optionImgB,List<MultipartFile> optionImgC,List<MultipartFile> optionImgD){
        List <Question> Questions = new ArrayList<>();
        String[] questions = question.getQuestionText().split(",");
        String [] questionIDs = qIDs.split(",");
        String[] A = question.getOptionA().split(",");
        String[] B = question.getOptionB().split(",");
        String[] C = question.getOptionC().split(",");
        String[] D = question.getOptionD().split(",");
        String[] Correct = question.getCorrectOption().split(",");
        String[] qEnglish = question.getQuestionInEnglish().split(",");
        String[] qNative = question.getQuestionInNativeLanguage().split(",");
        String[] PartIndex = partIndex.split(",");

        for(int i = 0; i < questions.length; i++){
            Question q = new Question();
            if(!optionImgA.get(i).isEmpty() || !optionImgB.get(i).isEmpty() || !optionImgC.get(i).isEmpty() || !optionImgD.get(i).isEmpty()){
                
                try{
                    q.setOptionAsImage(true);
                    if(optionImgA.get(i).getSize() > 0){
                        q.setOptionA(ImageUtils.save(optionImgA.get(i)));
                    }
                    if(optionImgB.get(i).getSize() > 0){
                        q.setOptionB(ImageUtils.save(optionImgB.get(i)));
                    }
                    if(optionImgC.get(i).getSize() > 0){
                        q.setOptionC(ImageUtils.save(optionImgC.get(i)));
                    }
                    if(optionImgD.get(i).getSize() > 0){
                        q.setOptionD(ImageUtils.save(optionImgD.get(i)));
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                q.setOptionAsImage(false);
                q.setOptionA(A[i]);
                q.setOptionB(B[i]);
                q.setOptionC(C[i]);
                try{
                    if(! D[i].strip().equals(null) && !D[i].strip().equals("")){
                        q.setOptionD(D[i]);
                    }
                    else{
                        q.setOptionD(null);
                    }
                }
                catch(Exception e){
                    q.setOptionD(null);
                }
            }
            q.setQuestionText(questions[i]);
            q.setCorrectOption(Correct[i]);
            try{
                q.setQuestionInEnglish(qEnglish[i] == null? null : qEnglish[i]);
            }
            catch(IndexOutOfBoundsException e){
                q.setQuestionInEnglish(" ");
            }
            try{
                q.setQuestionInNativeLanguage(qNative[i] == null? null :qNative[i]);
            }
            catch(IndexOutOfBoundsException e){
                q.setQuestionInNativeLanguage(" ");
            }
            q.setPartid(sectionIDs.get(Integer.parseInt(PartIndex[i])-1));
            if(! questionIDs[i].equals(" ")){
                q.setQuestionID(Long.parseLong(questionIDs[i]));
            }
            else{
                q.setQuestionID(null);
            }
            try{
                if(icons.get(i).getSize() > 0){                   
                    String filename = ImageUtils.save(icons.get(i));
                    q.setImage(filename);   
                }
                if(audios.get(i).getSize() > 0){
                    String filename = AudioUtils.save(audios.get(i));
                    q.setAudio(filename);    
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            Questions.add(q);
        }
        return(Questions);
    }

    public static List<Submission> submissionBuilder(JsonNode submission, Long testID, Long resultID){
        List<Submission> submissions = new ArrayList<>();
        for(int i = 0; i < submission.size(); i++){
            Submission s = new Submission();
            s.setAnswer(submission.get(i).get("answer").toString().replace("\"", ""));
            s.setQuestionID(Long.parseLong(submission.get(i).get("questionID").toString()));
            s.setTestID(testID);
            s.setUserID(Context.getUserID());
            s.setResultID(resultID);
            submissions.add(s);
        }
        return(submissions);
    }

    public static Result resultBuilder(Long TestID, List<Submission> submission , QuestionService questionService, Long resultID){

        Integer score = 0;
        for(int i = 0; i < submission.size(); i++){
            Submission s = submission.get(i);
            if(questionService.retrieve(s.getQuestionID()).isPresent()){
                try{
                    if(s.getAnswer().equals(questionService.retrieve(s.getQuestionID()).get().getCorrectOption())){
                        score ++;
                    }
                }
                catch(Exception e){
                }
            }
        }

        Result result = new Result();
        result.setResultID(resultID);
        result.setScore((int)((double) score/submission.size() * 100));
        if(result.getScore() >= 75){
            result.setStatus(true);
        }
        else{
            result.setStatus(false);
        }
        result.setTestID(TestID);
        result.setUserID(Context.getUserID());
        result.setState(false);
        result.setNote("No Notes have been added.");
        result.setDate(Date.valueOf(LocalDate.now()));
        return(result);
    }

    public static Hashtable<String, List<Hashtable<String,String>>> buildResultBySection(List<Question> 
    questions, List<Section> sections, List<Submission> submissions){
        Hashtable<String, List<Hashtable<String,String>>> hashParent = new Hashtable<>();
        for(int i = 0; i < sections.size(); i++){
            Section thisSection = sections.get(i);
            List<Hashtable<String,String>> childList = new ArrayList<>();
            for(int h = 0; h < questions.size(); h++){
                Question thisQuestion = questions.get(h);
                Map<String,String> optionToTextMap = new HashMap<>();
                optionToTextMap.put("A", thisQuestion.getOptionA());
                optionToTextMap.put("B", thisQuestion.getOptionB());
                optionToTextMap.put("C", thisQuestion.getOptionC());
                optionToTextMap.put("D", thisQuestion.getOptionD());
                if(questions.get(h).getPartid() == thisSection.getSectionID()){
                    Hashtable<String,String> hashChild = new Hashtable<>();
                    hashChild.put("qTextInEnglish", questions.get(h).getQuestionInEnglish());
                    hashChild.put("selectedAnswer", submissions.get(h).getAnswer());
                    hashChild.put("qTextInJapanese", questions.get(h).getQuestionText());
                    hashChild.put("correctAnswer", questions.get(h).getCorrectOption());
                    hashChild.put("selectedAnswerText", optionToTextMap.getOrDefault(hashChild.get("selectedAnswer"), "null"));
                    hashChild.put("correctAnswerText", optionToTextMap.getOrDefault(hashChild.get("correctAnswer"), "null"));
                    hashChild.put("optionAsImage", questions.get(h).getOptionAsImage().toString());
                    childList.add(hashChild);
                }
            }
            hashParent.put(thisSection.getText(), childList);
        }
        return(hashParent);
    }

    
}
