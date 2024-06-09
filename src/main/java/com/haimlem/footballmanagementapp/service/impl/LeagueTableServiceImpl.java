package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.LeagueTableDTO;
import com.haimlem.footballmanagementapp.entity.*;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.LeagueTableRepository;
import com.haimlem.footballmanagementapp.repository.MatchRepository;
import com.haimlem.footballmanagementapp.repository.TeamRepository;
import com.haimlem.footballmanagementapp.service.LeagueTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeagueTableServiceImpl implements LeagueTableService {

    private final LeagueTableRepository leagueTableRepository;
    private final Mapper mapper;

    @Autowired
    public LeagueTableServiceImpl(LeagueTableRepository leagueTableRepository, Mapper mapper) {
        this.leagueTableRepository = leagueTableRepository;
        this.mapper = mapper;
    }

    @Override
    public void updateLeagueTable(Match match) {
        League league = match.getLeague();
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();
        int homeGoals = match.getHomeTeamGoals();
        int awayGoals = match.getAwayTeamGoals();

        LeagueTable homeLeagueTable = leagueTableRepository.findByLeagueAndTeam(league, homeTeam);
        LeagueTable awayLeagueTable = leagueTableRepository.findByLeagueAndTeam(league, awayTeam);

        if (homeLeagueTable == null) {
            homeLeagueTable = createNewLeagueTable(league, homeTeam);
        }

        if (awayLeagueTable == null) {
            awayLeagueTable = createNewLeagueTable(league, awayTeam);
        }

        updateTeamStats(homeLeagueTable, homeGoals, awayGoals);
        updateTeamStats(awayLeagueTable, awayGoals, homeGoals);

        leagueTableRepository.save(homeLeagueTable);
        leagueTableRepository.save(awayLeagueTable);
    }

    private LeagueTable createNewLeagueTable(League league, Team team) {
        LeagueTable leagueTable = new LeagueTable();
        leagueTable.setLeague(league);
        leagueTable.setTeam(team);
        return leagueTable;
    }

    private void updateTeamStats(LeagueTable leagueTable, int goalsFor, int goalsAgainst) {
        leagueTable.setPlayed(leagueTable.getPlayed() + 1);
        leagueTable.setGoalsFor(leagueTable.getGoalsFor() + goalsFor);
        leagueTable.setGoalsAgainst(leagueTable.getGoalsAgainst() + goalsAgainst);
        leagueTable.setGoalDifference(leagueTable.getGoalsFor() - leagueTable.getGoalsAgainst());

        if (goalsFor > goalsAgainst) {
            leagueTable.setWon(leagueTable.getWon() + 1);
            leagueTable.setPoints(leagueTable.getPoints() + 3);
        } else if (goalsFor < goalsAgainst) {
            leagueTable.setLost(leagueTable.getLost() + 1);
        } else {
            leagueTable.setDrawn(leagueTable.getDrawn() + 1);
            leagueTable.setPoints(leagueTable.getPoints() + 1);
        }
    }

    @Override
    public List<LeagueTableDTO> getLeagueTable(Optional<League> league) {
        List<LeagueTable> leagueTables = leagueTableRepository.findByLeague(league);

        if (leagueTables == null || leagueTables.isEmpty()) {
            return Collections.emptyList();
        }

        return leagueTables.stream()
                .sorted(Comparator.comparing(LeagueTable::getPoints, Comparator.reverseOrder())
                        .thenComparing(LeagueTable::getGoalDifference, Comparator.reverseOrder())
                        .thenComparing(LeagueTable::getGoalsFor, Comparator.reverseOrder())
                        .thenComparing(leagueTable -> leagueTable.getTeam().getName()))
                .map(mapper::toLeagueTableDTO)
                .collect(Collectors.toList());
    }
}
