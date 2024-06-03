package com.haimlem.footballmanagementapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq")
    private Long id;

    private String name;

    private int age;

    private String position;

    private int shirtNo;

    private double salary;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team currentTeam;

}
