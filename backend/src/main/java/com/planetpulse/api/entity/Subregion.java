package com.planetpulse.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_subregions")
public class Subregion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subregion")
    private Integer id;

    @Column(name = "subregion", nullable = false, length = 30)
    private String subregion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    protected Subregion() {}

    public Subregion(String subregion, Region region) {
        this.subregion = subregion;
        this.region = region;
    }
}
