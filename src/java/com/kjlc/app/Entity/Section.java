package com.kjlc.app.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "section")
public class Section{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sectionID;
    Long testID;
    String text;
}