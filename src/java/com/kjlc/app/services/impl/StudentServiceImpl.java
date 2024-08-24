package com.kjlc.app.services.impl;

import java.sql.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjlc.app.Entity.Student;
import com.kjlc.app.SecurityContext.Context;
import com.kjlc.app.pojo.StudentPojo;
import com.kjlc.app.repository.StudentRepository;
import com.kjlc.app.services.StudentService;
import com.kjlc.app.services.UserService;
import com.kjlc.app.utils.TokenGenerator;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final UserService userService;
    private final MailSenderService mailService;
    @Override
    public void save(StudentPojo student) {
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        Student s;
        if(repository.findByUserID(student.getUserID()).isPresent()){
            s = repository.findByUserID(student.getUserID()).get();
        }
        else{
            s = new Student();
        }
        s.setAddress(student.getAddress());
        s.setBatch(student.getBatch());
        s.setContactNumber(student.getContactNumber());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setDOB(student.getDOB());
        s.setJoinedDate(currentDate);
        s.setLevel(student.getLevel());
        s.setUserID(student.getUserID());
        s.setAccountType("basic");
        s.setVerified(false);
        s.setCredit(Long.valueOf(0));
        String token = TokenGenerator.generate();
        s.setToken(token);
        mailService.sendNewMail(userService.retrieve(s.getUserID()).getEmail(), "Verify KJLC Account", "Your verification code is " + token + ".");
        repository.save(s);
    }
    @Override
    public List<Student> retrieve() {
        return(repository.findAll());
    }
    @Override
    public void delete(long id) {
        repository.deleteById(id);
        
    }
    @Override
    public Optional<Student> retrieve(Long id) {
        Optional<Student> result = repository.findById(id);
        return (result);
    }
    @Override
    public Optional<Student> retrieveByUserID(Long id) {
        Optional<Student> result = repository.findByUserID(id);
        return (result);
    }
    @Override
    public int countBatch() {
        //int result = repository.countBatch();
        return(0);
    }
    @Override
    public List<String> retrieveAllBatch() {
        List<String> batches = repository.findAllBatch();
        return (batches);
    }
    @Override
    public Hashtable<String, Integer> metrics() {
        Hashtable<String,Integer> metrics = new Hashtable<>();
        metrics.put("enrolled", repository.findAll().size());
        metrics.put("active", repository.findAll().size());
        metrics.put("batches", repository.findAllBatch().size());
        return(metrics);

    }
    @Override
    public List<Student> search(String keyword) {
        List<Student> searchResult = repository.find(keyword);
        return(searchResult);
    }
    @Override
    public void createAccount(StudentPojo student) {
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        Student s = new Student();
        if(repository.findByUserID(student.getUserID()).isPresent()){
            throw new EntityExistsException();
        }
        s.setAddress(student.getAddress());
        s.setBatch(student.getBatch());
        s.setContactNumber(student.getContactNumber());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setDOB(student.getDOB());
        s.setJoinedDate(currentDate);
        s.setLevel(student.getLevel());
        s.setUserID(student.getUserID());
        s.setAccountType("basic");
        s.setVerified(false);
        s.setCredit(Long.valueOf(0));
        String token = TokenGenerator.generate();
        s.setToken(token);
        mailService.sendNewMail(userService.retrieve(s.getUserID()).getEmail(), "Verify KJLC Account", "Your verification code is " + token + ".");
        repository.save(s);
    }
    @Override
    public void verify(String token) {
        Student s = retrieveByUserID(Context.getUserID()).get();
        if(s.getToken().equals(token)){
            s.setVerified(true);
            repository.save(s);
            } 
    }
    @Override
    @Transactional
    public void deductCredit(Long credit) {
        Student s = retrieveByUserID(Context.getUserID()).get();
        Long newCredit = s.getCredit() - 10;
        System.out.println(newCredit);
        repository.updateCredits(newCredit, s.getUserID());
    }

    @Override
    @Transactional
    public void addCredit(Long credit, Long userID) {
        Student s = retrieveByUserID(userID).get();
        Long newCredit = s.getCredit() + credit;
        repository.updateCredits(newCredit, s.getUserID());
    }   
}
