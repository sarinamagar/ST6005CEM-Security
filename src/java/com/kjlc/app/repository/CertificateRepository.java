package com.kjlc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kjlc.app.Entity.Certificate;
import java.util.List;


@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long>{
    
    @Query
    List<Certificate> findByUserID(Long userID);
} 
