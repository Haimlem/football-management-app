package com.haimlem.footballmanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private String name;
    private String country;

    private Long leagueId;

    private List<PlayerDTO> players;
    private List<Long> playerIds;

    private List<Long> homeMatchIds;
    private List<Long> awayMatchIds;
}
