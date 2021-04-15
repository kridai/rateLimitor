package com.example.demo.framework;

import com.example.demo.core.RateLimiterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static String CLIENT_ID_HEADER = "X-Client-ID";
    @Autowired
    RateLimiterService rateLimiterService;
    Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        logger.info("RateLimitInterceptor is invoked");
        if(isRateLimitAPI(handler)) {
                logger.info("The Request: "+ request.getRequestURI()+ " has active rate limiter");
                String clientName = request.getHeader(CLIENT_ID_HEADER);
                if(clientName == null || clientName.equals("")) {
                    response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                    response.getOutputStream().write(objectMapper.writeValueAsBytes("please pass "+ CLIENT_ID_HEADER + " in request"));
                    return false;
                }
                if(rateLimiterService.limitReached(request.getRequestURI(), clientName)) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.getOutputStream().write(objectMapper.writeValueAsBytes("Your hourly Limit has reached"));
                    return false;
                }
                return true;
            }
        return true;
    }
    public boolean isRateLimitAPI(Object handler) {
        HandlerMethod  handlerMethod = (HandlerMethod) handler;
        if(handlerMethod != null && handlerMethod.getMethodAnnotation(RateLimiter.class) != null) {
            return true;
        }
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        String clientName = request.getHeader(CLIENT_ID_HEADER);
       if(isRateLimitAPI(handler)) {
           if(!rateLimiterService.updateLimit(clientName, request.getRequestURI())){
               logger.error("Unable to update rateLimitorStorage for client :", clientName);
           }
       }

    }

}
