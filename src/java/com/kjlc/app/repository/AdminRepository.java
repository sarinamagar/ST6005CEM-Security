package com.kjlc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    @Query(value = "select * from admin where userid = ?1", nativeQuery=true)
    Optional<Admin> findByUserID(Long id);

}
