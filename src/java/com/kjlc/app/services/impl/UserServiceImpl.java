package com.kjlc.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.User;
import com.kjlc.app.config.PasswordEncoderUtil;
import com.kjlc.app.pojo.UserPojo;
import com.kjlc.app.repository.UserRepository;
import com.kjlc.app.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Override
    public User save(UserPojo userData) {
        User user;
        String role = userData.getRole();
        if(repository.findByEmail(userData.getEmail()).isPresent()){
            user = repository.findByEmail(userData.getEmail()).get();
        }
        else{
            user = new User();
        }
        if(userData.getRole() == null){
            user.setRole("USER");
        }
        else{
            user.setRole(role);
        }
        user.setEmail(userData.getEmail());
        if(! userData.getPassword().strip().isEmpty()){
            user.setPassword(PasswordEncoderUtil.getInstance().encode(userData.getPassword()));
        }
        repository.save(user);
        return(user);
    }

    @Override
    public User retrieve(Long id) {
        User user = repository.findById(id).get();
        return user;
    }

    @Override
    public List<User> retrieve() {
        List<User> user = repository.findAll();
        return(user);
    }


    
}
