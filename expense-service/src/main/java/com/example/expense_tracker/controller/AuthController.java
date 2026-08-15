package com.example.expense_tracker.controller;
import com.example.expense_tracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        authService.register(request.get("username"), request.get("password"));
        return "Kullanıcı başarıyla kaydedildi";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        return authService.login(request.get("username"), request.get("password"));
    }
}