package com.planetpulse.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_indicators")
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getIdIndicator() {
        return idIndicator;
    }

    public Country getCountry() {
        return country;
    }

    public Integer getYearIndicator() {
        return yearIndicator;
    }

    public BigDecimal getCo2Emissions() {
        return co2Emissions;
    }

    public BigDecimal getRenewableEnergy() {
        return renewableEnergy;
    }

    public BigDecimal getForestArea() {
        return forestArea;
    }

    public BigDecimal getPm25Exposure() {
        return pm25Exposure;
    }
}
