package com.planetpulse.api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table( name = "t_targets",

        uniqueConstraints = @UniqueConstraint(
                name = "uk_target_country_year",
                columnNames = {"id_country", "year_"}
        ))

public class Targets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_target")
    private Integer idTarget;


    @Column(name = "id_country")
    private Integer countryId;

    @Column(name = "target_year")   // garde ici le nom réel de la colonne en base
    private Integer targetYear;

    @Column(name = "co2_emissions")
    private BigDecimal co2Emissions;

    @Column(name = "pm25_emissions")
    private BigDecimal pm25Emissions;

    @Column(name = "forest_area")
    private BigDecimal forestArea;

    @Column(name = "renewable_energy_share")
    private BigDecimal renewableEnergyShare;

    public Targets() {
    }

    public Integer getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(Integer targetYear) {
        this.targetYear = targetYear;
    }

    public BigDecimal getCo2Emissions() {
        return co2Emissions;
    }

    public void setCo2Emissions(BigDecimal co2Emissions) {
        this.co2Emissions = co2Emissions;
    }

    public BigDecimal getPm25Emissions() {
        return pm25Emissions;
    }

    public void setPm25Emissions(BigDecimal pm25Emissions) {
        this.pm25Emissions = pm25Emissions;
    }

    public BigDecimal getForestArea() {
        return forestArea;
    }

    public void setForestArea(BigDecimal forestArea) {
        this.forestArea = forestArea;
    }

    public BigDecimal getRenewableEnergyShare() {
        return renewableEnergyShare;
    }

    public void setRenewableEnergyShare(BigDecimal renewableEnergyShare) {
        this.renewableEnergyShare = renewableEnergyShare;
    }

}