package com.festivalP.demo.filter;

import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class LoginFilter implements Filter {

//    private static final String[] unAuthList = {"/","/member/signup", "/board/allfestival","/**"};
    private static final String[] unAuthList = {"/**"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest =  (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        HttpSession session = httpServletRequest.getSession(false);

        try {

            if(!PatternMatchUtils.simpleMatch(unAuthList, requestURI)){
                // unAuthList에 있는 URI와 requestURI가 매치되지 않는다면 (인증이 필요한 )

                System.out.println(session.getAttribute("authInfo"));
                if(session.getAttribute("authInfo")==null){
//                    System.out.println("#### session is null!!!");
//                    System.out.println("### rerquestURI: "+requestURI);

                    httpServletResponse.sendRedirect("/");
                    return;
                }
            }

//            System.out.println("########### Login FILTER out ");
//            System.out.println("########################### ");
        } catch(Exception e){

        }

        chain.doFilter(request, response);

    }
}
