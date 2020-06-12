package java_itamae_g_auth.domain.service.authentication;

import java.io.Reader;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_contents.domain.repository.stream.StreamRepository;
import java_itamae_contents.domain.repository.stream.StreamRepositoryImpl;
import java_itamae_g_auth.domain.model.AuthenticationAttribure;
import java_itamae_g_auth.domain.repository.client_secrets.ClientSecretsRepository;
import java_itamae_g_auth.domain.repository.client_secrets.ClientSecretsRepositoryImpl;
import java_itamae_g_auth.domain.repository.credential.CredentialRepository;
import java_itamae_g_auth.domain.repository.credential.CredentialRepositoryImpl;
import java_itamae_g_auth.domain.repository.token_response.TokenResponseRepository;
import java_itamae_g_auth.domain.repository.token_response.TokenResponseRepositoryImpl;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final StreamRepository streamRepository;
    private final ClientSecretsRepository clientSecretsRepository;
    private final TokenResponseRepository tokenResponseRepository;
    private final CredentialRepository credentialRepository;

    public AuthenticationServiceImpl() {
        streamRepository = new StreamRepositoryImpl();
        clientSecretsRepository = new ClientSecretsRepositoryImpl();
        tokenResponseRepository = new TokenResponseRepositoryImpl();
        credentialRepository = new CredentialRepositoryImpl();
    }

    @Override
    public void authorizeInstalledApp(ContentsAttribute contentsAttr,
            AuthenticationAttribure authAttr) throws Exception {
        try (Reader reader = streamRepository.getReader(contentsAttr)) {
            GoogleClientSecrets clientSecrets = clientSecretsRepository
                    .create(reader, authAttr);
            authAttr.setClientSecrets(clientSecrets);

            Credential credential = credentialRepository
                    .createInstalledAppCredential(authAttr);
            authAttr.setCredential(credential);
        }
    }

    @Override
    public void authorizeWebApp(AuthenticationAttribure authAttr)
            throws Exception {
        GoogleTokenResponse tokenResponse = tokenResponseRepository
                .create(authAttr);
        authAttr.setTokenResponse(tokenResponse);

        Credential credential = credentialRepository
                .createWebAppCredential(authAttr);
        authAttr.setCredential(credential);
    }

    @Override
    public String getAuthUrl(ContentsAttribute contentsAttr,
            AuthenticationAttribure authAttr) throws Exception {
        String url = "";

        try (Reader reader = streamRepository.getReader(contentsAttr)) {
            GoogleClientSecrets clientSecrets = clientSecretsRepository
                    .create(reader, authAttr);
            authAttr.setClientSecrets(clientSecrets);

            String authUri = clientSecrets.getWeb().getAuthUri();
            String clientId = clientSecrets.getWeb().getClientId();
            String redirectUri = String.join(",",
                    clientSecrets.getWeb().getRedirectUris());
            String scope = String.join(",", authAttr.getScopeList());

            String format = "%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&access_type=offline";
            url = String.format(format, authUri, clientId, redirectUri, scope);
        }

        return url;
    }

}
