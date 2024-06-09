package com.haimlem.footballmanagementapp.repository;

import com.haimlem.footballmanagementapp.entity.League;
import com.haimlem.footballmanagementapp.entity.LeagueTable;
import com.haimlem.footballmanagementapp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueTableRepository extends JpaRepository<LeagueTable, Long> {
    List<LeagueTable> findByLeague(Optional<League> league);

    LeagueTable findByLeagueAndTeam(League league, Team team);
}
