package com.planetpulse.api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

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
    private Integer yearIndicator;
    private BigDecimal co2Emissions;
    private BigDecimal renewableEnergy;
    private BigDecimal forestArea;
    private BigDecimal pm25Exposure;

    public Indicator(){} // required

    public Integer getIdIndicator() {
        return idIndicator;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getYearIndicator() {
        return yearIndicator;
    }

    public void setYearIndicator(Integer yearIndicator) {
        this.yearIndicator = yearIndicator;
    }

    public BigDecimal getCo2Emissions() {
        return co2Emissions;
    }

    public void setCo2Emissions(BigDecimal co2Emissions) {
        this.co2Emissions = co2Emissions;
    }

    public BigDecimal getRenewableEnergy() {
        return renewableEnergy;
    }

    public void setRenewableEnergy(BigDecimal renewableEnergy) {
        this.renewableEnergy = renewableEnergy;
    }

    public BigDecimal getForestArea() {
        return forestArea;
    }

    public void setForestArea(BigDecimal forestArea) {
        this.forestArea = forestArea;
    }

    public BigDecimal getPm25Exposure() {
        return pm25Exposure;
    }

    public void setPm25Exposure(BigDecimal pm25Exposure) {
        this.pm25Exposure = pm25Exposure;
    }
}
