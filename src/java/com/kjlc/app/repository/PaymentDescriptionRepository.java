package com.kjlc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjlc.app.Entity.PaymentDescription;

@Repository
public interface PaymentDescriptionRepository extends JpaRepository<PaymentDescription,Long>{
    
}
