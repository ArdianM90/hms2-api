INSERT INTO public.oauth2_registered_client (
    id,
    client_id,
    client_id_issued_at,
    client_secret,
    client_secret_expires_at,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    redirect_uris,
    post_logout_redirect_uris,
    scopes,
    client_settings,
    token_settings
)
VALUES (
           'hms-api',
           'hms-api',
           NOW(),
           NULL,
           NULL,
           'HMS API Client',
           'none',
           'authorization_code,refresh_token',
           'http://localhost:8082/swagger-ui/oauth2-redirect.html',
           NULL,
           'openid,profile,read',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true}',
           '{"@class":"java.util.Collections$UnmodifiableMap"}'
       );