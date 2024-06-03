package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.RoundDTO;

import java.util.List;
import java.util.Optional;

public interface RoundService {
    Optional<RoundDTO> getRoundById(Integer id);
    List<RoundDTO> getAllRounds();
    RoundDTO updateRound(Integer id, RoundDTO roundDTO);
    void deleteRound(Integer id);
}
