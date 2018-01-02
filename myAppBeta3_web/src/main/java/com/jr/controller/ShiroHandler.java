package com.jr.controller;

import com.jr.dubboInterface.UserDubboInterface;
import com.jr.entity.Authority;
import com.jr.dubboInterface.UserDubboInterface;
import com.jr.utils.VerifyCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@SuppressWarnings(value = "all")
@Controller
public class ShiroHandler extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(ShiroHandler.class);

    /**
     * 这里用作存入session的名称
     */
    private static final String SESSION_KEY_OF_RAND_CODE = "randCode"; // todo 要统一常量

    @Autowired
    private HttpSession session;
    @Autowired
    private UserDubboInterface userDubboInterface;

    /**
     * 生成验证码
     * @return
     */
    @RequestMapping(value = "/userLogin/validateCode")
    public void validateCode(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        VerifyCode verifyCode = new VerifyCode();

        BufferedImage image = verifyCode.getImage();
        //得到验证码，将验证码存入SESSION
        // 取随机产生的认证码(4位数字)
        final String resultCode = verifyCode.getCode();
        // 将认证码存入SESSION
        request.getSession().setAttribute(SESSION_KEY_OF_RAND_CODE, resultCode);
        // 图象生效
//        image.getGraphics().dispose();

        ServletOutputStream outputStream = response.getOutputStream();
        // 输出图象到页面
        verifyCode.saveImage(image,outputStream);

    }


    @RequiresRoles("admin")
    @RequestMapping("shiro-test")
    public String test() {
        System.out.println("有权限，可以执行方法！");
        return "redirect:/list.jsp";
    }

    @RequestMapping("/shiro-login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("validateCode") String validateCode,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(validateCode)){
            redirectAttributes.addAttribute("validateMsg","验证码不存在");
            return "redirect:/login.jsp";
        }
        String sessionValidateCode = (String) request.getSession().getAttribute(SESSION_KEY_OF_RAND_CODE);
        if(StringUtils.equalsIgnoreCase(sessionValidateCode,validateCode)){
            //获取当前用户
            Subject currentUser = SecurityUtils.getSubject();
            //检验当前用户是否已经登录
            if (!currentUser.isAuthenticated()) {
                //若没有登录，则把用户名和密码封装为一个UsernamePasswordToken对象
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                token.setRememberMe(true);
                try {
                    //执行登录操作
                    //会比对密码，会调用realm的doGetAuthenticationInfo方法
                    currentUser.login(token);
                    session.setAttribute("currentUserName", currentUser.getPrincipal());
                }
                // ... catch more exceptions here (maybe custom ones specific to your application?
                catch (AuthenticationException ae) {
                    System.out.println("登录失败！" + ae.getMessage());
                    return "redirect:/login.jsp";
                }
            }
            //重定向到首页面
            return "redirect:/index";
        }
        //addFlashAttribute的值放在FLASH_MAPS中
        session.removeAttribute("org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS");
        redirectAttributes.addFlashAttribute("validateMsg","验证码不正确");
        return "redirect:/login.jsp";
    }


    /**
     * 跳转到首页
     *
     * @return
     * @author jql
     */
    @RequestMapping(value = "/index")
    public String toIndexPage(ModelMap modelMap) {
        log.debug("跳转到首页。");

//		List<Authority> authorities = userDubboInterface.findAllAuthorities();
//		modelMap.put("authorities", authorities);
        return "index";
    }

    /**
     * 加载左侧权限列表
     *
     * @return
     * @author jql
     */
    @RequestMapping(value = "/loadAuthoritiesTreeData")
    @ResponseBody
    public List<Authority> loadAuthoritiesTreeData() {
        log.debug("加载左侧权限列表。");
        //根据用户名查询具有的权限
//		String userName = (String) session.getAttribute("currentUserName");
//		List<Authority> authorities = userDubboInterface.findAllAuthoritiesByUserName(userName);
        List<Authority> authorities = userDubboInterface.findAllAuthorities();
        return authorities;
    }

    /**
     * 加载跑马灯内容
     *
     * @return
     * @author jql
     */
    @RequestMapping(value = "/getMarqueeContent")
    @ResponseBody
    public String getMarqueeContent() {
        log.debug("加载跑马灯内容");
        Date date = new Date();
        return date.toString();
    }


}
