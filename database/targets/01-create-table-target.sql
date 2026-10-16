DO $$
BEGIN
    IF to_regclass('public.t_countries') IS NULL THEN
        RAISE EXCEPTION 'The table t_countries do not exist.';
END IF;
END
$$;

CREATE TABLE t_target (
    id_target              INT GENERATED ALWAYS AS IDENTITY,
    id_country             INT           NOT NULL,
    year_                  INTEGER       NOT NULL,
    co2_emissions          NUMERIC(15,2),
    pm25_emissions         NUMERIC(15,2),
    forest_area            NUMERIC(5,2),
    renewable_energy_share NUMERIC(5,2),
    CONSTRAINT pk_target PRIMARY KEY (id_target),
    CONSTRAINT fk_target_country
        FOREIGN KEY (id_country) REFERENCES t_countries (id_country),
    -- valeurs strictement positives
    CONSTRAINT chk_co2_emissions   CHECK (co2_emissions > 0),
    CONSTRAINT chk_pm25_emissions  CHECK (pm25_emissions > 0),
    -- pourcentages entre 0 et 100
    CONSTRAINT chk_forest_area     CHECK (forest_area BETWEEN 0 AND 100),
    CONSTRAINT chk_renewable_share CHECK (renewable_energy_share BETWEEN 0 AND 100),
    -- au moins un indicateur renseigné
    CONSTRAINT chk_targets_at_least_one_field
        CHECK (num_nonnulls(co2_emissions, pm25_emissions, forest_area, renewable_energy_share) >= 1),
    -- un seul objectif par pays et par année
    CONSTRAINT uk_target_country_year UNIQUE (id_country, year_)
);