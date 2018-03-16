package cn.ghl.dubbo.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class TscLogFilter implements Filter {

    private String KEY_NAME = "event";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String event;
        if (request.getHeader(KEY_NAME) == null || request.getHeader(KEY_NAME).length() == 0) {
            event = UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            event = request.getHeader(KEY_NAME);
        }

        MDC.put(KEY_NAME, event);
        response.setHeader(KEY_NAME, event);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }

    }

    public void destroy() {

    }

}
