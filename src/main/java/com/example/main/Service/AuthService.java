package com.example.main.Service;

import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.User;
import com.example.main.domain.DTO.JwtTokenResponse;
import com.example.main.domain.DTO.SignInRequest;
import com.example.main.domain.DTO.SignUpRequest;
import com.example.main.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;

    public JwtTokenResponse signUp(SignUpRequest request){
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        User user1 = userService.create(user);
        if (user1 == null){
            return null;
        }
        var jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        return new JwtTokenResponse(jwt);
    }
    public JwtTokenResponse signIn(SignInRequest request){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            var user = userService.userDetailsService().loadUserByUsername(request.getUsername());
            var jwt = jwtService.generateToken(user);
            return new JwtTokenResponse(jwt);
        }
        return null;
    }
    public JwtTokenResponse signInVk(String username){
        System.out.println("asdhyasgdytgasdt7ygasd");
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,""
        ));
        System.out.println("dauyihsdayusgda");
        if (authentication.isAuthenticated()) {
            var user = userService.userDetailsService().loadUserByUsername(username);
            var jwt = jwtService.generateToken(user);
            System.out.println("JWT - "+jwt);
            return new JwtTokenResponse(jwt);
        }
        System.out.println("NO JWT");
        return null;
    }
}
