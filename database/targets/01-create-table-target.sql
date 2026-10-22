
CREATE TABLE t_targets (
                           id_target              INT GENERATED ALWAYS AS IDENTITY,
                           id_country             INT           NOT NULL,
                           target_year            INTEGER       NOT NULL,
                           co2_emissions          NUMERIC(15,2),
                           pm25_emissions         NUMERIC(15,2),
                           forest_area            NUMERIC(5,2),
                           renewable_energy_share NUMERIC(5,2),

                           CONSTRAINT pk_targets PRIMARY KEY (id_target),

                           CONSTRAINT fk_targets_country
                               FOREIGN KEY (id_country) REFERENCES t_countries (id_country),

                           CONSTRAINT chk_targets_co2_emissions  CHECK (co2_emissions > 0),
                           CONSTRAINT chk_targets_pm25_emissions CHECK (pm25_emissions > 0),

                           CONSTRAINT chk_targets_forest_area     CHECK (forest_area BETWEEN 0 AND 100),
                           CONSTRAINT chk_targets_renewable_share CHECK (renewable_energy_share BETWEEN 0 AND 100),

                           CONSTRAINT chk_targets_at_least_one_field
                               CHECK (num_nonnulls(co2_emissions, pm25_emissions, forest_area, renewable_energy_share) >= 1),

                           CONSTRAINT uk_targets_country_year UNIQUE (id_country, target_year)
);