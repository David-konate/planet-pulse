
-- delete the tables
DROP TABLE IF EXISTS t_countries;
DROP TABLE IF EXISTS t_subregions;
DROP TABLE IF EXISTS t_regions;
 
-- region table 
CREATE TABLE t_regions (
    id_region      INT GENERATED ALWAYS AS IDENTITY,
    region  VARCHAR(10) NOT NULL,
    CONSTRAINT pk_regions        PRIMARY KEY (id_region),
    CONSTRAINT uk_regions_region UNIQUE (region)
);
 
-- subregion table 
CREATE TABLE t_subregions (
    id_subregion     INT GENERATED ALWAYS AS IDENTITY,
    subregion  VARCHAR(30) NOT NULL,
    region_id  INT         NOT NULL,
    CONSTRAINT pk_subregions           PRIMARY KEY (id_subregion),
    CONSTRAINT uk_subregions_subregion UNIQUE (subregion),
    CONSTRAINT fk_subregions_region
        FOREIGN KEY (region_id) REFERENCES t_regions (id_region)
);
 
-- countries table 
CREATE TABLE t_countries (
    id_country          INT GENERATED ALWAYS AS IDENTITY,
    iso               CHAR(2)       NOT NULL,
    country_name      VARCHAR(50)   NOT NULL,
    flag_url          VARCHAR(50)      NOT NULL,
    area_kilometers   DECIMAL(10,2) NOT NULL,
    population        INT           NOT NULL,
    subregion_id      INT           NOT NULL,
    CONSTRAINT pk_countries                  PRIMARY KEY (id_country),
    CONSTRAINT uk_countries_iso              UNIQUE (iso),
    CONSTRAINT uk_countries_country_name     UNIQUE (country_name),
    CONSTRAINT ck_countries_area_kilometers  CHECK (area_kilometers > 0),
    CONSTRAINT ck_countries_population       CHECK (population > 0),
    CONSTRAINT fk_countries_subregion
        FOREIGN KEY (subregion_id) REFERENCES t_subregions (id_subregion)
);