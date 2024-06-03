package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.MatchDTO;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    Optional<MatchDTO> getMatchById(Long id);
    List<MatchDTO> getAllMatches();
    MatchDTO updateMatchScore(Long id, MatchDTO matchDTO);
    void deleteMatch(Long id);

}
