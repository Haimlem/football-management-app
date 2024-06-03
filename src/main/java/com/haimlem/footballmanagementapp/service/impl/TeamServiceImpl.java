package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.TeamDTO;
import com.haimlem.footballmanagementapp.entity.League;
import com.haimlem.footballmanagementapp.entity.Team;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.LeagueRepository;
import com.haimlem.footballmanagementapp.repository.PlayerRepository;
import com.haimlem.footballmanagementapp.repository.TeamRepository;
import com.haimlem.footballmanagementapp.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final Mapper mapper;

    @Autowired
    public TeamServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, LeagueRepository leagueRepository, Mapper mapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.mapper = mapper;
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = mapper.fromTeamDTO(teamDTO);
        team = teamRepository.save(team);
        return mapper.toTeamDTO(team);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(mapper::toTeamDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TeamDTO> getTeamById(Long id) {
        return teamRepository.findById(id).map(mapper::toTeamDTO);
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        return teamRepository.findById(id).map(team -> {
            team.setName(teamDTO.getName());
            team.setCountry(teamDTO.getCountry());
            if (teamDTO.getLeagueId() != null) {
                League league = leagueRepository.findById(teamDTO.getLeagueId()).orElse(null);
                team.setLeague(league);
            }
            if(!CollectionUtils.isEmpty(teamDTO.getPlayerIds())){
                team.setPlayers(teamDTO.getPlayerIds().stream()
                        .map(playerId -> playerRepository.findById(playerId).orElse(null))
                        .collect(Collectors.toList()));
            } else {
                team.setPlayers(null);
            }
            team = teamRepository.save(team);
            return mapper.toTeamDTO(team);
        }).orElse(null);
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
