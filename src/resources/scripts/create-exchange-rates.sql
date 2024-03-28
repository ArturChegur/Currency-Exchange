CREATE TABLE IF NOT EXISTS exchange_rates (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    base_currency_id INTEGER,
    target_currency_id INTEGER,
    rate NUMERIC(8, 6),
    FOREIGN KEY (base_currency_id) REFERENCES currencies(id),
    FOREIGN KEY (target_currency_id) REFERENCES currencies(id),
    UNIQUE (base_currency_id, target_currency_id)
);