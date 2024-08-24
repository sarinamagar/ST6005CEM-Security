package com.kjlc.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Section;
import com.kjlc.app.repository.SectionRepository;
import com.kjlc.app.services.SectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService{

    private final SectionRepository repository;

    @Override
    public List<Long> update( String[] sectionIDs, String[] sectionTexts, Long testID) {
        Section s;
        List<Long> ids = new ArrayList<>();
        for(int i = 0; i < sectionIDs.length; i ++){
            if(sectionIDs[i] !=null && sectionIDs[i].strip() !=""){
                s = repository.findById(Long.parseLong(sectionIDs[i])).get();
            }
            else{
                s = new Section();
            }
            s.setTestID(testID);
            s.setText(sectionTexts[i]);
            repository.save(s);
            ids.add(s.getSectionID());
        }
        return(ids);
    }

    @Override
    public List<Section> findByTestID(Long id) {
        List<Section> result  = repository.findByTestID(id);
        return(result);
    }

    @Override
    public List<Long> save(String[] sections, Long testID) {
        List<Long> ids = new ArrayList<>();
        Section section;
        for(String secText : sections){
            section = new Section();
            section.setTestID(testID);
            section.setText(secText);
            try{
                Section sec = repository.save(section);
                ids.add(sec.getSectionID());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return(ids);
    }

    @Override
    public void deleteByTestID(Long testID) {
        List<Section> sections = repository.findByTestID(testID);
        for(Section section : sections){
            repository.delete(section);
        }
    }
    
    
}
