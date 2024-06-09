package com.haimlem.footballmanagementapp.repository;

import com.haimlem.footballmanagementapp.entity.Match;
import com.haimlem.footballmanagementapp.entity.Round;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Transactional
    void deleteByRound(Round round);
    List<Match> findByLeagueId(Long leagueId);
}
