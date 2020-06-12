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

import java_itamae_g_auth.domain.model.AuthenticationAttribure;
import java_itamae_g_auth.domain.service.authentication.AuthenticationService;
import java_itamae_g_auth.domain.service.authentication.AuthenticationServiceImpl;

/**
 * 認証処理のデモページ
 */
@WebServlet("/authorize")
public class AuthorizeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthenticationService service;

    public AuthorizeController() {
        super();
        service = new AuthenticationServiceImpl();
    }

    /**
     * 認証処理を実行し、取得した Credential を画面へ送信する。
     *
     * @param request
     *            {@link javax.servlet.http.HttpServletRequest}
     * @param response
     *            {@link javax.servlet.http.HttpServletResponse}
     * @throws ServletException
     *             {@link javax.servlet.ServletException}
     * @throws IOException
     *             {@link java.io.IOException}
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // セッションを通じて認証情報を受け取る。
        HttpSession session = request.getSession();
        AuthenticationAttribure authAttr = (AuthenticationAttribure) session
                .getAttribute("authAttr");
        // リクエストパラメータからレスポンスコードを受け取る。
        String code = request.getParameter("code");
        authAttr.setCode(code);

        // 認証処理を実行する。
        try {
            service.authorizeWebApp(authAttr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("credential", authAttr.getCredential());

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context
                .getRequestDispatcher("/WEB-INF/authorize/index.jsp");
        dispatcher.include(request, response);
    }

}
