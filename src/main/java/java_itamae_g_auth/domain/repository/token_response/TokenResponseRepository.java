package java_itamae_g_auth.domain.repository.token_response;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * TokenResponse の操作を管理する。
 */
public interface TokenResponseRepository {
    /**
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @return tokenResponse
     *         {@link com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse}
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public GoogleTokenResponse create(AuthenticationAttribure authAttr)
            throws Exception;
}
