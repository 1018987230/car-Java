package com.example.nft.Handler;

import com.example.nft.service.ex.LoginException;
import com.example.nft.utils.JwtUtil;
import com.example.nft.utils.RedisUtil;
import com.example.nft.utils.ResultGenerator;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    //　业务处理器请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        String token =  request.getHeader("token");
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        if (token == null){
            throw new LoginException("token 为空！");
        }
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            return true;
        }
        JwtUtil.checkSign(token);

        // redisUtil 拦截器加载的时间点在springContext之前，所以在拦截器中注入自然为null，要手动注入！
        RedisUtil redisUtil = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(RedisUtil.class);
        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getUser(token);
//        String storeId = JwtUtil.getStore(token);
        // 生成的token保存在redis中，key为店铺uuid拼接上用户手机号
//        Object token_2 =  redisUtil.get(storeId + userId);
        Object token_2 =  redisUtil.get(userId);
        System.out.println(token_2);
        if(token_2 == null){
            throw new LoginException("token不存在");
        }
        if(!token_2.equals(token)){
            throw new LoginException("token值无效");
        }
        return true;


    }






    //在业务处理器处理请求执行完成后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    //在DispatcherServlet完全处理完请求后被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}