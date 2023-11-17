package com.example.demo.Controller;

import com.example.demo.DTO.RegistrationDTO;
import com.example.demo.Service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationDTO requestDTO){
        return registrationService.register(requestDTO);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token")String token){
        return registrationService.confirmToken(token);
    }
}
