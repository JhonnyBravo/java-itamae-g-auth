package java_itamae_g_auth.domain.repository.client_secrets;

import java.io.Reader;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * client_secret.json の操作を管理する。
 */
public interface ClientSecretsRepository {
    /**
     * client_secret.json を読込む。
     *
     * @param reader
     *            client_secret.json の Reader を指定する。
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @return clientSecrets
     *         {@link com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets}
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public GoogleClientSecrets create(Reader reader,
            AuthenticationAttribure authAttr) throws Exception;
}
