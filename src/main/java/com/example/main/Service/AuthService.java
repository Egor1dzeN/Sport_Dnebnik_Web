package com.example.main.Service;

import com.example.main.Entity.Role;
import com.example.main.Entity.User;
import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.SignInRequest;
import com.example.main.Object.SignUpRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    public JwtTokenResponse signUp(SignUpRequest request){
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);
        try {
            userService.create(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
}
