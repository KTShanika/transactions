package com.daofab.transactions.config;

import com.daofab.transactions.exception.DaoFabException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class DaoFabInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(DaoFabException.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Initializing the request");
        MDC.put("UUID", UUID.randomUUID().toString());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.info("Request completed");
        MDC.clear();
        super.postHandle(request, response, handler, modelAndView);
    }
}
