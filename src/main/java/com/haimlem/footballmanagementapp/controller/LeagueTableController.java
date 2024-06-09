package com.haimlem.footballmanagementapp.controller;

import com.haimlem.footballmanagementapp.dto.LeagueTableDTO;
import com.haimlem.footballmanagementapp.entity.League;
import com.haimlem.footballmanagementapp.repository.LeagueRepository;
import com.haimlem.footballmanagementapp.service.LeagueTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/league-tables")
public class LeagueTableController {
    private final LeagueTableService leagueTableService;
    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueTableController(LeagueTableService leagueTableService, LeagueRepository leagueRepository) {
        this.leagueTableService = leagueTableService;
        this.leagueRepository = leagueRepository;
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<List<LeagueTableDTO>> getLeagueTable(@PathVariable Long leagueId) {
        Optional<League> league = leagueRepository.findById(leagueId);
        List<LeagueTableDTO> leagueTable = leagueTableService.getLeagueTable(league);
        return ResponseEntity.ok(leagueTable);
    }
}
