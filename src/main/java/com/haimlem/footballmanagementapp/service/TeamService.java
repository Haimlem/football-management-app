package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.TeamDTO;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO);
    List<TeamDTO> getAllTeams();
    Optional<TeamDTO> getTeamById(Long id);
    TeamDTO updateTeam(Long id, TeamDTO teamDTO);
    void deleteTeam(Long id);
}
