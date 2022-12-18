package com.example.nft.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2022/6/29 上午11:02
 * @description：xss过滤
 * @modified By：
 * @version: $
 */
public class XssFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static boolean IS_INCLUDE_RICH_TEXT = false;

    private List<String> excluds = new ArrayList<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
