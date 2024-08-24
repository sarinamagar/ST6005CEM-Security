package com.kjlc.app.pojo;

import java.sql.Date;

import com.kjlc.app.Entity.Test;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestPojo {
    
    private Long TestID;

    @NotEmpty(message = "TestName can't be empty")
    private String TestName;

    private Date TestDate;

    @NotEmpty(message = "level can't be empty")
    private Integer level;

    private Boolean Status;

    @NotEmpty(message = "duration can't be empty")
    private Integer duration;

    public TestPojo(Test test) {
        this.TestID = test.getTestID();
        this.TestName = test.getTestName();
        this.TestDate = test.getTestDate();
        this.level = test.getLevel();
        this.Status = test.getStatus();
        this.duration = test.getDuration();
    }
}
