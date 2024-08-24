package com.kjlc.app.pojo;

import java.sql.Date;

import com.kjlc.app.Entity.Result;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResultPojo {
    
    private Long resultID;
    private Long userID;
    private Long testID;

    @NotEmpty(message = "score can't be empty")
    private Integer score;

    @NotEmpty(message = "status can't be empty")
    private Boolean status;

    private String note;

    @NotEmpty(message = "state can't be empty")
    private Boolean state;

    @NotEmpty(message = "date can't be empty")
    private Date date;

    public ResultPojo(Result result) {
        this.resultID = result.getResultID();
        this.userID = result.getUserID();
        this.testID = result.getTestID();
        this.score = result.getScore();
        this.status = result.getStatus();
        this.note = result.getNote();
        this.state = result.getState();
        this.date = result.getDate();
    }
}
