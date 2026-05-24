package com.gscience.security.proxy.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import waffle.servlet.WindowsPrincipal;

import java.util.Objects;

@Service
public class SecurityService {

    public void getCurrentWindowsUser() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if (principal instanceof WindowsPrincipal) {
            WindowsPrincipal windowsPrincipal = (WindowsPrincipal) principal;


            String fqdnUsername = windowsPrincipal.getName();

            // Get the underlying Windows identity
            // This gives you access to groups, SID, etc.
            var windowsIdentity = windowsPrincipal.getIdentity();
            String userSid = windowsIdentity.getSidString();

            System.out.println("Logged in Windows User: " + fqdnUsername);
            System.out.println("User SID: " + userSid);
        } else {
            System.out.println("Current user is not authenticated via Windows/Waffle.");
        }
    }
}