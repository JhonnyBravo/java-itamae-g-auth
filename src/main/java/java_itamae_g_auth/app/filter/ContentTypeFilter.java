package java_itamae_g_auth.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 文字エンコードとコンテンツタイプを設定する。
 */
@WebFilter("/*")
public class ContentTypeFilter implements Filter {
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * 文字エンコードとコンテンツタイプを設定する。
     *
     * @param request
     *            {@link javax.servlet.ServletRequest}
     * @param response
     *            {@link javax.servlet.ServletResponse}
     * @param chain
     *            {@link javax.servlet.FilterChain}
     * @throws ServletException
     *             {@link javax.servlet.ServletException}
     * @throws IOException
     *             {@link java.io.IOException}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        chain.doFilter(request, response);
    }

}
