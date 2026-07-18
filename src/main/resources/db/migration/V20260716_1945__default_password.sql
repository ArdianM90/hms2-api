ALTER TABLE auth.app_user
    ADD COLUMN is_default_password boolean NOT NULL DEFAULT true;