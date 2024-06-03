package com.haimlem.footballmanagementapp.repository;

import com.haimlem.footballmanagementapp.entity.Round;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Integer> {
    List<Round> findByLeagueId(Long leagueId);
    @Transactional
    void deleteByLeagueId(Long leagueId);
}
