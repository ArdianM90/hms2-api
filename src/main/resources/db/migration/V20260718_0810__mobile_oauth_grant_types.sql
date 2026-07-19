UPDATE public.oauth2_registered_client
SET authorization_grant_types = 'authorization_code,refresh_token'
WHERE client_id = 'hms-mobile';