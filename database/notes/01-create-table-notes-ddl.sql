-- Notes table
CREATE TABLE t_notes (
                         note_timestamp TIMESTAMP NOT NULL PRIMARY KEY,
                         iso_country CHAR(2) NOT NULL,
                         note_title VARCHAR(255) NOT NULL,
                         note_date DATE NOT NULL,
                         note_content VARCHAR(10000) NOT NULL,

                         CONSTRAINT fk_t_note_country
                             FOREIGN KEY (iso_country)
                                 REFERENCES t_countries (iso)
);