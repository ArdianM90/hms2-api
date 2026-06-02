CREATE TABLE oauth2_registered_client
(
    id                            varchar(100) PRIMARY KEY,
    client_id                     varchar(100) NOT NULL,
    client_id_issued_at           timestamp,
    client_secret                 varchar(200),
    client_secret_expires_at      timestamp,
    client_name                   varchar(200),
    client_authentication_methods varchar(1000),
    authorization_grant_types     varchar(1000),
    redirect_uris                 varchar(1000),
    post_logout_redirect_uris     varchar(1000),
    scopes                        varchar(1000)
);