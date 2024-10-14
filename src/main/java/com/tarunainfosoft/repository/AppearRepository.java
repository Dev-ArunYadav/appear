package com.tarunainfosoft.repository;

import com.tarunainfosoft.entity.Appear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppearRepository extends JpaRepository<Appear, Long> {
}
