package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.RoundDTO;
import com.haimlem.footballmanagementapp.entity.Round;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.RoundRepository;
import com.haimlem.footballmanagementapp.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final Mapper mapper;

    @Autowired
    public RoundServiceImpl(RoundRepository roundRepository, Mapper mapper) {
        this.roundRepository = roundRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<RoundDTO> getRoundById(Integer id) {
        return roundRepository.findById(id).map(mapper::toRoundDTO);
    }

    @Override
    public List<RoundDTO> getAllRounds() {
        return roundRepository.findAll().stream()
                .map(mapper::toRoundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoundDTO updateRound(Integer id, RoundDTO roundDTO) {
        Round round = mapper.fromRoundDTO(roundDTO);
        round.setRoundNumber(id);
        round = roundRepository.save(round);
        return mapper.toRoundDTO(round);
    }

    @Override
    public void deleteRound(Integer id) {
        roundRepository.deleteById(id);
    }
}
