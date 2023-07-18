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

    private static final String[] unAuthList = {"/**"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest =  (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        HttpSession session = httpServletRequest.getSession(false);

        try {

            if(!PatternMatchUtils.simpleMatch(unAuthList, requestURI)){

                if(session.getAttribute("authInfo")==null){


                    httpServletResponse.sendRedirect("/");
                    return;
                }
            }

        } catch(Exception e){

        }

        chain.doFilter(request, response);

    }
}
