--create table t_indicators
CREATE TABLE t_indicators
(
    id_indicator     INT GENERATED ALWAYS AS IDENTITY,
    id_country       INT      NOT NULL,
    indicator_year             INTEGER NOT NULL,
    co2_emissions    DECIMAL(14, 2), -- Mt
    renewable_energy DECIMAL(5, 2),  -- %
    forest_area      DECIMAL(5, 2),  -- %
    pm25_exposure    DECIMAL(7, 2),  -- µg/m³

    CONSTRAINT pk_indicators PRIMARY KEY (id_indicator),
    CONSTRAINT uk_indicators_country_year UNIQUE (id_country, indicator_year),
    CONSTRAINT ck_indicators_co2_emissions     CHECK (co2_emissions >= 0),
    CONSTRAINT ck_indicators_renewable_energy  CHECK (renewable_energy BETWEEN 0 AND 100),
    CONSTRAINT ck_indicators_forest_area       CHECK (forest_area BETWEEN 0 AND 100),
    CONSTRAINT ck_indicators_pm25_exposure     CHECK (pm25_exposure >= 0),

    CONSTRAINT ck_indicators_at_least_one      CHECK (
        co2_emissions IS NOT NULL OR renewable_energy IS NOT NULL
        OR forest_area IS NOT NULL OR pm25_exposure IS NOT NULL
    ),

    CONSTRAINT fk_indicators_country FOREIGN KEY (id_country) REFERENCES t_countries (id_country)
);