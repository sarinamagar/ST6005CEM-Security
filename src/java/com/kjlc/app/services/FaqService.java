package com.kjlc.app.services;

import java.util.List;

import com.kjlc.app.Entity.Faq;

public interface FaqService {
    void save(List<Faq> faqs);
    void delete(Long id);
    List<Faq> getFaq();
}
