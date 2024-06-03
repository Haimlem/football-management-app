package com.haimlem.footballmanagementapp.controller;

import com.haimlem.footballmanagementapp.dto.RoundDTO;
import com.haimlem.footballmanagementapp.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rounds")
public class RoundController {
    private final RoundService roundService;

    @Autowired
    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoundDTO> getRoundById(@PathVariable Integer id) {
        Optional<RoundDTO> round = roundService.getRoundById(id);
        return round.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoundDTO>> getAllRounds() {
        List<RoundDTO> rounds = roundService.getAllRounds();
        return ResponseEntity.ok(rounds);
    }

    @PutMapping("/{id}")
        public ResponseEntity<RoundDTO> updateRound(@PathVariable Integer id, @RequestBody RoundDTO roundDTO) {
        RoundDTO updatedRound = roundService.updateRound(id, roundDTO);
        return ResponseEntity.ok(updatedRound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRound(@PathVariable Integer id) {
        roundService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }
}
