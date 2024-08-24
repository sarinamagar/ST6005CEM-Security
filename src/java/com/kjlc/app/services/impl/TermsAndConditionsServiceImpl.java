package com.kjlc.app.services.impl;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.TermsAndConditions;
import com.kjlc.app.repository.TermsAndConditionsRepository;
import com.kjlc.app.services.TermsAndConditionsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TermsAndConditionsServiceImpl implements TermsAndConditionsService{
    private final TermsAndConditionsRepository repository;
     
    @Override
    public String getDescription() {
        return repository.findAll().size() > 0 ? repository.findAll().get(0).getDesc() : null;
        
    }

    @Override
    public void updateDescription(String description) {
        TermsAndConditions currTermsAndConditions = repository.findAll().size() > 0 ? repository.findAll().get(0) : new TermsAndConditions();
        currTermsAndConditions.setDesc(description); 
        repository.save(currTermsAndConditions);
    }
    
}
