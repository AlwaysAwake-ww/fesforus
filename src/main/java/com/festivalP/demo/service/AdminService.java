package com.festivalP.demo.service;


import com.festivalP.demo.domain.Admin;
import com.festivalP.demo.domain.Member;
import com.festivalP.demo.form.AuthInfo;
import com.festivalP.demo.repository.AdminRepository;
import com.festivalP.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    @Transactional
    private Admin encryptFunc(Admin admin){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = admin.getAdminPw();
        String securePw = encoder.encode(pw);
        admin.setAdminPw(securePw);
        return admin;
    }

    @Transactional
    public String join(Admin admin){
        adminRepository.save(encryptFunc(admin));
        return admin.getAdminId();
    }

    @Transactional
    public boolean validateDuplicateAdminId(String adminId){
        Admin admin = adminRepository.findById(adminId);
        if(admin!=null) {
            return false;
        }
        else{
            return true;
        }
    }


    @Transactional
    public boolean adminExistCheck(String adminId, String adminPw){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Admin admin =adminRepository.findById(adminId);
        if(admin==null){
            return false;
        }
        else{
            return encoder.matches(adminPw,admin.getAdminPw());
        }
    }

    @Transactional
    public Admin getAdminInfo(String adminId){

        Admin resAdmin = adminRepository.findById(adminId);
        return resAdmin;
    }
}
