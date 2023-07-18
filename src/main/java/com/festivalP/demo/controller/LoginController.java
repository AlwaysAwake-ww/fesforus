package com.festivalP.demo.controller;


import com.festivalP.demo.domain.Admin;
import com.festivalP.demo.domain.Member;
import com.festivalP.demo.form.AuthInfo;
import com.festivalP.demo.form.MemberForm;
import com.festivalP.demo.service.AdminService;
import com.festivalP.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.WeakHashMap;

@Controller
@RequiredArgsConstructor
public class LoginController {


    private final MemberService memberService;
    private final AdminService adminService;
    public WeakHashMap<String, String> authData = new WeakHashMap<>();

    @ResponseBody
    @PostMapping("/member/login")
    public String login(String login_id, String login_password, HttpServletRequest request) {

        if (memberService.memberExistCheck(login_id, login_password)) {
            try {
                AuthInfo authInfo = memberService.getMemberAuthInfo(login_id);
                Member member = memberService.getMemberAllInfo(login_id);
                if(member.getMemberState()==1 ||member.getMemberState()==3)
                    return "F";

                member.setMemberPw(null);
                HttpSession session = request.getSession();
                session.setAttribute("authInfo", authInfo);
                session.setAttribute("member", member);

                return "S";
            } catch (Exception e) {
                return "F";

            }
        }

        else {
            return "F";
        }
    }



    @ResponseBody
    @PostMapping("/member/adminlogin")
    public String adminlogin(String login_id, String login_password, HttpServletRequest request) {

        if (adminService.adminExistCheck(login_id, login_password)) {
            try {

                AuthInfo authInfo = new AuthInfo();
                Admin admin = adminService.getAdminInfo(login_id);

                authInfo.setId(login_id);
                authInfo.setState(2);

                HttpSession session = request.getSession();
                session.setAttribute("authInfo", authInfo);
                session.setAttribute("admin", admin);

                return "S";
            } catch (Exception e) {
                return "F";

            }
        }

        else {
            return "F";
        }
    }


    @ResponseBody
    @PostMapping("/member/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/";
    }





}