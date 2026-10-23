-- Check that t_countries exists before creating t_notes
DO
$$
BEGIN
  IF
to_regclass('public.t_countries') IS NULL THEN
    RAISE EXCEPTION 'Table t_countries does not exist. Run the t_countries script first.';
END IF;
END
$$;

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
