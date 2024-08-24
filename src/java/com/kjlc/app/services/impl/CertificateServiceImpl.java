package com.kjlc.app.services.impl;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import com.kjlc.app.Entity.Certificate;
import com.kjlc.app.Entity.Student;
import com.kjlc.app.Entity.User;
import com.kjlc.app.repository.CertificateRepository;
import com.kjlc.app.services.CertificateService;
import com.kjlc.app.services.StudentService;
import com.kjlc.app.services.UserService;
import com.kjlc.app.utils.CertificateUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService{
    private final StudentService studentService;
    private final CertificateRepository repository;
    private final UserService userService;

    @Override
    public Certificate getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Certificate getCertificateByID(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCertificateByID'");
    }

    @Override
    public List<Certificate> getCertificateByUserID(Long id) {
        return(repository.findByUserID(id));
    }

    @Override
    public Certificate generate(Long id) {
        Certificate certificate = new Certificate();
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        certificate.setUserID(id);
        certificate.setGeneratedDate(currentDate);
        Student student = studentService.retrieveByUserID(id).get();
        try{
            certificate.setUrl(CertificateUtil.generateCertificate(student.getFirstName() + student.getLastName(), studentService.retrieve(id).get().getJoinedDate() , currentDate));
            Certificate savedCertificate = repository.save(certificate);
            return(savedCertificate);
        }
        catch(Exception  e){   
            e.printStackTrace();
            return(null);
        }
    }
    
}
