package com.haimlem.footballmanagementapp.controller;

import com.haimlem.footballmanagementapp.dto.LeagueDTO;
import com.haimlem.footballmanagementapp.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {
    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody LeagueDTO leagueDTO) {
        LeagueDTO createdLeague = leagueService.createLeague(leagueDTO);
        return ResponseEntity.ok(createdLeague);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable Long id) {
        Optional<LeagueDTO> league = leagueService.getLeagueById(id);
        return league.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getAllLeagues() {
        List<LeagueDTO> leagues = leagueService.getAllLeagues();
        return ResponseEntity.ok(leagues);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> updateLeague(@PathVariable Long id, @RequestBody LeagueDTO leagueDTO) {
        LeagueDTO updatedLeague = leagueService.updateLeague(id, leagueDTO);
        return ResponseEntity.ok(updatedLeague);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/randomize")
    public ResponseEntity<Void> randomizeRoundsAndMatches(@PathVariable Long id) {
        leagueService.randomizeRoundsAndMatches(id);
        return ResponseEntity.ok().build();
    }
}
