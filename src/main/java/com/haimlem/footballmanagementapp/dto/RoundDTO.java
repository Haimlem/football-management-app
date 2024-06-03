package com.haimlem.footballmanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {
    private int roundNumber;

    private Long leagueId;

    private List<MatchDTO> matches;
}
