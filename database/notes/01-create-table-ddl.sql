-- check that t_country exists before creating t_note
DO $$
BEGIN
  IF to_regclass('public.t_country') IS NULL THEN
    RAISE EXCEPTION 'Table t_country does not exist. Run the t_country script first.';
  END IF;
END
$$;

-- notes table
CREATE TABLE t_note (
   id_notes bigint GENERATED ALWAYS AS IDENTITY,
   title VARCHAR(255) NOT NULL,
   date DATE NOT NULL,
   content VARCHAR(10000) NOT NULL,
   id_country bigint,
   PRIMARY KEY(id_notes),
   CONSTRAINT FK_ISO FOREIGN KEY (id_country) REFERENCES t_country(id_country)
);