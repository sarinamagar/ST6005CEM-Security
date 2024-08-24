package com.kjlc.app.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kjlc.app.Entity.TermsAndConditions;
import com.kjlc.app.SecurityContext.Context;
import com.kjlc.app.services.FaqService;
import com.kjlc.app.services.TermsAndConditionsService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@AllArgsConstructor
public class LoginController {
    private final FaqService faqService;
    private final TermsAndConditionsService termsAndConditionsService;
    
    @GetMapping({"/login"})
    public String getLogin(Model model){
        return("login");
    }
    
    @GetMapping("/logout")
    public String logout(){
        if(SecurityContextHolder.getContext() != null){
            SecurityContextHolder.clearContext();
        }
        return("login");
    }
    @GetMapping("/forgot")
    public String forgotPage(){
        return("forgot");
    }
    @GetMapping({"/", ""})
    public String getDashBoard(){
        try{
            if(SecurityContextHolder.getContext() != null){
                if(Context.getRole().equalsIgnoreCase("user")){
                    return("redirect:/user/home");
                }
                else{
                    return("redirect:/admin/home");
                }
            }
            else{
                return("redirect:/login");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            return("redirect:/login");
        }
        
    }
    @GetMapping("/create-account")
    public String createAccount(Model model){
        model.addAttribute("faqs", faqService.getFaq());
        return("createAccount");
    }
    @GetMapping("/t&c")
    public String getTermsAndConditions(Model model) {
        model.addAttribute("faqs", faqService.getFaq());
        model.addAttribute("description", termsAndConditionsService.getDescription());
        return("TermsAndConditions");
    }
    
}
