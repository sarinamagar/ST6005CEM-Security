package com.kjlc.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.kjlc.app.Entity.Result;
import com.kjlc.app.Entity.Submission;
import com.kjlc.app.Entity.User;
import com.kjlc.app.SecurityContext.Context;
import com.kjlc.app.pojo.StudentPojo;
import com.kjlc.app.pojo.UserPojo;
import com.kjlc.app.services.CertificateService;
import com.kjlc.app.services.QuestionService;
import com.kjlc.app.services.ResultService;
import com.kjlc.app.services.StudentService;
import com.kjlc.app.services.SubmissionService;
import com.kjlc.app.services.UserService;
import com.kjlc.app.utils.Builder;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController{

    private final UserService userService;
    private final StudentService studentService;
    private final SubmissionService submissionService;
    private final QuestionService questionService;
    private final ResultService resultService;
    private final CertificateService certificateService;

    @GetMapping("/register")
    public String getRegisterPage(){
        return ("register");
    }

    @PostMapping("/create")
    public String create(UserPojo user, StudentPojo student ,Model model) {
        String message;

        try{
            User u = userService.save(user);
            student.setUserID(u.getUserID());
            studentService.save(student);
            message = "Successfully registered";
        }
        catch(Exception e){
            message = "Error registering User";
            e.printStackTrace();
        }
        model.addAttribute("message", message);
        if(Context.getRole().equals("ADMIN")){
            return ("redirect:/admin/list/students");
        }
        else{
            return("redirect:/user/edit");
        }
    }
    @PostMapping("/register")
    public String register(UserPojo user, StudentPojo student ,Model model) {
        String message;

        try{
            User u = userService.save(user);
            student.setUserID(u.getUserID());
            studentService.createAccount(student);
            message = "Successfully registered";
            model.addAttribute("message", message);
        }
        catch(Exception e){
            e.printStackTrace();
            message = "Error registering account. Please make sure your email has not been registered already.";
            model.addAttribute("message", message);
            return("createAccount");
        }
        return("redirect:/login");
    }
    
    @CrossOrigin(origins={"https://kjlc.myexamcenter.com", "http://localhost:8080"})
    @PostMapping("/test/submit/{testID}")
    public String submitTest(@PathVariable Long testID, @RequestBody JsonNode submission, Model model){
        Result rs = new Result();
        Long resultID = resultService.save(rs);
        List<Submission> submissions = Builder.submissionBuilder(submission, testID, resultID);
        submissionService.save(submissions);
        List<Submission> submissionCollection = submissionService.retrieveByResultID(resultID);
        Result result = Builder.resultBuilder(testID, submissionCollection, questionService, resultID);
        resultService.save(result);
        return ("redirect:/user/result/"+resultID);
    }

    @CrossOrigin(origins={"https://kjlc.myexamcenter.com", "http://localhost:8080"})
    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/verify")
    public String submitTest( @RequestBody JsonNode token, Model model){
        studentService.verify(token.asText());
        return ("redirect:/user/home");
    }
    


    
}