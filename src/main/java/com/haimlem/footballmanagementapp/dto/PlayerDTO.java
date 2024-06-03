package com.haimlem.footballmanagementapp.dto;

import com.haimlem.footballmanagementapp.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String name;
    private int age;
    private String position;
    private int shirtNo;
    private double salary;
    private Long currentTeamId;
}
