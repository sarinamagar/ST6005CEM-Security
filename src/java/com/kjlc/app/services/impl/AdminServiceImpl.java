package com.kjlc.app.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Admin;
import com.kjlc.app.pojo.AdminPojo;
import com.kjlc.app.repository.AdminRepository;
import com.kjlc.app.services.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository repository;

    @Override
    public void delete(long id) {
        repository.deleteById(id);
        
    }

    @Override
    public List<Admin> retrieve(){
        List<Admin> result = repository.findAll();
        return (result);
    }

    @Override
    public Optional<Admin> retrieve(Long id) throws NoSuchElementException{
        Optional<Admin> result = repository.findById(id);
        return (result);
    }

    @Override
    public Optional<Admin> retrieveByUserID(Long id) {
        Optional<Admin> result = repository.findByUserID(id);
        return (result);
    }

    @Override
    public void save(AdminPojo adminDetails) {
        Admin admin;
        if(repository.findByUserID(adminDetails.getUserID()).isPresent()){
            admin = repository.findByUserID(adminDetails.getUserID()).get();
        }
        else{
            admin = new Admin();
        }
        admin.setAddress(adminDetails.getAddress());
        admin.setContact(adminDetails.getContact());
        admin.setFirstName(adminDetails.getFirstName());
        admin.setLastName(adminDetails.getLastName());
        admin.setUserID(adminDetails.getUserID());
        repository.save(admin);
    }

    
}
