package com.kjlc.app.pojo;

import com.kjlc.app.Entity.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private Long userID;
    private String Role;

    @NotEmpty(message = "email can't be empty")
    private String email;

    @NotEmpty(message = "password can't be empty")
    private String password;

    public UserPojo(User user) {
        this.userID = user.getUserID();
        this.Role = user.getRole();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
