package g_auth_resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作権限の設定を管理する。
 */
public class ScopesResource {
    private List<String> scopes;

    public ScopesResource() {
        this.scopes = new ArrayList<String>();
    }

    /**
     * @param scope 登録する操作権限を指定する。
     */
    public void addScope(String scope) {
        this.scopes.add(scope);
    }

    /**
     * @return scopes 操作権限の設定を納めたリストを返す。
     */
    public List<String> getScopes() {
        return this.scopes;
    }
}
