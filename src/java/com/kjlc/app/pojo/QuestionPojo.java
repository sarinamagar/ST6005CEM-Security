package com.kjlc.app.pojo;

import com.kjlc.app.Entity.Question;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPojo {
    private Long questionID;
    private Long TestID;

    @NotEmpty(message = "Question can't be empty")
    private String questionText;

    @NotEmpty(message = "Options can't be empty")
    private String optionA;

    @NotEmpty(message = "Options can't be empty")
    private String optionB;

    @NotEmpty(message = "Options can't be empty")
    private String optionC;

    @NotEmpty(message = "Options can't be empty")
    private String optionD;

    @NotEmpty(message = "Options can't be empty")
    private String correctOption;
    
    private String audio;
    private String image;

    private String questionInEnglish;
    private String questionInNativeLanguage;

    private Boolean optionAsImage;

    public QuestionPojo(Question question){
        this.questionID = question.getQuestionID();
        this.TestID = question.getTestID();
        this.questionText = question.getQuestionText();
        this.optionA = question.getOptionA();
        this.optionB = question.getOptionB();
        this.optionC = question.getOptionC();
        this.optionD = question.getOptionD();
        this.correctOption = question.getCorrectOption();
        this.audio = question.getAudio();
        this.image = question.getImage();
        this.questionInEnglish = question.getQuestionInEnglish();
        this.questionInNativeLanguage = question.getQuestionInNativeLanguage();
        this.optionAsImage = question.getOptionAsImage();
    }

    
    
}

