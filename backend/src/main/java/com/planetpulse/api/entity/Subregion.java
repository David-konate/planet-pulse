package com.planetpulse.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_subregions")
public class Subregion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subregion")
    private Integer id;

    @Column(name = "subregion")
    private String subregion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    protected Subregion() {}

    public Integer getId() {
        return id;
    }

    public String getSubregion() {
        return subregion;
    }

    public Region getRegion() {
        return region;
    }

    public Subregion(String subregion, Region region) {
        this.subregion = subregion;
        this.region = region;
    }
}
