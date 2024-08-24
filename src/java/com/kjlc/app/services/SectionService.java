package com.kjlc.app.services;

import java.util.List;

import com.kjlc.app.Entity.Section;

public interface SectionService {
    List<Long> update(String[] section,String[] sectionTexts, Long testID);
    List<Long> save(String[] sections, Long testID);
    List<Section> findByTestID(Long id);
    void deleteByTestID(Long testID);
}
