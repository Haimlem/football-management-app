package com.haimlem.footballmanagementapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_id_seq")
    private Long id;

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    private int homeTeamGoals;
    private int awayTeamGoals;

    @ManyToOne
    @JoinColumn(name = "round_number")
    private Round round;
}
