package com.haimlem.footballmanagementapp.service.impl;

import com.haimlem.footballmanagementapp.dto.PlayerDTO;
import com.haimlem.footballmanagementapp.entity.Player;
import com.haimlem.footballmanagementapp.mapper.Mapper;
import com.haimlem.footballmanagementapp.repository.PlayerRepository;
import com.haimlem.footballmanagementapp.repository.TeamRepository;
import com.haimlem.footballmanagementapp.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final Mapper mapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, Mapper mapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.mapper = mapper;
    }

    @Override
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
       Player player = mapper.fromPlayerDTO(playerDTO);
       player = playerRepository.save(player);
       return mapper.toPlayerDTO(player);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream().map(mapper::toPlayerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<PlayerDTO> getPlayerById(Long id) {
        return playerRepository.findById(id).map(mapper::toPlayerDTO);
    }

    @Override
    public PlayerDTO updatePlayer(Long id, PlayerDTO newPlayerData) {
        Optional<Player> existingPlayerOptional = playerRepository.findById(id);
        if (existingPlayerOptional.isPresent()) {
            Player existingPlayer = existingPlayerOptional.get();
            existingPlayer.setName(newPlayerData.getName());
            existingPlayer.setAge(newPlayerData.getAge());
            existingPlayer.setPosition(newPlayerData.getPosition());
            existingPlayer.setSalary(newPlayerData.getSalary());
            existingPlayer.setShirtNo(newPlayerData.getShirtNo());
            if (newPlayerData.getCurrentTeamId() != null) {
                existingPlayer.setCurrentTeam(teamRepository.findById(newPlayerData.getCurrentTeamId()).orElse(null));
            }
            Player updatedPlayer = playerRepository.save(existingPlayer);
            return mapper.toPlayerDTO(updatedPlayer);
        } else {
            return null;
        }
    }

    @Override
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }
}
