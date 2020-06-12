package java_itamae_g_auth.domain.service.authentication;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * 認証の実行を管理する。
 */
public interface AuthenticationService {
    /**
     * Installed App の認証を実行する。
     *
     * @param contentsAttr
     *            client_secret.json の読取りに使用する情報を収めた ContentsAttribute を指定する。
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public void authorizeInstalledApp(ContentsAttribute contentsAttr,
            AuthenticationAttribure authAttr) throws Exception;

    /**
     * Web App の認証を実行する。
     *
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public void authorizeWebApp(AuthenticationAttribure authAttr)
            throws Exception;

    /**
     * Web App 用の認証 URL を生成して返す。
     *
     * @param contentsAttr
     *            client_secret.json の読取りに使用する情報を収めた ContentsAttribute を指定する。
     * @param authAttr
     *            認証に使用する情報を収めた AuthenticationAttribute を指定する。
     * @return 認証 URL を返す。
     * @throws Exception
     *             {@link java.lang.Exception}
     */
    public String getAuthUrl(ContentsAttribute contentsAttr,
            AuthenticationAttribure authAttr) throws Exception;
}
