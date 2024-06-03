package com.haimlem.footballmanagementapp.repository;

import com.haimlem.footballmanagementapp.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
