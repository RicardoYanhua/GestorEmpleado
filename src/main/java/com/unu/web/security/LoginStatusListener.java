package com.unu.web.security;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginStatusListener implements ApplicationListener<ApplicationEvent> {

    public static boolean isUserLogin = false;

    @Override
    public void onApplicationEvent(ApplicationEvent  event) {
        if (event instanceof AuthenticationSuccessEvent) {
            isUserLogin = true; 
        } else if (event instanceof LogoutSuccessEvent) {
            isUserLogin = false;
        }
    }
}