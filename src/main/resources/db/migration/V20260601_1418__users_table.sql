CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE auth.user
(
    user_id       UUID PRIMARY KEY,
    username      text      NOT NULL UNIQUE,
    email         text      NOT NULL UNIQUE,
    password_hash text      NOT NULL,
    is_active     bool      NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL DEFAULT now()
);