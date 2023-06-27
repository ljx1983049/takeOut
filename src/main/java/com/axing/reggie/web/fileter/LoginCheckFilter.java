package com.axing.reggie.web.fileter;

import com.alibaba.fastjson.JSON;
import com.axing.reggie.common.BaseContext;
import com.axing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")//过滤器名字，和拦截路径（拦截所有路径）
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        // 1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("本次请求路径{}",requestURI);
        //定义不需要处理的路径
        String[] uris = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/sendMsg"
                // ,"/common/**"//测试图片上传下载
                //-----knife4j相关配置-------
                ,"/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        // 2、判断本次请求是否需要处理
        boolean check = check(requestURI, uris);

        // 3、如果不需要处理，则直接放行
        if(check){
            log.info("不需要处理");
            filterChain.doFilter(request,response);
            return;
        }

        // 4、判断后台登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employeeId")!=null){
            log.info("员工已登录");

            Long employeeId = (Long) request.getSession().getAttribute("employeeId");
            BaseContext.setCurrentId(employeeId);
            log.info("员工id为{}",employeeId);

            filterChain.doFilter(request,response);
            return;
        }

        // 4-2、判断用户登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("userId")!=null){
            log.info("用户已登录");

            Long userId = (Long) request.getSession().getAttribute("userId");
            BaseContext.setCurrentId(userId);
            log.info("用户id为{}",userId);

            filterChain.doFilter(request,response);
            return;
        }

        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestUri
     * @param uris
     * @return
     */
    private boolean check(String requestUri,String[] uris){
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requestUri);//路径匹配，uri：模板、 requestUri：路径（路径是否与模板匹配）
            if (match){
                return true;
            }
        }
        return false;
    }
}
