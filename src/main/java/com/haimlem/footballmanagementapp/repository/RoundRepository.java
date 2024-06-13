package com.haimlem.footballmanagementapp.repository;

import com.haimlem.footballmanagementapp.entity.Round;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {
    List<Round> findByLeagueId(Long leagueId);
    @Transactional
    void deleteByLeagueId(Long leagueId);
}
