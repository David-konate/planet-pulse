package com.planetpulse.api.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name= "t_regions")
@Getter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer id;

    @Column(name = "region", nullable = false, length = 10)
    private String region;

    protected Region() {}

    public Region(String region) {
        this.region = region;
    }
}
