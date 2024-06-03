package com.haimlem.footballmanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Long id;

    private Long homeTeamId;
    private Long awayTeamId;

    private int homeTeamGoals;
    private int awayTeamGoals;

    private Integer roundId;
}
