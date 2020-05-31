package java_itamae_g_auth.domain.repository.credential;

import com.google.api.client.auth.oauth2.Credential;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * Credential の操作を管理する。
 */
public interface CredentialRepository {
    /**
     * 認証情報を取得する。
     *
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @return credential {@link com.google.api.client.auth.oauth2.Credential}
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public Credential create(AuthenticationAttribure authAttr) throws Exception;
}
