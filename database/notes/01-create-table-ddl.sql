-- Notes table
CREATE TABLE t_notes
(
    note_timestamp TIMESTAMP      NOT NULL PRIMARY KEY,
    id_country     INT            NOT NULL,
    note_title     VARCHAR(255)   NOT NULL,
    note_content   VARCHAR(10000) NOT NULL,
    CONSTRAINT fk_t_note_country
        FOREIGN KEY (id_country)
        REFERENCES t_countries (id_country)
);
