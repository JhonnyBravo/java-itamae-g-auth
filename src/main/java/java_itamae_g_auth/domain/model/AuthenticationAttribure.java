package java_itamae_g_auth.domain.model;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * 認証に使用する情報を管理する。
 */
public class AuthenticationAttribure implements Serializable {
    private static final long serialVersionUID = 1L;

    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final List<String> scopeList;
    private String userName;
    private String code;
    private GoogleClientSecrets clientSecrets;
    private GoogleTokenResponse tokenResponse;
    private Credential credential;

    public AuthenticationAttribure()
            throws GeneralSecurityException, IOException {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        jsonFactory = JacksonFactory.getDefaultInstance();
        scopeList = new ArrayList<>();
    }

    /**
     * @return httpTransport {@link com.google.api.client.http.HttpTransport}
     */
    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    /**
     * @return jsonFactory {@link com.google.api.client.json.JsonFactory}
     */
    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * @return scopeList アプリケーションへ許可する操作権限のリストを返す。
     */
    public List<String> getScopeList() {
        return scopeList;
    }

    /**
     * アプリケーションへ許可する操作権限を追加する。
     *
     * @param scope
     *            許可する操作権限を指定する。
     */
    public void addScope(String scope) {
        scopeList.add(scope);
    }

    /**
     * @return ユーザ名を返す。
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            ユーザ名を指定する。
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return code code パラメータを返す。
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            code パラメータを指定する。
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return clientSecrets
     *         {@link com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets}
     */
    public GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }

    /**
     * @param clientSecrets
     *            {@link com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets}
     */
    public void setClientSecrets(GoogleClientSecrets clientSecrets) {
        this.clientSecrets = clientSecrets;
    }

    /**
     * @return tokenResponse
     *         {@link com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse}
     */
    public GoogleTokenResponse getTokenResponse() {
        return tokenResponse;
    }

    /**
     * @param tokenResponse
     *            {@link com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse}
     */
    public void setTokenResponse(GoogleTokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    /**
     * @return credential {@link com.google.api.client.auth.oauth2.Credential}
     */
    public Credential getCredential() {
        return credential;
    }

    /**
     * @param credential
     *            {@link com.google.api.client.auth.oauth2.Credential}
     */
    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
