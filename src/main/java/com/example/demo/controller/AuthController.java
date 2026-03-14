package com.example.demo.controller;

import com.example.demo.entity.Session;
import com.example.demo.entity.User;
import com.example.demo.model.LoginRequestDTO;
import com.example.demo.model.LoginResponseDTO;
import com.example.demo.model.LogoutRequestDTO;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(AuthRepository authRepository, UserRepository userRepository,SessionRepository sessionRepository,PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Login User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login User",
                    content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDTO.class)
            )
    )
    public Object login(@RequestBody LoginRequestDTO requestDTO){
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        Optional<User> userQuery = userRepository.findByEmail(email);
        if(userQuery.isEmpty()){
            loginResponseDTO.setMessage("Username or password is incorrect");
            loginResponseDTO.setStatus("401");
            loginResponseDTO.setSessionId("");
            loginResponseDTO.setEmail("");
            return loginResponseDTO;
        }
        User user = userQuery.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            loginResponseDTO.setMessage("Username or password is incorrect");
            loginResponseDTO.setStatus("401");
            loginResponseDTO.setSessionId("");
            loginResponseDTO.setEmail("");
            return loginResponseDTO;
        }
        String SessionId = generateSessionId();
        insertSessionId(user.getId(),SessionId);
        loginResponseDTO.setMessage("Login Successful");
        loginResponseDTO.setStatus("200");
        loginResponseDTO.setSessionId(SessionId);
        loginResponseDTO.setEmail(requestDTO.getEmail());
        return loginResponseDTO;

    }
    @PostMapping("/logout")
    @Operation(
            summary = "Logout NEw",
            description = "Logout User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Logout User",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDTO.class)
            )
    )
    public String logout(@RequestHeader("Session-Id") String sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId);
        session.setIsExpired(true);
        sessionRepository.save(session);
        return "Logout success";
    }

    public void insertSessionId(Integer userId, String sessionId){
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setExpiryAt(LocalDateTime.now().plusMinutes(30));
        session.setCreatedBy(userId);
        session.setIsExpired(false);
        sessionRepository.save(session);
    }

    public void checkSessionId(String auth){
        log.info("Session Id is {}", auth);
        if (auth == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error Unauthorized");
        }
        int isSession = sessionRepository.checkSession(auth);
        log.info("Session is {}", isSession);
        if(isSession == 0){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Error Unauthorized"
            );
        }
    }

    public String generateSessionId()
    {
        String result_String = "";

        Random Random_number_generator = new Random();

        int number_of_digits = 64;

        for(int i=0; i < number_of_digits; i++)
        {
            int randomNumber_0_to_9 = Random_number_generator.nextInt(10);
            result_String +=  String.valueOf(randomNumber_0_to_9);
        }
        return result_String;
    }
}
