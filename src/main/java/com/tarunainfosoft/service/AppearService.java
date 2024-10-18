package com.tarunainfosoft.service;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import jakarta.mail.MessagingException;
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

    public String registerUser(Appear appear) throws MessagingException {    //register appear

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

    public Optional<Appear> loginUser(String email, String password) {
        Optional<Appear> user = appearRepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(password) && user.get().isVerified()) {
            return user;
        }
        return Optional.empty();
    }

    public void delete(Appear appear) {    //delete appear
        appearRepository.delete(appear);
    }

    public void update(Appear appear) {    //update appear
        appearRepository.save(appear);
    }

    public Optional<Appear> get(String email) {    //get appear
        return appearRepository.findByEmail(email);
    }

    public void getAll() {    //get all appear
        appearRepository.findAll();
    }
}
