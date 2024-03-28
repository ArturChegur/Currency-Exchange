CREATE TABLE IF NOT EXISTS currencies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code VARCHAR,
    full_name VARCHAR,
    sign VARCHAR,
    UNIQUE (code)
);