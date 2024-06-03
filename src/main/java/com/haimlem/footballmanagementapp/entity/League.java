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
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "league_id_seq")
    private Long id;

    private String name;

    private String country;

    @OneToMany(mappedBy = "league")
    private List<Team> teams;

    @OneToMany(mappedBy = "league")
    private List<Round> rounds;
}
