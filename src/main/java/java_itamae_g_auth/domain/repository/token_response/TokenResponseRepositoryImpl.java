package java_itamae_g_auth.domain.repository.token_response;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

public class TokenResponseRepositoryImpl implements TokenResponseRepository {

    @Override
    public GoogleTokenResponse create(AuthenticationAttribure authAttr)
            throws Exception {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                authAttr.getHttpTransport(), authAttr.getJsonFactory(),
                authAttr.getClientSecrets(), authAttr.getScopeList())
                        .setAccessType("offline").setApprovalPrompt("force")
                        .build();

        String redirectUrl = String.join(",",
                authAttr.getClientSecrets().getWeb().getRedirectUris());

        GoogleTokenResponse tokenResponse = flow
                .newTokenRequest(authAttr.getCode()).setRedirectUri(redirectUrl)
                .execute();
        return tokenResponse;
    }

}
