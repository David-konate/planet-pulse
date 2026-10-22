package com.planetpulse.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_indicators")
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // the database creates the id
    private Integer idIndicator;

    // Many indicator rows belong to one country (foreign key id_country)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_country")
    private Country country;

    @Column(name = "year_indicator")
    private Integer yearIndicator;

    @Column(name = "co2_emissions")
    private BigDecimal co2Emissions;

    @Column(name = "renewable_energy")
    private BigDecimal renewableEnergy;

    @Column(name = "forest_area")
    private BigDecimal forestArea;

    @Column(name = "pm25_exposure")
    private BigDecimal pm25Exposure;

}
