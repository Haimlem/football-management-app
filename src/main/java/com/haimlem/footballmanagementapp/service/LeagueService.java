package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.LeagueDTO;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    LeagueDTO createLeague(LeagueDTO leagueDTO);
    Optional<LeagueDTO> getLeagueById(Long id);
    List<LeagueDTO> getAllLeagues();
    LeagueDTO updateLeague(Long id, LeagueDTO leagueDTO);
    void deleteLeague(Long id);

    void randomizeRoundsAndMatches(Long leagueId);
}
