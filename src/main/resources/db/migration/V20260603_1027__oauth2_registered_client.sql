delete from oauth2_registered_client;

alter table oauth2_registered_client add column client_settings varchar(2000) not null;
alter table oauth2_registered_client add column token_settings varchar(2000) not null;

