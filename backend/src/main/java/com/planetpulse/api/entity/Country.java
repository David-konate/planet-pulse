package com.planetpulse.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "t_countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private Integer idCountry;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "iso")
    private String iso;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "flag_url")
    private String flagUrl;

    @Column(name = "area_kilometers")
    private BigDecimal areaKilometers;

    @Column(name = "population")
    private Integer population;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subregion_id")
    private Subregion subregion;

    public Country() {

    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public BigDecimal getAreaKilometers() {
        return areaKilometers;
    }

    public void setAreaKilometers(BigDecimal areaKilometers) {
        this.areaKilometers = areaKilometers;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Subregion getSubregion() {
        return subregion;
    }

    public void setSubregion(Subregion subregion) {
        this.subregion = subregion;
    }
}
