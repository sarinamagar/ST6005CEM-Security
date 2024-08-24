package com.kjlc.app.pojo;

import com.kjlc.app.Entity.Section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionPojo {
    private Long sectionID;
    private Long testID;
    private String text;

    public SectionPojo(Section section) {
        this.sectionID = section.getSectionID();
        this.testID = section.getTestID();
        this.text = section.getText();
    }
}
