package com.planetpulse.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "t_targets")
@Getter
@Setter
@NoArgsConstructor
public class Targets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_target")
    private Integer idTarget;

    // Plusieurs objectifs pour un même pays
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_country", nullable = false)
    private Country country;

    @Column(name = "year_", nullable = false)
    private Integer year;

    @Column(name = "co2_emissions", precision = 15, scale = 2)
    private BigDecimal co2Emissions;

    @Column(name = "pm25_emissions", precision = 15, scale = 2)
    private BigDecimal pm25Emissions;

    @Column(name = "forest_area", precision = 5, scale = 2)
    private BigDecimal forestArea;

    @Column(name = "renewable_energy_share", precision = 5, scale = 2)
    private BigDecimal renewableEnergyShare;
}