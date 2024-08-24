package com.kjlc.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Faq;
import com.kjlc.app.repository.FaqRepository;
import com.kjlc.app.services.FaqService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService{
    private final FaqRepository repository;

    @Override
    public void save(List<Faq> faqs) {
        repository.saveAll(faqs);
    }

    @Override
    public List<Faq> getFaq() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
