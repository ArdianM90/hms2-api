ALTER TABLE auth.app_user
    ALTER COLUMN password_hash DROP NOT NULL;