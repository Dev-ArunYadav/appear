package com.tarunainfosoft.controller;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import com.tarunainfosoft.service.AppearService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/register")
    public String register(@RequestBody Appear appear){
        appearService.add(appear);
        return "appear";
    }

    @GetMapping("/getUser/{id}")
    public Optional<Appear> getApiById(@RequestBody Long id){
        return Optional.ofNullable(appearService.get(id));
    }

}
