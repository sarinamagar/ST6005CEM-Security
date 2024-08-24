package com.kjlc.app.pojo;

import com.kjlc.app.Entity.Admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPojo {

    private Long adminID;
    private Long userID;

    @NotEmpty(message = "FirstName can't be empty")
    private String FirstName;

    @NotEmpty(message = "LastName can't be empty")
    private String LastName;

    @NotEmpty(message = "Address can't be empty")
    private String Address;

    private Long Contact;

    public AdminPojo(Admin admin){
        this.adminID = admin.getAdminID();
        this.userID = admin.getUserID();
        this.FirstName = admin.getFirstName();
        this.LastName = admin.getLastName();
        this.Address = admin.getAddress();
        this.Address = admin.getAddress();
    }
}
