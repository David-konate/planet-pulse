# Database: setup scripts

This folder contains the SQL scripts that build the PostgreSQL database
of the project, **`planet_pulse`**.

## Scripts

Run them **in this order**. A script that references another table
(foreign key) must run **after** the script that creates that table.

| Order | File | What it does |
|---|---|---|
| 1 | `01-create-table-countries-region-subregion-ddl.sql` | creates `t_regions`, `t_subregions` and `t_countries` |
| … | `02-….sql` | *(add your script here)* |


### Adding a script

1. Put the `.sql` file in this folder.
2. Name it with the next number: `02-…`, `03-…`. The number gives the execution order.
3. Add a line to the table above.

## Prerequisites

- **PostgreSQL** installed and running (version 18 at last).
- **`psql`**, the PostgreSQL command-line tools. It is installed with PostgreSQL.
- **VS Code**, with this project open.
- Your PostgreSQL **user** (`postgres` by default) and its **password**.

### Check that the tools are available

In VS Code, open the integrated terminal (**Terminal > New Terminal**, or `` Ctrl+` ``) and type:

```bash
psql --version
```

You should see something like `psql (PostgreSQL) 18.x`.

If the terminal says `psql` is not recognised (common on Windows),
add the PostgreSQL `bin` folder to your `PATH`, for example
`C:\Program Files\PostgreSQL\16\bin` (replace `16` with your version),
then **close and reopen VS Code**.

## 1. Create the database (first time only)

```bash
psql -U postgres -c "CREATE DATABASE planet_pulse;"
```

- `-U postgres`: the PostgreSQL user (replace it if you use another one).
- `planet_pulse`: the name of the database

The tool asks for your password: type it and press Enter
(nothing appears on the screen while you type, this is normal).

If the database already exists, skip this step.

## 2. Run a script

From the **root of the project**, in the VS Code terminal:

```bash
psql -U postgres -d planet_pulse -v ON_ERROR_STOP=1 -f database/folder/<script>.sql
```

Replace `<script>` with the name of the file. For example, for the countries script:

```bash
psql -U postgres -d planet_pulse -v ON_ERROR_STOP=1 -f database/countries/01-create-table-countries-region-subregion-ddl.sql
```

- `-d planet_pulse`: the database the script runs in.
- `-v ON_ERROR_STOP=1`: stops the script at the **first error**, instead of
  running the next lines on a half-built database.
- `-f database/folder/<script>.sql`: the script to run (path from the root of the project).

Run the scripts one after the other, **in the order of the table**.

The first time, PostgreSQL shows lines like
`NOTICE: table "t_countries" does not exist, skipping`.
**This is normal**: the script tries to delete tables that do not exist yet.

## 3. Check the result

List the tables of the database:

```bash
psql -U postgres -d planet_pulse -c "\dt"
```

For example, after `01-create-table-countries-region-subregion-ddl.sql`, you should see:

```
 Schema |     Name     | Type  |  Owner   
--------+--------------+-------+----------
 public | t_countries  | table | postgres
 public | t_regions    | table | postgres
 public | t_subregions | table | postgres
```

To see the columns and constraints of one table (here `t_countries`):

```bash
psql -U postgres -d planet_pulse -c "\d t_countries"
```

## Common errors

| Message | Cause | Solution |
|---|---|---|
| `psql: command not found` / `not recognized` | `psql` is not in the `PATH` | See *Check that the tools are available* |
| `password authentication failed` | Wrong user or password | Check `-U` and your password |
| `database "planet-pulse" does not exist` | Step 1 not done | Create the database (step 1) |
| `could not connect to server` | PostgreSQL is not running | Start the PostgreSQL service |
| `No such file or directory` | The terminal is not at the root of the project | Run `cd` to the project folder, or fix the path after `-f` |
| `relation "…" does not exist` | A script ran before the one that creates the table it references | Run the scripts in the order of the table |