package com.tarunainfosoft.repository;

import com.tarunainfosoft.entity.Appear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppearRepository extends JpaRepository<Appear, Long> {
    Optional<Appear> findByEmail(String email);
    Appear findByVerificationToken(String token);
    Appear findByResetToken(String token);
}
