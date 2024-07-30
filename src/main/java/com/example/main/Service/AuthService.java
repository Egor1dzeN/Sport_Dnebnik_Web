package com.example.main.Service;

import com.example.main.MyException.UserAlreadyExistException;
import com.example.main.MyException.UserEmailException;
import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.User;
import com.example.main.domain.DTO.JwtTokenResponse;
import com.example.main.domain.DTO.SignInRequest;
import com.example.main.domain.DTO.SignUpRequest;
import com.example.main.Repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final MailService mailService;
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public JwtTokenResponse signUp(SignUpRequest request) throws MessagingException {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setName(request.getName());
        user.setSurname(request.getSurname());

        User user1 = userService.create(user);
        String jwt = jwtService.generateToken(user1);
        logger.info("Send email {}", request.getEmail());
        mailService.sendVerifyLink(request.getEmail());
        return new JwtTokenResponse(jwt);
    }

    public Optional<JwtTokenResponse> signIn(SignInRequest request) {
        System.out.println("Sign In" + request);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        if (authentication.isAuthenticated()) {
            var user = userService.userDetailsService().loadUserByUsername(request.getUsername());
            var jwt = new JwtTokenResponse(jwtService.generateToken(user));
            System.out.println("Not empty");
            return Optional.of(jwt);
        }
        System.out.println("empty");
        return Optional.empty();
    }

    public JwtTokenResponse signInVk(String username) {
        System.out.println("asdhyasgdytgasdt7ygasd");
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, ""
        ));
        System.out.println("dauyihsdayusgda");
        if (authentication.isAuthenticated()) {
            var user = userService.userDetailsService().loadUserByUsername(username);
            var jwt = jwtService.generateToken(user);
            System.out.println("JWT - " + jwt);
            return new JwtTokenResponse(jwt);
        }
        System.out.println("NO JWT");
        return null;
    }
}
