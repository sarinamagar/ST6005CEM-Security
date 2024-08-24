package com.kjlc.app.pojo;

import com.kjlc.app.Entity.Submission;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionPojo {
    private Long submissionID;
    private Long userID;
    private Long testID;
    private Long questionID;

    @NotEmpty(message = "Anwers can't be empty")
    private String answer;

    public SubmissionPojo(Submission submission) {
        this.submissionID = submission.getSubmissionID();
        this.userID = submission.getUserID();
        this.testID = submission.getTestID();
        this.questionID = submission.getQuestionID();
        this.answer = submission.getAnswer();
    }
}
