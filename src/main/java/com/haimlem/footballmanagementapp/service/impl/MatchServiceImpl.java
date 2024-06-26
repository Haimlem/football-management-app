package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.MatchDTO;
import com.haimlem.footballmanagementapp.entity.Match;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.MatchRepository;
import com.haimlem.footballmanagementapp.service.LeagueTableService;
import com.haimlem.footballmanagementapp.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final LeagueTableService leagueTableService;
    private final Mapper mapper;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, LeagueTableService leagueTableService, Mapper mapper) {
        this.matchRepository = matchRepository;
        this.leagueTableService = leagueTableService;
//        this.leagueTableService = leagueTableService;
        this.mapper = mapper;
    }

    @Override
    public Optional<MatchDTO> getMatchById(Long id) {
        return matchRepository.findById(id).map(mapper::toMatchDTO);
    }

    @Override
    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(mapper::toMatchDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MatchDTO updateMatchScore(Long id, MatchDTO matchDTO) {
        Optional<Match> matchOpt = matchRepository.findById(id);
        if (matchOpt.isEmpty()) {
            throw new IllegalArgumentException("Match not found");
        }

        Match match = matchOpt.get();
        match.setHomeTeamGoals(matchDTO.getHomeTeamGoals());
        match.setAwayTeamGoals(matchDTO.getAwayTeamGoals());
        match = matchRepository.save(match);

        leagueTableService.updateLeagueTable(match);

        return mapper.toMatchDTO(match);
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
