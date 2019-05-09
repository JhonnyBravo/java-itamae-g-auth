package g_auth_resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import context_resource.ContextResource;

/**
 * ファイルの読込を管理する。
 */
public class ReaderResource extends ContextResource {
    private String path;
    private String encoding;
    private File file;
    private InputStreamReader reader = null;

    /**
     * @param path 読込対象とするファイルのパスを指定する。
     */
    public ReaderResource(String path) {
        this.path = path;
        this.file = new File(path);
        this.encoding = System.getProperty("file.encoding");
    }

    /**
     * @param encoding ファイルの文字エンコーディングを指定する。
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * ファイルの入力ストリームを開く。
     */
    @Override
    public void openContext() {
        this.initStatus();

        if (!this.file.isFile()) {
            this.errorTerminate(this.path + " が見つかりません。");
            return;
        }

        System.out.println(this.path + " を読込んでいます。");

        try {
            this.reader = new InputStreamReader(new FileInputStream(this.file), this.encoding);
            this.setCode(2);
        } catch (FileNotFoundException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        } catch (UnsupportedEncodingException e) {
            this.errorTerminate("エラーが発生しました。 " + e);
        }
    }

    /**
     * @return InputStreamReader
     */
    @Override
    public Object getContext() {
        return this.reader;
    }

    /**
     * ファイルの入力ストリームを閉じる。
     */
    @Override
    public void closeContext() {
        this.initStatus();

        if (this.reader != null) {
            try {
                this.reader.close();
                this.reader = null;
                this.setCode(2);
            } catch (IOException e) {
                this.errorTerminate("エラーが発生しました。 " + e);
            }
        }
    }
}
