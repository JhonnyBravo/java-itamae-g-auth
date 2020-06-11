package java_itamae_g_auth.app.authorize;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java_itamae_g_auth.domain.model.AuthenticationAttribure;

/**
 * Servlet implementation class AuthorizeController
 */
@WebServlet("/authorize")
public class AuthorizeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizeController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        HttpSession session = request.getSession();
        AuthenticationAttribure authAttr = (AuthenticationAttribure) session
                .getAttribute("authAttr");

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                authAttr.getHttpTransport(), authAttr.getJsonFactory(),
                authAttr.getClientSecrets(), authAttr.getScopeList())
                        .setAccessType("offline").setApprovalPrompt("force")
                        .build();

        String redirectUrl = String.join(",",
                authAttr.getClientSecrets().getWeb().getRedirectUris());
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(redirectUrl).execute();

        Credential credential = new GoogleCredential.Builder()
                .setClientSecrets(
                        authAttr.getClientSecrets().getDetails().getClientId(),
                        authAttr.getClientSecrets().getDetails()
                                .getClientSecret())
                .setJsonFactory(authAttr.getJsonFactory())
                .setTransport(authAttr.getHttpTransport()).build();

        credential.setAccessToken(tokenResponse.getAccessToken());
        credential.refreshToken();

        authAttr.setCredential(credential);
        request.setAttribute("credential", credential);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context
                .getRequestDispatcher("/WEB-INF/authorize/index.jsp");
        dispatcher.include(request, response);
    }

}
