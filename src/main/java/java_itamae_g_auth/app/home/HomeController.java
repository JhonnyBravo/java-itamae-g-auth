package java_itamae_g_auth.app.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.services.drive.DriveScopes;

import java_itamae_contents.domain.model.ContentsAttribute;
import java_itamae_g_auth.domain.model.AuthenticationAttribure;
import java_itamae_g_auth.domain.service.authentication.AuthenticationService;
import java_itamae_g_auth.domain.service.authentication.AuthenticationServiceImpl;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthenticationService service;

    public HomeController() {
        super();
        service = new AuthenticationServiceImpl();
    }

    /**
     * 認証ページへの URL を取得して画面へ送信する。
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
        ServletContext context = getServletContext();

        ContentsAttribute contentsAttr = null;
        AuthenticationAttribure authAttr = null;
        String authUrl = null;

        try {
            // client_secret.json の文字エンコードとパスを指定する。
            contentsAttr = new ContentsAttribute();
            contentsAttr.setEncoding("UTF-8");
            String path = context
                    .getRealPath("/WEB-INF/classes/client_secret.json");
            contentsAttr.setPath(path);

            // スコープを指定する。
            authAttr = new AuthenticationAttribure();
            authAttr.addScope(DriveScopes.DRIVE);

            // 認証ページへの URL を取得する。
            authUrl = service.getAuthUrl(contentsAttr, authAttr);
        } catch (Exception e) {
            throw new IOException(e);
        }

        request.setAttribute("authUrl", authUrl);

        HttpSession session = request.getSession();
        session.setAttribute("authAttr", authAttr);

        RequestDispatcher dispatcher = context
                .getRequestDispatcher("/WEB-INF/home/index.jsp");
        dispatcher.include(request, response);
    }

}
