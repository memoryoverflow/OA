package com.yj.oa.common.utils;

import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  @author 永健
 *
 *  http请求头处理工具
 */
public class HttpHeaderUtil{
    private static Logger logger = LoggerFactory.getLogger(HttpHeaderUtil.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";


//    引入依赖
//        <dependency>
//            <groupId>eu.bitwalker</groupId>
//            <artifactId>UserAgentUtils</artifactId>
//            <version>1.21</version>
//        </dependency>


    /**
     * 从请求头中获取 操作系统
     */
    public static String getSys(String USER_AGENT)
    {
        UserAgent agent = UserAgent.parseUserAgentString(USER_AGENT);
        OperatingSystem sys = agent.getOperatingSystem();
        return sys.getName();
    }

    /**
     * 从请求头中获取 浏览器类型
     */
    public static String getBrowser(String USER_AGENT)
    {
        UserAgent agent = UserAgent.parseUserAgentString(USER_AGENT);
        Browser browser = agent.getBrowser();
        return browser.getName();
    }


    /**
     *
     * @描述 获取请求头
     *
     * @date 2018/9/20 0:12
     */
    public static String getUserAgent()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        String header = request.getHeader("User-Agent");
        return header;
    }


    /**
     *  获取Ip
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取查询结果
     */
    private static String sendPost(String content, String encoding)
    {
        URL url = null;
        HttpURLConnection connection = null;
        try
        {
            url = new URL(IP_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        }
        catch (IOException e)
        {
            System.out.println("温馨提醒：您的主机已经断网，请您检查主机的网络连接");
            System.out.println("根据IP获取所在位置----------错误消息：" + e.getMessage());
        }
        finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 获取ip所在地
     */
    public static String getAddress(String ip)
    {
        String address = "";
        try
        {
            address = sendPost("ip=" + ip, "UTF-8");

            JSONObject json = JSONObject.parseObject(address);
            JSONObject object = json.getObject("data", JSONObject.class);
            String region = object.getString("region");
            String country = object.getString("country");
            String city = object.getString("city");
            address =country+" "+ region + " " + city;
        }
        catch (Exception e)
        {
            logger.error("根据IP获取所在位置----------错误消息：[{}]" , e.getMessage());
        }
        return address==null?"获取地址失败":address;
    }

}
