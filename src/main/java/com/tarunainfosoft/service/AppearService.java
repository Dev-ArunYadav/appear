package com.tarunainfosoft.service;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
//import java.util.UUID;

@Service
public class AppearService {

    @Autowired
    private AppearRepository appearRepository;

   // @Autowired
   // private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private final Map<String, Appear> pendingUsers = new HashMap<>();

    public String registerUser(Appear appear) {    //register appear

        if(appearRepository.findByEmail(appear.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists with same email");
        }
        if (!appear.getPassword().equals(appear.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match. Please try Again!");
        }
            appear.setVerified(false);
            appear.setVerificationToken(UUID.randomUUID().toString());
            pendingUsers.put(appear.getVerificationToken(), appear);
            emailService.sendVerificationEmail(appear);
        return appear.getVerificationToken();
    }

    public boolean verifyUser(String token){
        Appear appear = pendingUsers.get(token);
        if(appear!=null){
            appear.setVerified(true);
            appear.setVerificationToken(null);
            pendingUsers.remove(token);
            appearRepository.save(appear);
            return true;
        }
        return false;
    }

    public Appear loginUser(String email, String password) {
        Optional<Appear> user = appearRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();   // Return the user if email and password match
        }
        return user.orElse(null);    //return null if user not found
    }
    public void delete(Appear appear) {    //delete appear
        appearRepository.delete(appear);
    }

    public void update(Appear appear) {    //update appear
        appearRepository.save(appear);
    }

    public Optional<Appear> get(Long id) {    //get appear
        return appearRepository.findById(id);
    }

    public void getAll() {    //get all appear
        appearRepository.findAll();
    }
}
