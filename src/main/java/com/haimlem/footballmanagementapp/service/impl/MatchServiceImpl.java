package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.MatchDTO;
import com.haimlem.footballmanagementapp.entity.Match;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.MatchRepository;
import com.haimlem.footballmanagementapp.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final Mapper mapper;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, Mapper mapper) {
        this.matchRepository = matchRepository;
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
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match match = mapper.fromMatchDTO(matchDTO);
        match.setId(id);
        match = matchRepository.save(match);
        return mapper.toMatchDTO(match);
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
