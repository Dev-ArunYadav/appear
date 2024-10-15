package com.tarunainfosoft.controller;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import com.tarunainfosoft.service.AppearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> registerUser(@RequestBody Appear appear) {
        String savedAppear = appearService.registerUser(appear);
        return ResponseEntity.ok(savedAppear);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        boolean verified = appearService.verifyUser(token);
        return verified ? ResponseEntity.ok("User verified successfully") : ResponseEntity.badRequest().body("Invalid token");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Appear user = appearService.loginUser(email, password);
        return user != null ? ResponseEntity.ok("Login successful") : ResponseEntity.badRequest().body("Invalid credentials");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Appear> getApiById(@PathVariable Long id){
        Optional<Appear> appear = appearService.get(id);

        return appear.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
