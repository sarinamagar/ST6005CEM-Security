package com.kjlc.app.SecurityContext;

import org.springframework.security.core.context.SecurityContextHolder;

import com.kjlc.app.Entity.User;

public class Context {

    //Important return principal instead of Username and userID separately

    public static String getUsername(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            username = ((User) principal).getUsername();
        }
        else{
            username = principal.toString();
        }
        return (username);
    }

    
    public static Long getUserID(){
        Long userId = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            userId = ((User) principal).getUserID();
        }
        return (userId);
    }

    public static String getRole(){
        String role = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            role = ((User) principal).getRole();
        }
        return (role);
    }

}
