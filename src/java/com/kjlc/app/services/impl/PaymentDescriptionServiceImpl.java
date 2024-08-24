package com.kjlc.app.services.impl;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.PaymentDescription;
import com.kjlc.app.repository.PaymentDescriptionRepository;
import com.kjlc.app.services.PaymentDescriptionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentDescriptionServiceImpl implements PaymentDescriptionService{
    private final PaymentDescriptionRepository repository;

    @Override
    public String getDescription() {
        PaymentDescription description;
        try{
            description = repository.findAll().get(0);
        }
        catch(IndexOutOfBoundsException e){
            description = save("Enter Description");
        }
        return description.getDesc();
    }

    @Override
    public String updateDescription(String description) {
        PaymentDescription desc = repository.findAll().get(0);
        desc.setDesc(description);
        repository.save(desc);
        return desc.getDesc();
    }

    private PaymentDescription save(String des){
        PaymentDescription description = new PaymentDescription();
        description.setDesc(des);
        return repository.save(description);
    }


}
