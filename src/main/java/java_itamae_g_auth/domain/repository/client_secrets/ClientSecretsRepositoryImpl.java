package java_itamae_g_auth.domain.repository.client_secrets;

import java.io.Reader;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

public class ClientSecretsRepositoryImpl implements ClientSecretsRepository {

    @Override
    public GoogleClientSecrets create(Reader reader,
            AuthenticationAttribure authAttr) throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
                .load(authAttr.getJsonFactory(), reader);
        return clientSecrets;
    }

}
