package java_itamae_g_auth.domain.service.authentication;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * 認証の実行を管理する。
 */
public interface AuthenticationService {
    /**
     * 認証を実行する。
     *
     * @param contentsAttr
     *            client_secret.json の読取りに使用する情報を収めた ContentsAttribute を指定する。
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public void authorize(ContentsAttribute contentsAttr,
            AuthenticationAttribure authAttr) throws Exception;
}
