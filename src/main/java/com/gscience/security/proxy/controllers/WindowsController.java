package com.gscience.security.proxy.controllers;

import com.gscience.security.proxy.dto.CurrentWindowsDetailDTO;
import com.gscience.security.proxy.service.WindowsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class WindowsController {

    private final WindowsUserService windowsUserSv;


    @PostMapping("/windows-login")
    public ResponseEntity<Object> windowsLoginDirect() {


        //windowsUserSv.getWindowsAccount();
        Map<String, String> response = new HashMap<>();
/*
        Map<String, String> response = new HashMap<>();
        response.put("token", "jwtToken");
        response.put("username", windowsUserSv.currentWindowsUser()); // Bevat nu bijv. "DOMAIN\mgerrit2"
*/
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current_detail")
    public ResponseEntity<CurrentWindowsDetailDTO> currentWindowsDetail()
    {

        return ResponseEntity.ok(windowsUserSv.currentWindowsUser());
    }

}
