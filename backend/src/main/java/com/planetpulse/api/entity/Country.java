package com.planetpulse.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "t_countries")
@Getter
@Setter
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private Integer idCountry;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "iso", nullable = false, length = 2)
    private String iso;

    @Column(name = "country_name", nullable = false, length = 50)
    private String countryName;

    @Column(name = "flag_url", nullable = false, length = 50)
    private String flagUrl;

    @Column(name = "area_kilometers", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaKilometers;

    @Column(name = "population", nullable = false)
    private Integer population;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subregion_id", nullable = false)
    private Subregion subregion;

}
