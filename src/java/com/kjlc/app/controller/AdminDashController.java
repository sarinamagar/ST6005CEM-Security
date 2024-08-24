package com.kjlc.app.controller;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kjlc.app.Entity.Admin;
import com.kjlc.app.Entity.Question;
import com.kjlc.app.Entity.Result;
import com.kjlc.app.Entity.Section;
import com.kjlc.app.Entity.Student;
import com.kjlc.app.Entity.Test;
import com.kjlc.app.services.AdminService;
import com.kjlc.app.services.FaqService;
import com.kjlc.app.services.PaymentDescriptionService;
import com.kjlc.app.services.QuestionService;
import com.kjlc.app.services.ResultService;
import com.kjlc.app.services.SectionService;
import com.kjlc.app.services.StudentService;
import com.kjlc.app.services.TermsAndConditionsService;
import com.kjlc.app.services.TestService;
import com.kjlc.app.services.UserService;
import com.kjlc.app.utils.Lister;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashController {

    private final QuestionService questionService;
    private final StudentService studentService;
    private final ResultService resultService;
    private final TestService testService;
    private final UserService userService;
    private final AdminService adminService;
    private final SectionService sectionService;
    private final PaymentDescriptionService paymentDescriptionService;
    private final FaqService faqService;
    private final TermsAndConditionsService termsAndConditionsService;

    @GetMapping({"/home"})
    public String getHome(Model model){
        Integer students = studentService.retrieve().size();
        Integer tests = testService.retrieveActive().size();
        Integer admins = adminService.retrieve().size();
        Integer results = resultService.retrieve().size();
        model.addAttribute("students", students);
        model.addAttribute("tests", tests);
        model.addAttribute("admins", admins);
        model.addAttribute("results", results);
        return ("home");
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return("registerAdmin");
    }

    @GetMapping("/test")
    public String getTest(){
        return("createTest");
    }

    @GetMapping("/test/get")
    public String getTest(Model model){
        Hashtable<String,Integer> metrics = testService.metrics();
        metrics.put("conducted", resultService.retrieve().size());
        model.addAttribute("metrics", metrics);
        model.addAttribute("tests",testService.retrieveActive());
        model.addAttribute("drafts", testService.retrieveInactive());
        return("listTest");
    }

    @GetMapping("/list/students")
    public String listStudents(Model model){    
        List<Student> students = studentService.retrieve();
        Hashtable<String,Integer> metrics = studentService.metrics();
        List<Long> userIDs = students.stream()
                                        .map(Student :: getUserID)
                                        .collect(Collectors.toList());
        List<Hashtable<String,Integer>> performance = userIDs.stream()
                                                        .map(resultService :: metrics)
                                                        .toList();
        model.addAttribute("metrics", metrics);
        model.addAttribute("students", students);
        if(model.getAttribute("searchResult") != null){
            model.addAttribute("students", model.getAttribute("searchResult"));
        }
        model.addAttribute("performance", performance);
        return("listStudents");
    }

    @GetMapping("list/results")
    public String redirectToStudentList(Model model){
        model.addAttribute("redirect", true);
        return(listStudents(model));
    }

    @GetMapping("/list/results/{id}")
    public String listResults(@PathVariable("id") Long userID, Model model){
        List<Result> resultSet = resultService.retrieveByUserID(userID);
        List<Dictionary<String,String>> results = Lister.lister(resultSet, testService, studentService);
        model.addAttribute("results", results);
        return("listResult");
    }

    @GetMapping("/list/admins")
    public String listAdmins(Model model){
        List<Admin> admins = adminService.retrieve();
        model.addAttribute("admins", admins);
        return("listAdmin");
    }

    @PostMapping("/admin/edit/{id}")
    public String editAdminPage(@PathVariable("id") Long id, Model model){
        Admin admin = adminService.retrieveByUserID(id).get();
        String email =  userService.retrieve(admin.getUserID()).getEmail();
        model.addAttribute("details", admin);
        model.addAttribute("email", email);
        return("editAdmin");
    }

    @GetMapping("/student/edit/{id}")
    public String editStudentPage(@PathVariable("id") Long id, Model model){
        Student student = studentService.retrieve(id).get();
        System.out.print(student.getUserID());
        String email =  userService.retrieve(student.getUserID()).getEmail();
        model.addAttribute("details", student);
        model.addAttribute("email", email);
        model.addAttribute("role", "Admin");
        return("editStudent");
    }

    @GetMapping("/test/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model){
        List<Question> questions = questionService.findByTestID(id);
        Test test = testService.retrieve(id).get();
        List<Section> section = sectionService.findByTestID(id);
        model.addAttribute("questions", questions);
        model.addAttribute("test", test);
        model.addAttribute("sections", section);
        return("editTest");
    }

    @GetMapping("/list/student/search")
    public String searchStudent(String keyword, Model model){
        List<Student> students = studentService.search(keyword);
        model.addAttribute("searchResult", students);
        return(listStudents(model));
    }
    
    @GetMapping("/creditPage")
    public String getCreditPage(Model model){
        List<Student> students = studentService.retrieve();
        model.addAttribute("students", students);
        return("creditPage");
    }

    @GetMapping("/create-faq")
    public String getCreateFaq(Model model) {
        model.addAttribute("faqs", faqService.getFaq());
        return("AddFaq");
    }
    @GetMapping("/create-qr")
    public String getCreateQr(Model model) {
        String desc = paymentDescriptionService.getDescription();
        model.addAttribute("description", desc);
        return("AddQr");
    }
    @GetMapping("/t&s")
    public String getTermsAndConditionsPage(Model model) {
        String termsAndConditions = termsAndConditionsService.getDescription();
        model.addAttribute("description", termsAndConditions);
        return ("UpdateTermsAndConditions");
    }
    
    

}