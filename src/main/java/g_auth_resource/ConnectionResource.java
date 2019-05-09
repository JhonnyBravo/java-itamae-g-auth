package g_auth_resource;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import status_resource.Status;

/**
 * 通信に必要なオブジェクトを管理する。
 */
public abstract class ConnectionResource extends Status {
    private JsonFactory jsonFactory;
    private HttpTransport transport;

    public ConnectionResource() {
        this.initStatus();
        this.jsonFactory = JacksonFactory.getDefaultInstance();

        try {
            this.transport = GoogleNetHttpTransport.newTrustedTransport();
            this.setCode(2);
        } catch (GeneralSecurityException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }
    }

    /**
     * @return JsonFactory
     */
    public JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    /**
     * @return HttpTransport
     */
    public HttpTransport getTransport() {
        return this.transport;
    }

    /**
     * @return Credential
     */
    public abstract Credential getCredential();
}
