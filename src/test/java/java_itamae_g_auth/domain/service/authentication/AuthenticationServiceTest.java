package java_itamae_g_auth.domain.service.authentication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        credentialFile.delete();
    }

    @Test
    public void 認証ファイルが作成されること() throws Exception {
        ContentsAttribute contentsAttr = new ContentsAttribute();
        contentsAttr.setPath("src/test/resources/client_secret.json");
        contentsAttr.setEncoding("UTF-8");

        AuthenticationAttribure authAttr = new AuthenticationAttribure();
        authAttr.setUserName("test_user");
        authAttr.addScope(DriveScopes.DRIVE);

        service.authorize(contentsAttr, authAttr);
        assertThat(credentialFile.isFile(), is(true));
    }

}
