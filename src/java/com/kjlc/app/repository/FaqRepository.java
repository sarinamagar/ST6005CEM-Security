package com.kjlc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjlc.app.Entity.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq,Long>{

    
} 
