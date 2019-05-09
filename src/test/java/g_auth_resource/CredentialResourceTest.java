package g_auth_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

/**
 * {@link g_auth_resource.CredentialResource} のための単体テスト。
 */
public class CredentialResourceTest {
    /**
     * {@link g_auth_resource.CredentialResource#getCredential()} のためのテスト・メソッド。
     */
    @Test
    public final void testGetCredential() {
        // 認証ファイルの読込
        ReaderResource rr = new ReaderResource("src/test/resources/credentials.json");
        rr.setEncoding("UTF-8");
        rr.openContext();
        InputStreamReader reader = (InputStreamReader) rr.getContext();

        // 操作権限の設定
        ScopesResource sr = new ScopesResource();
        sr.addScope(DriveScopes.DRIVE);
        List<String> scopes = sr.getScopes();

        // 認証の実行
        CredentialResource cr = new CredentialResource(reader, scopes);
        Drive drive = new Drive.Builder(cr.getTransport(), cr.getJsonFactory(), cr.getCredential())
                .setApplicationName("g_auth_resource_test").build();
        rr.closeContext();

        assertThat(cr.getCode(), is(2));
        assertThat(new java.io.File("tokens/StoredCredential").isFile(), is(true));

        try {
            List<File> files = drive.files().list().setFields("files(id, name, parents)").execute().getFiles();

            for (File file : files) {
                System.out.println("Name: " + file.getName());
                System.out.println("ID: " + file.getId());

                List<String> parents = file.getParents();

                for (String parent : parents) {
                    File directory = drive.files().get(parent).execute();
                    System.out.println("Parent Name: " + directory.getName());
                    System.out.println("Parent ID: " + directory.getId());
                }

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
