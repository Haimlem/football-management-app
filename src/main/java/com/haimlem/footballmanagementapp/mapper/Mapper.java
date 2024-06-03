package com.haimlem.footballmanagementapp.mapper;

import com.haimlem.footballmanagementapp.dto.*;
import com.haimlem.footballmanagementapp.entity.*;
import com.haimlem.footballmanagementapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private RoundRepository roundRepository;

    public PlayerDTO toPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setAge(player.getAge());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setShirtNo(player.getShirtNo());
        playerDTO.setSalary(player.getSalary());
        playerDTO.setCurrentTeamId(player.getCurrentTeam() != null ? player.getCurrentTeam().getId() : null);
        return playerDTO;
    }

    public Player fromPlayerDTO(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        player.setPosition(playerDTO.getPosition());
        player.setShirtNo(playerDTO.getShirtNo());
        player.setSalary(playerDTO.getSalary());
        if (playerDTO.getCurrentTeamId() != null) {
            Team team = teamRepository.findById(playerDTO.getCurrentTeamId()).orElse(null);
            player.setCurrentTeam(team);
        }
        return player;
    }

    public TeamDTO toTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setCountry(team.getCountry());
        teamDTO.setLeagueId(team.getLeague() != null ? team.getLeague().getId() : null);
        if (!CollectionUtils.isEmpty(team.getHomeMatches())) {
            teamDTO.setHomeMatchIds(team.getHomeMatches().stream().map(Match::getId).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(team.getAwayMatches())) {
            teamDTO.setAwayMatchIds(team.getAwayMatches().stream().map(Match::getId).collect(Collectors.toList()));
        }
        if(!CollectionUtils.isEmpty(team.getPlayers())) {
            teamDTO.setPlayers(team.getPlayers().stream().map(this::toPlayerDTO).collect(Collectors.toList()));
            teamDTO.setPlayerIds(team.getPlayers().stream().map(Player::getId).collect(Collectors.toList()));
        }
        return teamDTO;
    }

    public Team fromTeamDTO(TeamDTO teamDTO) {
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setCountry(teamDTO.getCountry());
        if (teamDTO.getLeagueId() != null) {
            League league = leagueRepository.findById(teamDTO.getLeagueId()).orElse(null);
            team.setLeague(league);
        }
        if(!CollectionUtils.isEmpty(teamDTO.getHomeMatchIds())) {
            List<Match> homeMatches = teamDTO.getHomeMatchIds().stream()
                    .map(matchRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            team.setHomeMatches(homeMatches);
        }
        if(!CollectionUtils.isEmpty(teamDTO.getAwayMatchIds())) {
            List<Match> awayMatches = teamDTO.getAwayMatchIds().stream()
                    .map(matchRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            team.setAwayMatches(awayMatches);
        }
        if(!CollectionUtils.isEmpty(teamDTO.getPlayerIds())) {
            List<Player> players = teamDTO.getPlayerIds().stream()
                    .map(playerRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            team.setPlayers(players);
        }

        return team;
    }

    public LeagueDTO toLeagueDTO(League league) {
        LeagueDTO leagueDTO = new LeagueDTO();
        leagueDTO.setId(league.getId());
        leagueDTO.setName(league.getName());
        leagueDTO.setCountry(league.getCountry());
        if(!CollectionUtils.isEmpty(league.getTeams())) {
            leagueDTO.setTeams(league.getTeams().stream().map(this::toTeamDTO).collect(Collectors.toList()));
            leagueDTO.setTeamIds(league.getTeams().stream().map(Team::getId).collect(Collectors.toList()));
        }
        if(!CollectionUtils.isEmpty(league.getRounds())) {
            leagueDTO.setRounds(league.getRounds().stream().map(this::toRoundDTO).collect(Collectors.toList()));
        }
        return leagueDTO;
    }

    public League fromLeagueDTO(LeagueDTO leagueDTO) {
        League league = new League();
        league.setId(leagueDTO.getId());
        league.setName(leagueDTO.getName());
        league.setCountry(leagueDTO.getCountry());
        if(!CollectionUtils.isEmpty(leagueDTO.getTeamIds())) {
            List<Team> teams = leagueDTO.getTeamIds().stream()
                    .map(teamRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            league.setTeams(teams);
        }
        return league;
    }

    public MatchDTO toMatchDTO(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setHomeTeamId(match.getHomeTeam() != null ? match.getHomeTeam().getId() : null);
        matchDTO.setAwayTeamId(match.getAwayTeam() != null ? match.getAwayTeam().getId() : null);
        matchDTO.setHomeTeamGoals(match.getHomeTeamGoals());
        matchDTO.setAwayTeamGoals(match.getAwayTeamGoals());
        matchDTO.setRoundId(match.getRound() != null ? match.getRound().getRoundNumber() : null);
        return matchDTO;
    }

    public Match fromMatchDTO(MatchDTO matchDTO) {
        Match match = new Match();
        match.setId(matchDTO.getId());
        if (matchDTO.getHomeTeamId() != null) {
            Team homeTeam = teamRepository.findById(matchDTO.getHomeTeamId()).orElse(null);
            match.setHomeTeam(homeTeam);
        }
        if (matchDTO.getAwayTeamId() != null) {
            Team awayTeam = teamRepository.findById(matchDTO.getAwayTeamId()).orElse(null);
            match.setAwayTeam(awayTeam);
        }
        match.setHomeTeamGoals(matchDTO.getHomeTeamGoals());
        match.setAwayTeamGoals(matchDTO.getAwayTeamGoals());
        if (matchDTO.getRoundId() != null) {
           Round currentRound = roundRepository.findById(matchDTO.getRoundId()).orElse(null);
           match.setRound(currentRound);
        }
        return match;
    }

    public RoundDTO toRoundDTO(Round round) {
        RoundDTO roundDTO = new RoundDTO();
        roundDTO.setRoundNumber(round.getRoundNumber());
        roundDTO.setLeagueId(round.getLeague() != null ? round.getLeague().getId() : null);
        if(!CollectionUtils.isEmpty(round.getMatches())) {
            roundDTO.setMatches(round.getMatches().stream().map(this::toMatchDTO).collect(Collectors.toList()));
        }
        return roundDTO;
    }

    public Round fromRoundDTO(RoundDTO roundDTO) {
        Round round = new Round();
        round.setRoundNumber(roundDTO.getRoundNumber());
        if (roundDTO.getLeagueId() != null) {
            League league = leagueRepository.findById(roundDTO.getLeagueId()).orElse(null);
            round.setLeague(league);
        }
        return round;
    }
}
