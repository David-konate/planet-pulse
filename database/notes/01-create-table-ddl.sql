
-- Check that t_country exists before creating t_note
DO $$
BEGIN
  IF to_regclass('public.t_country') IS NULL THEN
    RAISE EXCEPTION 'Table t_country does not exist. Run the t_country script first.';
  END IF;
END
$$;

-- Notes table
CREATE TABLE t_note (
    note_timestamp TIMESTAMP NOT NULL,
    id_country BIGINT NOT NULL,
    note_title VARCHAR(255) NOT NULL,
    note_content VARCHAR(10000) NOT NULL,

    PRIMARY KEY (note_timestamp, id_country),
    CONSTRAINT fk_t_note_country
        FOREIGN KEY (id_country)
        REFERENCES t_country (id_country)
);
