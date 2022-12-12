//package com.festivalP.demo.filter;
//
//import com.festivalP.demo.form.AuthInfo;
//import org.springframework.util.PatternMatchUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//
//public class MemberFilter implements Filter {
//
//
//
//    // 나중에 편집 요망 (비회원도 전체축제, 지역별 축제는 봐야하니께)
//    private static final String[] whiteList={"/", "/member/login", "/member/signup", "/logout", "/css/*"};
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String requestURI = httpServletRequest.getRequestURI();
//
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        try{
//            System.out.println("@@@ Member filter start");
//
//            if(isLoginCheckPath(requestURI)){
//                HttpSession session = httpServletRequest.getSession();
//
//                AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
//
//
//                if(authInfo.getState()<1|| session==null || session.getAttribute("authInfo")==null){
//                    System.out.println("@@@ oh shit Member filter ");
//
//                    httpServletResponse.sendRedirect("/");
//                    return;
//                }
//            }
//
//            chain.doFilter(request, response);
//        }catch (Exception e){
//            throw e;
//        }
//        finally {
//            System.out.println("@@@ Member filter close");
//        }
//    }
//
//
//    // 화이트리스트
//    private boolean isLoginCheckPath(String requestURI){
//        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
//    }
//}
