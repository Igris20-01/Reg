package com.example.demo.Service;

import com.example.demo.Entity.Tokens;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
                "User with email %s not found";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException
                                (String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists){
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }



      String encodedPsw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPsw);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        Tokens tokens = new Tokens(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        user
        );

        tokenService.saveTokens(tokens);

//        TODO: SEND EMAIL

        return token;
    }

    public int enableUser(String email){
            return userRepository.enableUser(email);
    }
}
