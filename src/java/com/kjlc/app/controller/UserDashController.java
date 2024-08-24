package com.kjlc.app.controller;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjlc.app.Entity.Question;
import com.kjlc.app.Entity.Result;
import com.kjlc.app.Entity.Section;
import com.kjlc.app.Entity.Student;
import com.kjlc.app.Entity.Submission;
import com.kjlc.app.services.CertificateService;
import com.kjlc.app.services.PaymentDescriptionService;
import com.kjlc.app.services.QuestionService;
import com.kjlc.app.services.ResultService;
import com.kjlc.app.services.SectionService;
import com.kjlc.app.services.StudentService;
import com.kjlc.app.services.SubmissionService;
import com.kjlc.app.services.TermsAndConditionsService;
import com.kjlc.app.services.TestService;
import com.kjlc.app.SecurityContext.Context;
import com.kjlc.app.services.UserService;
import com.kjlc.app.utils.Builder;
import com.kjlc.app.utils.Lister;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserDashController {

    private final UserService userService;
    private final QuestionService questionService;
    private final TestService testService;
    private final SubmissionService submissionService;
    private final StudentService studentService;
    private final ResultService resultService;
    private final SectionService sectionService;
    private final CertificateService certificateService;
    private final PaymentDescriptionService paymentDescriptionService;
    private final TermsAndConditionsService termsAndConditionsService;

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("tests",testService.retrieveActive().size());
        model.addAttribute("performance", resultService.metrics(Context.getUserID()).get("averagePerformance"));
        model.addAttribute("userDetails", studentService.retrieveByUserID(Context.getUserID()).get());
        model.addAttribute("student", studentService.retrieveByUserID(Context.getUserID()).get());
        return("userHome");
    }
    
    @GetMapping("/list/tests")
    public String getTests(Model model){
        Hashtable<String,Integer> metrics = testService.metrics();
        metrics.put("taken", resultService.metrics(Context.getUserID()).get("total"));
        model.addAttribute("metrics", metrics);
        model.addAttribute("tests",testService.retrieveActive());
        if(studentService.retrieveByUserID(Context.getUserID()).get().getCredit() < 10){
            model.addAttribute("creditMessage", "Not enough credit. Please purchase 10 credits and try again.");
        }
        return("listTestUser");
    }
    //renamed retrievebytestID to find by testID. change in kjlc production
    
    @GetMapping("/test/{id}")
    public String getTestPage(@PathVariable("id") Long testID, Model model){
        List<Question> questions = questionService.findByTestID(testID);
        List<Section> sections = sectionService.findByTestID(testID);
        String fullName;
        if(Context.getRole().equals("ADMIN")){
            fullName = "Admin";
        }
        else{
            fullName = studentService.retrieveByUserID(Context.getUserID()).get().getFirstName() 
            + " "
            + studentService.retrieveByUserID(Context.getUserID()).get().getLastName();
        }
        model.addAttribute("sections", sections);
        model.addAttribute("questions", questions);
        model.addAttribute("test", testService.retrieve(testID).get());
        model.addAttribute("candidate", fullName);
        model.addAttribute("role", Context.getRole());
        if(!Context.getRole().equalsIgnoreCase("admin")){
            if(studentService.retrieveByUserID(Context.getUserID()).get().getCredit() > 10){
                studentService.deductCredit(Context.getUserID());
                return("test");
            }
        }
        else{
            return("test");
        }
        
        return("redirect:/user/list/tests");
    }

    @GetMapping("/viewSubmission/{id}")
    public String viewSubmission(@PathVariable Long id, Model model){
        List<Submission> submission = submissionService.retrieve(id);
        model.addAttribute("submissions", submission);
        return("ViewSubmission");
    } 

    @GetMapping("/list/results")
    public String allResult(Model model){
        List<Result> resultSet = resultService.retrieveByUserID(Context.getUserID());
        List<Dictionary<String,String>> results = Lister.lister(resultSet, testService, studentService);
        Hashtable<String,Integer> metrics = resultService.metrics(Context.getUserID());
        model.addAttribute("results", results);
        model.addAttribute("metrics", metrics);
        return("resultListUser");
    }

    @GetMapping("/edit")
    public String editUserPage(Model model){
        Student student = studentService.retrieveByUserID(Context.getUserID()).get();
        String email =  userService.retrieve(student.getUserID()).getEmail();
        model.addAttribute("details", student);
        model.addAttribute("email", email);
        model.addAttribute("role", "User");
        return("editStudent");
    }
    
    @GetMapping("/result/{id}")
    public String getResultPage(@PathVariable("id") Long resultID, Model model){
        Result thisResult = resultService.retrieveByID(resultID);
        Long testID = thisResult.getTestID();
        Integer score = thisResult.getScore();
        List<Section> sections = sectionService.findByTestID(testID);
        List<Submission> submissions = submissionService.retrieveByResultID(resultID);
        List<Question> questions = questionService.findByTestID(testID);
        Hashtable<String, List<Hashtable<String, String>>>  resultBySection = Builder.buildResultBySection(questions, sections, submissions);
        model.addAttribute("result", resultBySection);
        model.addAttribute("score", score);
        return("result");
    }

    @GetMapping("/buy-credit")
    public String getCreditPage(Model model){
        model.addAttribute("student", studentService.retrieveByUserID(Context.getUserID()).get());
        model.addAttribute("description", paymentDescriptionService.getDescription());
        return("buyCreditPage");
    }

    @GetMapping("/view-certificates")
    public String getCertificatePage(Model model) {
        String url = null;
        try{
            url = certificateService.getCertificateByUserID(Context.getUserID()).get(0).getUrl();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        model.addAttribute("certificate", url);
        return("ViewCertificates");
    }
    @GetMapping("/t&c")
    public String getTermsAndConditionsPage(Model model) {
        String termsAndConditions = termsAndConditionsService.getDescription();
        model.addAttribute("description", termsAndConditions);
        model.addAttribute("student", studentService.retrieveByUserID(Context.getUserID()).get());
        return ("UserTermsAndConditions");
    }

}
