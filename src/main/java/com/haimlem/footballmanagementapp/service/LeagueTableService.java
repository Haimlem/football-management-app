package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.LeagueTableDTO;
import com.haimlem.footballmanagementapp.entity.League;
import com.haimlem.footballmanagementapp.entity.Match;

import java.util.List;
import java.util.Optional;

public interface LeagueTableService {
    void updateLeagueTable(Match match);
    List<LeagueTableDTO> getLeagueTable(Optional<League> league);
}
