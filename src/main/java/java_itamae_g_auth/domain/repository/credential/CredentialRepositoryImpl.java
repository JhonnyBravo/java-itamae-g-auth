package java_itamae_g_auth.domain.repository.credential;

import java.io.File;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.util.store.FileDataStoreFactory;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

public class CredentialRepositoryImpl implements CredentialRepository {

    @Override
    public Credential createInstalledAppCredential(
            AuthenticationAttribure authAttr) throws Exception {
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
                new File("credential"));

        GoogleAuthorizationCodeFlow.Builder builder = new GoogleAuthorizationCodeFlow.Builder(
                authAttr.getHttpTransport(), authAttr.getJsonFactory(),
                authAttr.getClientSecrets(), authAttr.getScopeList());
        builder.setDataStoreFactory(fileDataStoreFactory);

        AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(
                builder.build(), new LocalServerReceiver());

        Credential credential = authorizationCodeInstalledApp.authorize(
                authAttr.getClientSecrets().getInstalled().getClientId());
        credential.refreshToken();

        return credential;
    }

    @Override
    public Credential createWebAppCredential(AuthenticationAttribure authAttr)
            throws Exception {
        Details details = authAttr.getClientSecrets().getDetails();
        String accessToken = authAttr.getTokenResponse().getAccessToken();

        Credential credential = new GoogleCredential.Builder()
                .setClientSecrets(details.getClientId(),
                        details.getClientSecret())
                .setJsonFactory(authAttr.getJsonFactory())
                .setTransport(authAttr.getHttpTransport()).build();
        credential.setAccessToken(accessToken);
        credential.refreshToken();

        return credential;
    }

}
