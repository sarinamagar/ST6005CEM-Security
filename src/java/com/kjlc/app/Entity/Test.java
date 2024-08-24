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
@Table(name = "test")

@Getter 
@Setter
public class Test {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TestID;
    private String TestName;
    private Date TestDate;
    private Integer level;
    private Boolean Status;
    private Integer duration;
}
