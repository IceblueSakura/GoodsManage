package com.example.goodsmanage.controller;

import com.example.goodsmanage.Utils;
import com.example.goodsmanage.entity.TokenInfo;
import com.example.goodsmanage.entity.UserVO;
import com.example.goodsmanage.model.User;
import com.example.goodsmanage.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * /user/**页面的控制器类
 * 包含用户登录，登出在内的各种用户信息相关的页面响应
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    final private UserService userService;

    @Autowired //自动装配Service
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/", "/login"})  // 设置/user和/user/login都是登录界面
    public String loginPage(@CookieValue(value = "token", defaultValue = "") String token, Model model) {
        TokenInfo tokenInfo = Utils.getInfoByToken(token);  // 根据浏览器Cookie内存储的Token获取用户数据
        if (tokenInfo != null) {  // 根据获取的token信息判断是跳转到用户信息页(已经登陆)还是登录页(未登录)
            UserVO userVO = userService.selectOneUser(tokenInfo.getSid(), null);  // 已经登录，根据token内的sid获取用户信息
            model.addAttribute("user", userVO);  // 给前端页面传入已经登录的用户信息
            return "user/userLoginInfo";
        } else {
            model.addAttribute("userLogin", new UserLogin());
            return "user/userLogin";
        }
    }

    @Data
    static class UserLogin {
        private String username;
        private String password;
    }

    @PostMapping({"/login"})
    public String login(@ModelAttribute("userLogin") UserLogin userLogin,
                        Model model, HttpServletResponse httpServletResponse) {
        User user = userService.login(userLogin.getUsername(), userLogin.getPassword());
        if (user == null) {
            model.addAttribute("status", "登陆失败！");
            model.addAttribute("info", "用户名不存在或密码错误！");
        } else {
            String token = Utils.generateToken(user.getSid(), user.getUsername());
            Cookie cookie = new Cookie("token", token);
            cookie.setSecure(true);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            model.addAttribute("status", "登录成功！");
            model.addAttribute("info", "欢迎你," + user.getUsername());
        }
        return "tip";
    }

    @PostMapping({"/logout"})
    public String logout(Model model, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");  //防止无法清除cookie，设定成统一的地址
        httpServletResponse.addCookie(cookie);
        model.addAttribute("state", "已注销！");
        model.addAttribute("info", "用户已经注销，重新登录请访问/user/login");
        return "tip";
    }

    @GetMapping({"/sid/{sid}"})
    public String getAllBySid(@PathVariable("sid") Integer sid, Model model) {
        model.addAttribute("user", userService.selectOneUser(sid, null));
        return "user/userInfo";
    }

    @GetMapping({"/username/{username}"})
    public String getAllByUsername(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.selectOneUser(null, username));
        return "user/userInfo";
    }

    @GetMapping({"/modified/{sid}"})
    public String modifiedPage(@PathVariable("sid") Integer sid, Model model) {
        model.addAttribute("user", userService.selectOneUser(sid, null));
        return "user/userModified";
    }

    @PostMapping({"/modified"})
    public String modified(@CookieValue(value = "token", defaultValue = "") String token, // 获取Cookie内的token信息，设置默认值防止没有token(比如还没登录)
                           @ModelAttribute("user") User user, Model model) {
        TokenInfo tokenInfo = Utils.getInfoByToken(token);
        if (tokenInfo != null) {
            try {
                log.info(user.toString());
                userService.modifiedUser(user);
                model.addAttribute("status", "修改成功！");
                model.addAttribute("info", "用户SID： " + user.getSid() + "的信息已经被修改！");
            } catch (Exception e) {
                log.error(e.toString());
                model.addAttribute("status", "修改失败！");
                model.addAttribute("info", "用户SID： " + user.getSid() + "的信息数据库写入失败！");
            }
        } else {
            model.addAttribute("status", "你没有权限！");
            model.addAttribute("info", "请重新登陆后再试。");
        }
        return "tip";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserVO());
        return "user/userRegister";
    }

    @PostMapping({"/register"})
    public String register(@CookieValue(value = "token", defaultValue = "") String token, // 获取Cookie内的token信息，设置默认值防止没有token(比如还没登录)
                           @ModelAttribute("user") User user, Model model) {
        // 注释掉这一段提供登陆后才能注册功能，保证权限不外泄
//        TokenInfo tokenInfo = Utils.getInfoByToken(token);
//        if (tokenInfo != null) {
//            try {
//                log.info(user.toString());
//                userService.register(user);
//                model.addAttribute("status", "注册成功！");
//                model.addAttribute("info", "用户SID： " + user.getSid() + "的用户已经被创建！");
//            } catch (Exception e) {
//                log.error(e.toString());
//                model.addAttribute("status", "修改失败！");
//                model.addAttribute("info", "用户SID： " + user.getSid() + "的信息数据库写入失败！");
//            }
//        } else {
//            model.addAttribute("status", "你没有权限！");
//            model.addAttribute("info", "请重新登陆后再试。");
//        }

        // 不验证权限，直接注册
        try {
            log.info(user.toString());
            userService.register(user);
            model.addAttribute("status", "注册成功！");
            model.addAttribute("info", "用户SID： " + user.getSid() + "的用户已经被创建！");
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("status", "修改失败！");
            model.addAttribute("info", "用户SID： " + user.getSid() + "的信息数据库写入失败！");
        }
        return "tip";

    }
}
