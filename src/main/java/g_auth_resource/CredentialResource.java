package g_auth_resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * Google API の認証を実行する。
 */
public class CredentialResource extends ConnectionResource {
    private InputStreamReader reader;
    private Credential credential = null;
    private List<String> scopes;

    /**
     * @param reader 読込対象とする認証ファイルの InputStreamReader オブジェクトを指定する。
     * @param scopes 操作権限の設定を納めたリストを指定する。
     */
    public CredentialResource(InputStreamReader reader, List<String> scopes) {
        this.reader = reader;
        this.scopes = scopes;
    }

    /**
     * 認証を実行する。
     */
    private void authorize() {
        this.initStatus();

        System.out.println("認証を実行しています。");

        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(this.getJsonFactory(), this.reader);

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(this.getTransport(),
                    this.getJsonFactory(), clientSecrets, this.scopes)
                            .setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).setAccessType("offline")
                            .build();
            LocalServerReceiver receiver = new LocalServerReceiver();

            AuthorizationCodeInstalledApp app = new AuthorizationCodeInstalledApp(flow, receiver);
            this.credential = app.authorize("user");
            this.setCode(2);
        } catch (IOException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }
    }

    /**
     * @return Credential
     */
    @Override
    public Credential getCredential() {
        this.authorize();
        return this.credential;
    }
}
