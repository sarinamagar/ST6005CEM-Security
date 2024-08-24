package com.kjlc.app.pojo;

import java.sql.Date;

import com.kjlc.app.Entity.Student;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentPojo {

    private Long studentID;
    private Long userID;

    @NotEmpty(message = "level can't be empty")
    private Integer level;

    private Integer tests;

    @NotEmpty(message = "batch can't be empty")
    private String batch;

    @NotEmpty(message = "FirstName can't be empty")
    private String FirstName;
    
    @NotEmpty(message = "LastName can't be empty")
    private String LastName;

    @NotEmpty(message = "Address can't be empty")
    private String Address;

    @NotEmpty(message = "ContactNumber can't be empty")
    private Long ContactNumber;

    @NotEmpty(message = "DOB can't be empty")
    private Date DOB;

    public StudentPojo(Student student) {
        this.studentID = student.getStudentID();
        this.userID = student.getUserID();
        this.level = student.getLevel();
        this.tests = student.getTests();
        this.batch = student.getBatch();
        this.FirstName = student.getFirstName();
        this.LastName = student.getLastName();
        this.Address = student.getAddress();
        this.ContactNumber = student.getContactNumber();
        this.DOB = student.getDOB();
    }

}
