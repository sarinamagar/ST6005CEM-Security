package com.kjlc.app.services;

import java.util.List;
import java.util.Optional;

import com.kjlc.app.Entity.Admin;
import com.kjlc.app.pojo.AdminPojo;

public interface AdminService {
    void save(AdminPojo admin);
    List<Admin> retrieve();
    Optional<Admin> retrieve(Long id);
    Optional<Admin> retrieveByUserID(Long id);
    void delete(long id);
}
