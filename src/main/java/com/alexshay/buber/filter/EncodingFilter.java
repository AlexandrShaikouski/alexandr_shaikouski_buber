package com.alexshay.buber.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter(urlPatterns = { "/*" })
public class EncodingFilter implements Filter {
    private static final String ENCODING_PARAM = "UTF-8";
    public void init(FilterConfig fConfig) throws ServletException {
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING_PARAM);
        response.setCharacterEncoding(ENCODING_PARAM);
        chain.doFilter(request, response);
    }
    public void destroy() {

    }
}