package com.kjlc.app.services;

import java.util.List;

import com.kjlc.app.Entity.User;
import com.kjlc.app.pojo.UserPojo;

public interface UserService {
    User save(UserPojo user);
    User retrieve(Long id);
    List<User> retrieve();
}
