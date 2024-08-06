package com.example.homescapebackend.controller;

import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.entity.Role;
import com.example.homescapebackend.pojo.AuthResponsePojo;
import com.example.homescapebackend.pojo.CustomerPojo;
import com.example.homescapebackend.repo.CustomerRepo;
import com.example.homescapebackend.repo.RoleRepository;
import com.example.homescapebackend.security.JwtGenerator;
import com.example.homescapebackend.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerRepo customerRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final CustomerService cus;




    @PostConstruct
    public void init() {
        cus.createAdminAccountIfNotExists();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
        }

        try {
            if (jwtGenerator.validateToken(refreshToken)) {
                String username = jwtGenerator.getUsernameFromJwt(refreshToken);
                String newAccessToken = jwtGenerator.generateToken(username);
                return ResponseEntity.ok(new AuthResponsePojo(newAccessToken, refreshToken, null, null));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponsePojo> login(@RequestBody CustomerPojo loginPojo) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPojo.getUsername(), loginPojo.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtGenerator.generateToken(loginPojo.getUsername());
        String refreshToken = jwtGenerator.generateRefreshToken(loginPojo.getUsername());

        Customer user = customerRepo.findByUsername(loginPojo.getUsername()).orElseThrow();
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        AuthResponsePojo response = new AuthResponsePojo(accessToken, refreshToken, user.getId(), roles);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/user")
    public ResponseEntity<String> registerUser(@RequestBody CustomerPojo registerPojo){
        if(customerRepo.existsByUsername(registerPojo.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        Customer user = new Customer();
        user.setId(registerPojo.getId());
        user.setUsername(registerPojo.getUsername());
        user.setPassword(passwordEncoder.encode(registerPojo.getPassword()));

        Optional<Role> role = roleRepository.findByName("USER");
        if(role.isPresent()) {
            user.setRoles(Collections.singletonList(role.get()));
        } else {
            return new ResponseEntity<>("Role not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        customerRepo.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> register(@RequestBody CustomerPojo registerPojo){
        if(customerRepo.existsByUsername(registerPojo.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        Customer user = new Customer();
        user.setId(registerPojo.getId());
        user.setUsername(registerPojo.getUsername());
        user.setPassword(passwordEncoder.encode(registerPojo.getPassword()));

        Optional<Role> role = roleRepository.findByName("ADMIN");
        if(role.isPresent()) {
            user.setRoles(Collections.singletonList(role.get()));
        } else {
            return new ResponseEntity<>("Role not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        customerRepo.save(user);
        return new ResponseEntity<>("ADMIN registered successfully", HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody CustomerPojo request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtGenerator.generateToken(request.getUsername());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint(@RequestHeader("Authorization") String token) {
        if (!jwtGenerator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtGenerator.getUsernameFromJwt(token);

        return ResponseEntity.ok("Hello, " + username + "! This is a secured endpoint.");
    }
}
