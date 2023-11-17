package com.example.demo.Service;

import com.example.demo.Entity.Tokens;
import com.example.demo.Repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveTokens(Tokens token){
        tokenRepository.save(token);
    }

    public Optional<Tokens> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public int  setConfirmedAt(String token){
        return tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
