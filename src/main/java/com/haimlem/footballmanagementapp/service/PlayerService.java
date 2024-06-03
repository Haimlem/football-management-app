package com.haimlem.footballmanagementapp.service;

import com.haimlem.footballmanagementapp.dto.PlayerDTO;
import com.haimlem.footballmanagementapp.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    PlayerDTO createPlayer (PlayerDTO playerDTO);
    List<PlayerDTO> getAllPlayers();
    Optional<PlayerDTO> getPlayerById(Long id);
    PlayerDTO updatePlayer(Long id, PlayerDTO newPlayerData);
    void deletePlayerById(Long id);
}
