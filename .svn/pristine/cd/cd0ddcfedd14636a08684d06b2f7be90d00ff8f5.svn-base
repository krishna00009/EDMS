package com.hyva.bsfms.Interceptor;

import com.hyva.bsfms.bs.bsentities.User;
import com.hyva.bsfms.bs.bsrespositories.BsUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    BsUserRepository bsUserRepository;

    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request = " + request.getRequestURL());
        Cookie[] accessTokens = request.getCookies();
        Cookie accessTokenCookie = null;
        String accessToken = null;
        String user = null;
        if (accessTokens != null) {
            Stream<Cookie> cookieStream = Arrays.stream(accessTokens)
                    .filter(cookie -> cookie.getName().equalsIgnoreCase("email"));
            Optional<Cookie> first = cookieStream.findFirst();
            if (first.isPresent()) {
                accessTokenCookie = first.get();
                user = accessTokenCookie.getValue();
            }
        }

        if(user == null) {
            HttpSession session = request.getSession();
            user = (String) session.getAttribute("email");
        }

        if (StringUtils.isBlank(user)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return true;
        }
        Map<String, String> credentialsMap = null;
//        try {
        User sassUser =  bsUserRepository.findByUserName(user);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(518400);//extending time for 60 days
        response.addCookie(accessTokenCookie);
        if(sassUser != null) {
            request.setAttribute("userId", sassUser.getUseraccount_id());
            request.setAttribute("organizationId", sassUser.getOrganizationId());
            request.setAttribute("branchCode", sassUser.getBranchCode());
        }
        return true;
    }
}
