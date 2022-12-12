package com.festivalP.demo.controller;

import com.festivalP.demo.domain.Admin;
import com.festivalP.demo.domain.Member;

import com.festivalP.demo.form.AdminForm;
import com.festivalP.demo.form.AuthInfo;
import com.festivalP.demo.form.MemberForm;
import com.festivalP.demo.service.AdminService;
import com.festivalP.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

//import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AdminService adminService;
    public WeakHashMap<String, String> authData = new WeakHashMap<>();


    //////////////////
    // 어드민 회원가입 페이지
    @GetMapping("/member/adminsignup")
    public String createAdminForm(Model model){
        model.addAttribute("adminForm", new AdminForm());
        // resource 의 HTML 경로
        return "member/adminSignUpForm";
    }

    @PostMapping("/member/adminsignup")
    public String create(AdminForm adminForm, BindingResult result){
        if(result.hasErrors()){
            return "member/adminSignUpForm";
        }

        Admin admin = new Admin();
        admin.setAdminId(adminForm.getId());
        admin.setAdminPw(adminForm.getPw());

        adminService.join(admin);
        System.out.println("관리자 회원가입성공");
        // 회원가입 완료 시 리턴 페이지
        return "redirect:/";
    }

    ////////////////////////////////
    // admin 아이디 중복체크
    @ResponseBody
    @PostMapping("/admin/iddupcheck")
    public String adminiddupcheck(String adminId){

        if(adminService.validateDuplicateAdminId(adminId)){
            return "S";
        }
        else{
            return "F";
        }
    }


    /////////////////////////
    // 멤버 회원가입
    @GetMapping("/member/signup")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        // resource 의 HTML 경로
        return "member/signUpForm";
    }

    @PostMapping("/member/signup")
    public String create(MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "member/signUpForm";
        }

        Member member = new Member();
        member.setMemberId(form.getId());
        member.setMemberPw(form.getPw());
        member.setMemberBirth(form.getBirth());
        member.setMemberAddr(form.getAddr());
        member.setMemberEmail(form.getEmail());
        member.setMemberNickname(form.getNickname());
        member.setMemberCategory(form.getCategory());
        member.setMemberState(form.getState());

        memberService.join(member);
        System.out.println("회원가입성공");
        // 회원가입 완료 시 리턴 페이지
        return "redirect:/";
    }

    @PostMapping("/member/delete")
    public String delete(HttpSession session){

        Member member = (Member) session.getAttribute("member");
        memberService.deleteMember(member);
        session.invalidate();
        return "redirect:/";
    }


    // 마이페이지
    @GetMapping("/member/mypage")
    public String mypage(HttpSession session, Model model){


        try{
            AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

            Member member = (Member) session.getAttribute("member");

            return "member/myPageForm";
        }catch(Exception e){

            return "redirect:/";
        }
    }

    @ResponseBody
    @PostMapping("/member/mypage/modifyconfirm")
    public String modifyConfirm(@RequestParam("nickname")String nickname, @RequestParam("addr")String addr, @RequestParam("birth")Date birth, @RequestParam("category")String category, @RequestParam("email")String email, HttpSession session){

        Member member = (Member) session.getAttribute("member");

        member.setMemberNickname(nickname);
        member.setMemberAddr(addr);
        member.setMemberBirth(birth);
        member.setMemberEmail(email);
        member.setMemberCategory(category);

        session.setAttribute("member", memberService.updateInfo(member));

        System.out.println("$$$ wowwowow modifyyyy");
        return "modify success";
    }

    @ResponseBody
    @PostMapping("/member/mypage/modify")
    public String modify(@RequestParam("login_id")String login_id, @RequestParam("login_password")String login_password){


        if (memberService.memberExistCheck(login_id, login_password)) {

            try {
                return "S";
            } catch (Exception e) {
                System.out.println("login failed: "+e.toString());
                return "F";

            }
        }
        else {
            return "F";
        }
    }

    @ResponseBody
    @PostMapping("/member/mypage/pwmodify")
    public String pwModify(@RequestParam("login_id")String login_id, @RequestParam("pw_modify_login_password")String pw_modify_login_password){

        System.out.println("## pwModify func Controller");
        if (memberService.memberExistCheck(login_id, pw_modify_login_password)) {

            try {
                return "S";
            } catch (Exception e) {
                System.out.println("login failed: "+e.toString());
                return "F";

            }
        }
        else {
            return "F";
        }
    }

    @GetMapping("/member/pwmodifypage")
    public String pwModifyPage(HttpSession session){

        Member member = (Member)session.getAttribute("member");

        System.out.println(member.getMemberId());
        System.out.println(member.getMemberPw());
        return "member/memberPasswordModifyPage";
    }

    @ResponseBody
    @PostMapping("/member/mypage/pwmodifyconfirm")
    public String pwModifyConfirm(@RequestParam("password") String password, HttpSession session){

        Member member = (Member) session.getAttribute("member");
        member.setMemberPw(password);
        session.setAttribute("member", memberService.updatePassword(member));

        System.out.println("$$$ wowwowow modifyyyy");
        return "modify success";
    }


    @GetMapping("/member/modifypage")
    public String modifyPage(HttpSession session){

        Member member = (Member)session.getAttribute("member");

        System.out.println(member.getMemberId());
        System.out.println(member.getMemberPw());
        return "member/memberModifyPage";
    }



    // 아이디 중복체크
    @ResponseBody
    @PostMapping("/member/iddupcheck")
    public String iddupcheck(String memberId){

        if(memberService.validateDuplicateMemberId(memberId)){
            return "S";
        }
        else{
            return "F";
        }
    }

    // 닉네임 중복체크
    @ResponseBody
    @PostMapping("/member/nicknamedupcheck")
    public String nicknamedupcheck(String memberNickname){

        if(memberService.validateDuplicateMemberNickname(memberNickname)){
            return "S";
        }
        else{
            return "F";
        }
    }

    @ResponseBody
    @PostMapping("/member/emaildupcheck")
    public String emaildupcheck(String memberEmail){

        if(memberService.validateDuplicateMemberEmail(memberEmail)){
            System.out.println("### email dup check true");
            return "S";
        }
        else{
            return "F";
        }
    }


    @Autowired
    JavaMailSender mailSender;


    // 인증메일 발송
    @ResponseBody
    @PostMapping("/member/emailAuth")
    public String emailAuth(String email){
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;

        // 메일 발송
        String setFrom = "wowinteresting234@gmail.com";
        String toMail = email;
        String title = "인증 메일입니다.";
        String content =
                "안녕하세요."+
                        "<br><br>"+
                        "인증번호는 "+checkNum+" 입니다."+
                        "<br>"+
                        "해당 번호를 입력하여 인증을 진행해주세요.";
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        authData.put(email, Integer.toString(checkNum));
        return Integer.toString(checkNum);
    }

    // 이메일 인증번호 체크
    @ResponseBody
    @PostMapping("/member/emailAuthCheck")
    public String emailAuthCheckFunction(String email, String emailAuthValue){
        if(emailAuthValue.equals(authData.get(email))){
            authData.replace(email, null);
            System.gc();
        return "S";
        }
        else{
            return "F";
        }
    }
    
    /////////////////////////////////////////////////////////////////
    // 아이디/비밀번호 찾기
    @GetMapping("/member/findaccount")
    public String findAccount(Model model){

        return "member/findAccount";
    }

    @GetMapping("/member/findaccountid")
    public String findAccountId(Model model){

        return "member/findAccountId";
    }

    @GetMapping("/member/findaccountpw")
    public String findAccountPassword(Model model){

        return "member/findAccountPassword";
    }

    @ResponseBody
    @PostMapping("/member/findaccountpw")
    public String findAccountPasswordSubmit(@RequestParam("memberId") String memberId, @RequestParam("memberEmail")String memberEmail){

        // 받은 이메일로 아이디 찾기
        String id = memberService.getMemberIdByEmail(memberEmail);


        if(id.equals(memberId)){
            // 찾은 아이디가 입력받은 아이디와 같으면
            // 이메일로 임시 비밀번호 전송

            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();


//            Random random = new Random();
//            int checkNum = random.nextInt(888888) + 111111;

            // 메일 발송
            String setFrom = "wowinteresting234@gmail.com";
            String toMail = memberEmail;
            String title = "임시 비밀번호입니다.";
            String content =
                    "안녕하세요."+
                            "<br><br>"+
                            "임시 비밀번호는 "+generatedString+" 입니다."+
                            "<br>"+
                            "임시 비밀번호로 로그인 후 비밀번호 변경을 진행해주세요.";
            try{
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(setFrom);
                helper.setTo(toMail);
                helper.setSubject(title);
                helper.setText(content,true);
                mailSender.send(message);
                memberService.setMemberState(memberId, generatedString);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return "S";
        }
        else{
            // 다르면

            return "F";
        }
    }
    @ResponseBody
    @PostMapping("/member/emailAuthCheck/findid")
    public String emailAuthCheckFindIdFunction(String email, String emailAuthValue){
        if(emailAuthValue.equals(authData.get(email))){
            authData.replace(email, null);
            System.gc();
            return memberService.getMemberIdByEmail(email);
        }
        else{
            return "F";
        }
    }


    @ResponseBody
    @RequestMapping("/findid")
    public String findId() {

        return "findId";
    }


//    @RequestMapping("/findid")
//    public String findId() {
//
//        return "findId";
//    }



}