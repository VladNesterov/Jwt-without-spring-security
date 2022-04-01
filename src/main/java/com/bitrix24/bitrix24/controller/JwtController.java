package com.bitrix24.bitrix24.controller;

import com.bitrix24.bitrix24.dto.AuthRequest;
import com.bitrix24.bitrix24.dto.AuthResponse;
import com.bitrix24.bitrix24.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtController {
    @Autowired
    JWTService jwtService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthResponse loginUser(@RequestBody AuthRequest authRequest) {
        return jwtService.getAccessToken(authRequest);
    }
}
