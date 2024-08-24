package com.kjlc.app.services;

import java.util.List;

import com.kjlc.app.Entity.Certificate;

public interface CertificateService {
    Certificate getAll();
    Certificate getCertificateByID(Long id);
    List<Certificate> getCertificateByUserID(Long id);
    Certificate generate(Long id);
}
