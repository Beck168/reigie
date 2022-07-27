package com.beck.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.beck.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.FileNameMap;

/**
 * 检查用户是否完成登录
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求:{}", request.getRequestURI());
//        1.获取请求的URI?
        String uri = request.getRequestURI();
//        不需要的处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/**"
        };
//        2.判断本次请求是否不处理
        boolean check = Check(urls, uri);
        if (check){
            filterChain.doFilter(request, response);
            return;
        }
//        3.判断登录状态
        if(request.getSession().getAttribute("employee") != null){
            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("未登录")));
        return;



    }

    /**
     * 路径匹配,检查请求是否要放行
     * @param urls
     * @param uri
     * @return
     */
    public boolean Check(String[] urls, String uri) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
