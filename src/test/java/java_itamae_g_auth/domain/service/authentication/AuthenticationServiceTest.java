package java_itamae_g_auth.domain.service.authentication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.services.drive.DriveScopes;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_g_auth.domain.model.AuthenticationAttribure;

public class AuthenticationServiceTest {
    private AuthenticationService service;
    private File credentialFile;

    @Before
    public void setUp() throws Exception {
        service = new AuthenticationServiceImpl();
        credentialFile = new File("credential/StoredCredential");

        if (credentialFile.isFile()) {
            credentialFile.delete();
        }
    }

    @After
    public void tearDown() throws Exception {
        if (credentialFile.isFile()) {
            credentialFile.delete();
        }
    }

    @Test
    public void 認証ファイルが作成されること() throws Exception {
        ContentsAttribute contentsAttr = new ContentsAttribute();
        contentsAttr.setPath("src/test/resources/cli/client_secret.json");
        contentsAttr.setEncoding("UTF-8");

        AuthenticationAttribure authAttr = new AuthenticationAttribure();
        authAttr.addScope(DriveScopes.DRIVE);

        service.authorizeInstalledApp(contentsAttr, authAttr);
        assertThat(credentialFile.isFile(), is(true));
    }

    @Test
    public void 認証URLを取得できること() throws Exception {
        ContentsAttribute contentsAttr = new ContentsAttribute();
        contentsAttr.setPath("src/test/resources/web/client_secret.json");
        contentsAttr.setEncoding("UTF-8");

        AuthenticationAttribure authAttr = new AuthenticationAttribure();
        authAttr.addScope(DriveScopes.DRIVE);

        String actualUrl = service.getAuthUrl(contentsAttr, authAttr);

        GoogleClientSecrets clientSecrets = authAttr.getClientSecrets();
        String clientId = clientSecrets.getWeb().getClientId();
        String redirectUri = String.join(",",
                clientSecrets.getWeb().getRedirectUris());

        String format = "https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&scope=https://www.googleapis.com/auth/drive&response_type=code&access_type=offline";
        String expectUrl = String.format(format, clientId, redirectUri);
        assertThat(actualUrl, is(expectUrl));
    }

}
