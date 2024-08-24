package com.kjlc.app.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter
@Table(name = "student")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private Long studentID;  
    private Long userID;
    private Integer level;
    private Integer tests;
    private String batch;
    private String FirstName;
    private String LastName;
    private String Address;
    private Long ContactNumber;
    private Date DOB;
    private Date joinedDate;
    private String accountType;
    private Long credit;
    private String token;
    private boolean verified;
}
