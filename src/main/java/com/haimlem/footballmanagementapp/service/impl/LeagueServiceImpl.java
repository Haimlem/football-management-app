package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.LeagueDTO;
import com.haimlem.footballmanagementapp.entity.League;
import com.haimlem.footballmanagementapp.entity.Match;
import com.haimlem.footballmanagementapp.entity.Round;
import com.haimlem.footballmanagementapp.entity.Team;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.LeagueRepository;
import com.haimlem.footballmanagementapp.repository.MatchRepository;
import com.haimlem.footballmanagementapp.repository.RoundRepository;
import com.haimlem.footballmanagementapp.repository.TeamRepository;
import com.haimlem.footballmanagementapp.service.LeagueService;
import com.haimlem.footballmanagementapp.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final RoundRepository roundRepository;
    private final MatchRepository matchRepository;
    private final Mapper mapper;

    @Autowired
    public LeagueServiceImpl(LeagueRepository leagueRepository, TeamRepository teamRepository, RoundRepository roundRepository, MatchRepository matchRepository, Mapper mapper) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.roundRepository = roundRepository;
        this.matchRepository = matchRepository;
        this.mapper = mapper;
    }

    @Override
    public LeagueDTO createLeague(LeagueDTO leagueDTO) {
        League league = mapper.fromLeagueDTO(leagueDTO);
        league = leagueRepository.save(league);
        return mapper.toLeagueDTO(league);
    }

    @Override
    public Optional<LeagueDTO> getLeagueById(Long id) {
        return leagueRepository.findById(id).map(mapper::toLeagueDTO);
    }

    @Override
    public List<LeagueDTO> getAllLeagues() {
        return leagueRepository.findAll().stream()
                .map(mapper::toLeagueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeagueDTO updateLeague(Long id, LeagueDTO leagueDTO) {
        League league = mapper.fromLeagueDTO(leagueDTO);
        league.setId(id);
        league = leagueRepository.save(league);
        return mapper.toLeagueDTO(league);
    }

    @Override
    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }

    @Override
    public void randomizeRoundsAndMatches(Long leagueId) {
        Optional<League> currentLeague = findLeagueById(leagueId);
        if (currentLeague.isEmpty()) {
            throw new IllegalArgumentException("League not found");
        }

        League league = currentLeague.get();
        List<Team> teams = findTeamsByLeagueId(leagueId);
        validateTeams(teams);

        deleteExistingRoundsAndMatches(leagueId);

        generateRoundsAndMatches(league, teams);
    }

    private Optional<League> findLeagueById(Long leagueId) {
        return leagueRepository.findById(leagueId);
    }

    private List<Team> findTeamsByLeagueId(Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }

    private void validateTeams(List<Team> teams) {
        if (teams.size() < 2) {
            throw new IllegalArgumentException("Not enough teams to create matches");
        }
    }

    private void deleteExistingRoundsAndMatches(Long leagueId) {
        List<Round> existingRounds = roundRepository.findByLeagueId(leagueId);
        if (!existingRounds.isEmpty()) {
            existingRounds.forEach(matchRepository::deleteByRound);
            roundRepository.deleteByLeagueId(leagueId);
        }
    }

    private void generateRoundsAndMatches(League league, List<Team> teams) {
        List<Team> modifiedTeams = adjustTeamListForOddNumberOfTeams(teams);
        int numberOfRounds = calculateNumberOfRounds(modifiedTeams.size());

        List<Round> rounds = createRounds(league, numberOfRounds);
        roundRepository.saveAll(rounds);

        List<Match> matches = createMatches(modifiedTeams, rounds);
        matchRepository.saveAll(matches);
    }

    private List<Team> adjustTeamListForOddNumberOfTeams(List<Team> teams) {
        int numberOfTeams = teams.size();
        if (numberOfTeams % 2 != 0) {
            Team dummyTeam = createDummyTeam();
            teams.add(dummyTeam);
        }
        return teams;
    }

    private Team createDummyTeam() {
        Team dummyTeam = new Team();
        dummyTeam.setId(-1L); // Use a negative ID to distinguish the dummy team
        dummyTeam.setName("Bye");
        return dummyTeam;
    }

    private int calculateNumberOfRounds(int numberOfTeams) {
        return (numberOfTeams - 1) * 2;
    }

    private List<Round> createRounds(League league, int numberOfRounds) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 1; i <= numberOfRounds; i++) {
            Round round = new Round();
            round.setLeague(league);
            rounds.add(round);
        }
        return rounds;
    }

    private List<Match> createMatches(List<Team> teams, List<Round> rounds) {
        List<Match> matches = new ArrayList<>();
        int numberOfTeams = teams.size();
        int numberOfRounds = rounds.size();

        for (int roundNumber = 0; roundNumber < numberOfRounds / 2; roundNumber++) {
            for (int i = 0; i < numberOfTeams / 2; i++) {
                int homeIndex = (roundNumber + i) % (numberOfTeams - 1);
                int awayIndex = (numberOfTeams - 1 - i + roundNumber) % (numberOfTeams - 1);

                if (i == 0) {
                    awayIndex = numberOfTeams - 1;
                }

                Team homeTeam = teams.get(homeIndex);
                Team awayTeam = teams.get(awayIndex);

                if (!homeTeam.getId().equals(-1L) && !awayTeam.getId().equals(-1L)) {
                    Match match = createMatch(homeTeam, awayTeam, rounds.get(roundNumber));
                    matches.add(match);

                    match = createMatch(awayTeam, homeTeam, rounds.get(roundNumber + numberOfRounds / 2));
                    matches.add(match);
                }
            }
        }
        return matches;
    }

    private Match createMatch(Team homeTeam, Team awayTeam, Round round) {
        Match match = new Match();
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setRound(round);
        match.setLeague(round.getLeague()); // Ensure the match is associated with the league
        match.setHomeTeamGoals(0); // Initialize goals to 0
        match.setAwayTeamGoals(0); // Initialize goals to 0
        return match;
    }
}