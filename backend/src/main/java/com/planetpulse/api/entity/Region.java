package com.planetpulse.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "t_regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer id;

    @Column(name = "region")
    private String region;

    protected Region() {}

    public Integer getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public Region(String region) {
        this.region = region;
    }
}
