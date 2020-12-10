package com.yj.oa.project.controller.VerificationCode;

import com.yj.oa.project.po.VerificationCode.ValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 永健
 */
@org.springframework.stereotype.Controller
@RequestMapping("/code")
public class Controller{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //    获取验证码
    @RequestMapping("/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            ValidateCodeUtil randomValidateCode = new ValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }
}
