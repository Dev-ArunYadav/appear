package com.tarunainfosoft.service;

import com.tarunainfosoft.entity.Appear;
import com.tarunainfosoft.repository.AppearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppearService {

    @Autowired
    private AppearRepository appearRepository;

    public void add(Appear appear) {    //add appear
        appearRepository.save(appear);
    }

    public void delete(Appear appear) {    //delete appear
        appearRepository.delete(appear);
    }

    public void update(Appear appear) {    //update appear
        appearRepository.save(appear);
    }

    public Appear get(Long id) {    //get appear
        return appearRepository.findById(id).get();
    }

    public void getAll() {    //get all appear
        appearRepository.findAll();
    }
}
