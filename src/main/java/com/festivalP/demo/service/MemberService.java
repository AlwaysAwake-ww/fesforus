package com.festivalP.demo.service;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.festivalP.demo.domain.Member;
import com.festivalP.demo.domain.Notice;
import com.festivalP.demo.form.AuthInfo;
import com.festivalP.demo.form.MemberForm;
import com.festivalP.demo.repository.MemberRepository;
import com.festivalP.demo.repository.PageMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PageMemberRepository pageMemberRepository;

    @Transactional
    public Member updateMemberState(Long memberIndex) {

        Member member = memberRepository.findOne(memberIndex);
        if (member.getMemberState()==0){
            member.setMemberState(1);}
        else {
            member.setMemberState(0);
        }

        return member;
    }


    public Page<Member> paging(String keyword, Pageable pageable) {
        // Page<Posts> Pages= pageRepository.findAll(pageable);
        return pageMemberRepository.findByMemberIdContaining(keyword, pageable);
    }

    public Page<Member> findByMemberState(int memberState, Pageable pageable){
    return pageMemberRepository.findByMemberState(memberState, pageable);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    private Member encryptFunc(Member member){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = member.getMemberPw();
        String securePw = encoder.encode(pw);
        member.setMemberPw(securePw);
        return member;
    }


    @Transactional
    public String join(Member member){
        System.out.println("MemberService.join");
        memberRepository.save(encryptFunc(member));
        if(member.getMemberCategory()!="")
            memberRepository.saveCategory(member);

        return member.getMemberId();
    }

    @Transactional
    public boolean validateDuplicateMemberId(String memberId){
        List<Member> findMem = memberRepository.findById(memberId);
        if(!findMem.isEmpty()) {
            // 중복된 ID 있을 경우
            System.out.println("@@@@@@@ duplicate! @@@@");
            return false;
        }
        else{
            // 중복된 ID 없을 경우
            System.out.println("$$$$$$$$ no id in db$$$$$$$$$$");
            return true;
        }
    }

    @Transactional
    public boolean validateDuplicateMemberNickname(String memberNickname){
        List<Member> findMem = memberRepository.findByNickname(memberNickname);
        if(!findMem.isEmpty()) {
            // 중복된 ID 있을 경우
            System.out.println("@@@@@@@ duplicate! @@@@");
            return false;
        }
        else{
            // 중복된 닉네임 없을 경우
            System.out.println("$$$$$$$$ no nickname in db$$$$$$$$$$");
            return true;
        }
    }

    @Transactional
    public boolean validateDuplicateMemberEmail(String memberEmail){
        List<Member> findMem = memberRepository.findByEmail(memberEmail);

        if(!findMem.isEmpty()) {
            // 중복된 email 있을 경우
            System.out.println("@@@@@@@ email duplicate! @@@@");
            return false;
        }
        else{
            // 중복된 email 없을 경우
            System.out.println("$$$$$$$$ no email in db$$$$$$$$$$");
            return true;
        }
    }

    @Transactional
    public boolean memberExistCheck(String memberId, String memberPw){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<Member> findMem =memberRepository.findById(memberId);
        if(findMem.isEmpty()){
            return false;
        }
        else{
            return encoder.matches(memberPw,findMem.get(0).getMemberPw());
        }
    }

    @Transactional
    public String getMemberIdByEmail(String memberEmail){
        Member member = memberRepository.findByEmailOne(memberEmail);
        return member.getMemberId();
    }

    @Transactional
    public void setMemberState(String memberId, String tempPw){
        Member member = memberRepository.findById(memberId).get(0);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String securePw = encoder.encode(tempPw);

        memberRepository.memberStateUpdate(member.getMemberIndex(), securePw);
    }




    @Transactional
    public AuthInfo getMemberAuthInfo(String memberId){
        List<Member> findMem =memberRepository.findById(memberId);
        Member mem = findMem.get(0);

//        Member mem = memberRepository.findById(memberId);
        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(memberId);
        authInfo.setState(mem.getMemberState());

        return authInfo;
    }

    @Transactional
    public Member getMemberAllInfo(String memberId){
        List<Member> findMem =memberRepository.findById(memberId);
        Member mem = findMem.get(0);
        return mem;
    }

    @Transactional
    public Member updateInfo(Member member){
        Member resMember =  memberRepository.memberInfoUpdate(member);

        return resMember;
    }

    @Transactional

    public Member updatePassword(Member member){


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPw = encoder.encode(member.getMemberPw());
        Member resMember =  memberRepository.memberPasswordUpdate(member, newPw);
        return resMember;
    }

    @Transactional
    public Member deleteMember(Member member){

        memberRepository.memberDelete(member);
        return member;
    }



//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}