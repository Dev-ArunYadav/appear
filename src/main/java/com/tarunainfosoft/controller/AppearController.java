package com.tarunainfosoft.controller;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import com.tarunainfosoft.service.AppearService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appear")
public class AppearController {
    @Autowired
    private AppearService appearService;

    @Autowired
    private AppearRepository appearRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Appear appear) throws MessagingException, UnsupportedEncodingException {
        String savedAppear = appearService.registerUser(appear);
        return ResponseEntity.ok(savedAppear);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        boolean verified = appearService.verifyUser(token);
        return verified ? ResponseEntity.ok("User verified successfully") : ResponseEntity.badRequest().body("Invalid token");
    }

   /* @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Appear user = appearService.loginUser(email, password);
        return user != null ? ResponseEntity.ok("Login successful") : ResponseEntity.badRequest().body("Invalid credentials");
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Appear appear) {
        Optional<Appear> user = appearService.loginUser(appear.getEmail(), appear.getPassword());
        return user.map(value -> ResponseEntity.ok("Login Succesfully : " + value.getFirstName())).orElseGet(() -> ResponseEntity.badRequest().body("Invalid credentials"));
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<Appear> getApiById(@PathVariable String email){
        Optional<Appear> appear = appearService.get(email);
        return appear.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
