package com.kjlc.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.kjlc.app.Entity.Faq;
import com.kjlc.app.Entity.Question;
import com.kjlc.app.Entity.TermsAndConditions;
import com.kjlc.app.Entity.User;
import com.kjlc.app.pojo.AdminPojo;
import com.kjlc.app.pojo.QuestionPojo;
import com.kjlc.app.pojo.TestPojo;
import com.kjlc.app.pojo.UserPojo; 
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
import com.kjlc.app.utils.Builder;
import com.kjlc.app.utils.QrUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final StudentService studentService;
    private final AdminService adminService;
    private final QuestionService questionService;
    private final TestService testService;
    private final ResultService resultService;
    private final SectionService sectionService;
    private final FaqService faqService;
    private final PaymentDescriptionService paymentDescriptionService;
    private final TermsAndConditionsService termsAndConditionsService;


    @PostMapping("/create")
    public String createAdmin(@Valid UserPojo user, @Valid AdminPojo admin, Model model){
        String message;
        try{
            user.setRole("ADMIN");
            User u = userService.save(user);
            admin.setUserID(u.getUserID());
            adminService.save(admin);
            message = "Successfully registered";
        }
        catch(Exception e){
            e.printStackTrace();
            message = "Error registering User";
        }
        model.addAttribute("message", message);
        return("registerAdmin");
    }

    @PostMapping(value = "/test/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createTest(@RequestPart(name = "QuestionImage") List<MultipartFile> icons,@RequestPart(name = "QuestionAudio") List<MultipartFile> audios, List<MultipartFile> optionImageA,List<MultipartFile> optionImageB,List<MultipartFile> optionImageC,List<MultipartFile> optionImageD, String part, String partIndex, QuestionPojo question, String qID, TestPojo test, Model model){
        try{
            Long testId = testService.save(test);
            List <Long> sectionIDs = sectionService.save(part.split(","), testId);
            List <Question> questions = Builder.questionBuilder(question, qID, sectionIDs, partIndex, icons, audios, optionImageA, optionImageB, optionImageC, optionImageD);
            questionService.save(questions, testId);
        }
        catch(Exception e){
            model.addAttribute("message", 1);
        }
        return("redirect:/admin/test/get");
    }


    @PostMapping("/test/delete/{id}")
    public String deleteTest(@PathVariable("id") Long id){
        testService.delete(id);
        return("redirect:/admin/test/get");
    }

    @PostMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        studentService.delete(id);
        return("redirect:/admin/list/students");
    }

    @PostMapping("/test/activate/{id}")
    public String activateTest(@PathVariable("id") Long id){
        testService.activate(id);
        return("redirect:/admin/test/get");
    }

    @PostMapping("/test/deactivate/{id}")
    public String deactivateTest(@PathVariable("id") Long id){
        testService.deactivate(id);
        return("redirect:/admin/test/get");
    }

    @PostMapping(value = "/test/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateTest(@PathVariable ("id") Long testID,@RequestPart(name = "QuestionImage") List<MultipartFile> icons,@RequestPart(name = "QuestionAudio") List<MultipartFile> audios, List<MultipartFile> optionImageA,List<MultipartFile> optionImageB,List<MultipartFile> optionImageC,List<MultipartFile> optionImageD, String qID, String part, String partIndex, QuestionPojo question, TestPojo test, Model model){
        try{
            sectionService.deleteByTestID(testID);
            List <Long> sectionIDs = sectionService.save(part.split(","), testID);
            List <Question> questions = Builder.questionBuilder(question,qID, sectionIDs, partIndex, icons, audios, optionImageA, optionImageB, optionImageC, optionImageD);
            test.setTestID(testID);
            testService.save(test);
            questionService.update(questions, testID);
        }
        catch(Exception e){
            model.addAttribute("message", 1);
        }
        return("redirect:/admin/test/edit/"+testID);
    }
    @CrossOrigin(origins="https://kjlc.myexamcenter.com")
    @PostMapping("/result/note/{id}")
    public String addNote(@PathVariable("id") Long id, @RequestBody String note){
        resultService.addNote(id, note);
        return("redirect:/admin/list/results");
    }
    @PostMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable("id") Long id){
        adminService.delete(id);
        return("redirect:/admin/list/admins");
    }    
    @PostMapping("/student/credit/{id}")
    public String addCredit(@PathVariable("id") Long userID, Long credit ){
        studentService.addCredit(credit, userID);
        return("redirect:/admin/creditPage");
    } 
    @PostMapping("/add-faq")
    public String addFaq(String title, String description) {
        List<Faq> faqs = new ArrayList<>();
        String[] titleArr = title.split(",");
        String[] descArr = description.split(",");
        for(int i = 0; i < titleArr.length; i++){
            Faq faq = new Faq(titleArr[i], descArr[i]);
            faqs.add(faq);
        }
        faqService.save(faqs);
        return ("redirect:/admin/create-faq");
    }
    @PostMapping("/delete-faq/{id}")
    public String deleteFaq(@PathVariable("id") Long faqID) {
        faqService.delete(faqID);
        return ("redirect:/admin/create-faq");
    }
    

    @PostMapping(value = "/add-qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addQr(@RequestPart(name = "image") MultipartFile file) throws IOException{
        QrUtil.save(file);
        return ("redirect:/admin/create-qr");
    }

    @PostMapping("/payment/update-desc")
    public String updatePaymentDescription(String description) {
        paymentDescriptionService.updateDescription(description);
        return ("redirect:/admin/create-qr");
    }

    @PostMapping("/t&c/update-t&c")
    public String postMethodName(String description) {
        termsAndConditionsService.updateDescription(description);
        return ("redirect:/admin/t&s");
    }
    

}
