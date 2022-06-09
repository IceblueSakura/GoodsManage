package com.example.goodsmanage;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个自定义的拦截器类
 * 用途：根据是否登录有不同处理方式的类(比如访问全部商品时未登录，会自动跳转到登陆页面)
 */

public class LoginHandleInterceptor implements HandlerInterceptor {
    @Override // 服务器刚收到请求还没处理
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {  // 如果Cookie存在
            String token = "";
            for (Cookie cookie : cookies) {  // 就遍历找到名为token的Cookie
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            if (Utils.getInfoByToken(token)!=null) {  // 验证token是否有效用户，有效返回用户信息，无效返回null
//                System.out.println("用户已经登录，放行。token=" + token);
                return true;
            }

        }
        // 未登录(没找到token),跳转登录页
//        System.out.println("用户未登录，跳转登录页。");
        httpServletRequest.getRequestDispatcher("/user/login").forward(httpServletRequest, httpServletResponse);
        return false;
    }

    @Override  // 服务器收到请求后刚处理完还没返回前端显示
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // 无内容
    }

    @Override  // 已经返回前端显示了
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 无内容
    }
}
