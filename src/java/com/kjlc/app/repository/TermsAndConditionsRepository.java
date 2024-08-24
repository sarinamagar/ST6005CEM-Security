package com.kjlc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjlc.app.Entity.TermsAndConditions;

@Repository
public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions,Long>{

    
} 