package com.haimlem.footballmanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDTO {
    private Long id;
    private String name;
    private String country;

    private List<TeamDTO> teams;
    private List<Long> teamIds;

    private List<RoundDTO> rounds;
}
