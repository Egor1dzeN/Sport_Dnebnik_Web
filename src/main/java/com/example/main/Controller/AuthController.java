package com.example.main.Controller;

import com.example.main.MyException.InvalidDataException;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.Repository.VerifyAccountRepository;
import com.example.main.Service.VkAuthService;
import com.example.main.domain.DTO.PayloadVkAuth;
import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.User;
import com.example.main.domain.Entity.VkUser;
import com.example.main.domain.DTO.JwtTokenResponse;
import com.example.main.domain.DTO.SignInRequest;
import com.example.main.domain.DTO.SignUpRequest;
import com.example.main.Repository.UserRepository;
import com.example.main.Repository.VkUserRepository;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import com.example.main.Service.JwtService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@Data
public class AuthController {
    private final AuthService authService;
    private final VkUserRepository vkUserRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final VkAuthService vkAuthService;
    private final CookieService cookieService;
    private final VerifyAccountRepository verifyAccountRepository;

    private final Logger logger = LogManager.getLogger(AuthController.class);

    // Todo: add description endpoint method
    @PostMapping("/vk.auth")
    public String authVk(@ModelAttribute PayloadVkAuth payloadVkAuth, HttpServletResponse response) {
        String username = payloadVkAuth.getUsername();
        String secretKey = payloadVkAuth.getSecretKey();
        System.out.println(payloadVkAuth);
        VkUser vkUser = vkUserRepository.findBySecretKey(secretKey);
        System.out.println(vkUser);
        User user = new User(username, vkUser.getFirstName(), vkUser.getLastName(), vkUser.getUserVkId());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        System.out.println(user);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.generateToken(username));
        cookieService.addCookie(response, jwtTokenResponse);
        System.out.println(jwtTokenResponse);
        return "redirect:/";
    }

    @GetMapping("/vk.auth")
    public String method2(
            @RequestParam(value = "payload") String payloadStr, @RequestParam String state,
            Principal principal, Model model, HttpServletResponse response) {
        VkUser vkUser = vkAuthService.authWithVK(payloadStr);
        System.out.println("VkUSER" + vkUser);
        if (userRepository.existsByVkId(vkUser.getUserVkId())) {
            System.out.println("AUTH VK");
            User user = userRepository.findByVkId(vkUser.getUserVkId());
            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.generateToken(user.getUsername()));
            cookieService.addCookie(response, jwtTokenResponse);
            System.out.println(jwtTokenResponse);
            return "redirect:/";
        }
        model.addAttribute("secretKey", vkUser.getSecretKey());
        return "addUsername";


    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<JwtTokenResponse> signIn(@ModelAttribute SignInRequest signInRequest, HttpServletResponse response) {
        logger.info("{}", signInRequest);
        Optional<JwtTokenResponse> tokenResponse = authService.signIn(signInRequest);
        System.out.println(tokenResponse);
        if (tokenResponse.isPresent()) {
            cookieService.addCookie(response, tokenResponse.orElseThrow(() -> new UserNotFoundException("User wasn't found, username -" + signInRequest.getUsername())));
        } else {
            throw new UserNotFoundException("User wasn't found, username -" + signInRequest.getUsername());
        }
        return new ResponseEntity<>(tokenResponse.get(), HttpStatus.OK);


    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Principal principal) {
        logger.info("Logout {}", principal);
        cookieService.removeCookie(response);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String method3(Model model) {
        model.addAttribute("key_vk", "123451");
        return "login";
    }


    @GetMapping("/sign-up")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<?> signUp(@ModelAttribute SignUpRequest request) throws MessagingException {
        System.out.println(request);
        JwtTokenResponse tokenResponse = authService.signUp(request);
        if (tokenResponse == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/verify_account")
    @ResponseBody
    public Long verifyAccount(@RequestParam(name = "access_token") String token) {
        logger.info("Access token {}", token);
        Optional<Long> id = verifyAccountRepository.findUserIdByAccessToken(token);
        return id.orElseThrow(() -> new InvalidDataException("Access_token not found"));
    }

}
