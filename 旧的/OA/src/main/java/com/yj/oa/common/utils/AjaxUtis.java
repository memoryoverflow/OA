package com.yj.oa.common.utils;

import com.alibaba.druid.support.json.JSONUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author 永健
 * ajax请求判断以及输出结果工具类
 */
public class AjaxUtis{
    /**
     *  判断是否是ajax请求
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        return (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals(httpRequest.getHeader("X-Requested-With").toString()));
    }


    public static void out(ServletResponse response, Map<String, String> resultMap) {
        PrintWriter out = null;
        try {
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //设置返回类型
            response.setContentType("application/json");
            out = response.getWriter();
            //输出
            response.getWriter().write(JSONUtils.toJSONString(resultMap));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            out.flush();
            out.close();
        }

    }
}
