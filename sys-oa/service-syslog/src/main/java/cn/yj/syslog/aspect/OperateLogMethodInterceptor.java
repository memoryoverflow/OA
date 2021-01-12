package cn.yj.syslog.aspect;

import cn.yj.common.*;
import cn.yj.commons.utils.ServletUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.syslog.entity.SysLog;
import cn.yj.syslog.service.ISysLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-17 17:34
 */
public class OperateLogMethodInterceptor implements MethodInterceptor
{
    private ISysLogService iSysLogService;

    public OperateLogMethodInterceptor(ISysLogService iSysLogService)
    {
        this.iSysLogService = iSysLogService;
    }

    private static volatile Set<HttpServletRequest> LOCAL = new HashSet<>();


    final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        Method method = methodInvocation.getMethod();
        OperateLog annotation = method.getAnnotation(OperateLog.class);
        if (annotation == null)
        {
            return methodInvocation.proceed();
        }


        // 参数类型
        //Class<?>[] parameterTypes = method.getParameterTypes();

        // 参数值
        // Object[] arguments = methodInvocation.getArguments();


        Object proceed;
        Throwable throwable = null;
        String operateUser = "";
        try
        {
            // 操作用户
            if (ShiroUtils.getCurrentUser() != null)
            {
                operateUser = ((LoginUser) ShiroUtils.getCurrentUser()).getLoginName();
            }
            proceed = methodInvocation.proceed();

        } catch (Throwable e)
        {
            e.printStackTrace();
            throwable = e;
            throw throwable;
        } finally
        {
            HttpServletRequest request = ServletUtils.getRequest();
            if (!LOCAL.contains(request))
            {
                LOCAL.add(request);
                System.out.println(request);


                String requestBody = getReqParams(methodInvocation.getArguments(), method.getParameterTypes(), methodInvocation.getMethod().getParameters());

                new Job(request, throwable, method, operateUser, annotation, requestBody).doHandle();
                //executorService.execute(new Job(request, throwable, method, operateUser, annotation, stringHashMap));
            }
            else
            {
                LOCAL.remove(request);
            }
        }
        return proceed;
    }

    class Job
    {
        private HttpServletRequest request;
        private Throwable e;
        private Method method;
        private String operateUser;
        private OperateLog operateLog;
        private String params;

        public Job(HttpServletRequest request, Throwable e, Method method, String operateUser, OperateLog operateLog, String params)
        {
            this.request = request;
            this.e = e;
            this.method = method;
            this.operateUser = operateUser;
            this.operateLog = operateLog;
            this.params = params;
        }

        public void doHandle()
        {
            SysLog operLog = new SysLog(UUIdUtils.getUUId32());

            String host = HttpHeaderUtil.getIpAddr(request);

            String requestURI = request.getRequestURI();


            String userAgent = HttpHeaderUtil.getUserAgent(request);
            String browser = HttpHeaderUtil.getBrowser(userAgent);
            String sys = HttpHeaderUtil.getSys(userAgent);


            String className = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();
            String type = request.getMethod();
            String methodInfo = className + "/" + methodName + "()" + "/" + type;

            operLog.setOperUser(operateUser);
            operLog.setHost(host);
            operLog.setDescr(operateLog.describe());

            // 淘宝的不给免费用了
            //String address = HttpHeaderUtil.getAddress(ip);
            //operLog.setIpAddress(address);

            operLog.setUrl(requestURI);
            operLog.setSys(sys);
            operLog.setBrowser(browser);
            operLog.setParams(params);
            operLog.setType("正常");
            operLog.setMethod(methodInfo);
            operLog.setCreateTime(new Date());
            operLog.setUpdateTime(operLog.getCreateTime());

            if (e != null)
            {
                operLog.setErrorMsg(e.getMessage());
                StringBuffer errMsg = new StringBuffer();
                if (e.getStackTrace().length > 0)
                {
                    operLog.setType("异常");
                    StackTraceElement stackTraceElement = e.getStackTrace()[0];
                    int lineNumber = stackTraceElement.getLineNumber();
                    String fileName = stackTraceElement.getFileName();
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    e.printStackTrace(printWriter);
                    errMsg.append("<p>").append("url:").append(request.getRequestURI()).append("</p>");
                    errMsg.append("<p>").append("参数:").append(JSON.toJSONString(request.getParameterMap())).append("</p>");
                    errMsg.append("<p>").append("方法:").append(className + "/" + methodName + "()" + "/" + type).append("</p>");
                    errMsg.append("<p>").append("错误文件:").append(fileName).append("->").append(lineNumber).append(" 出现错误");
                    errMsg.append("<p>").append("错误信息:").append(stringWriter.toString());
                    operLog.setErrorMsg(stringWriter.toString());
                }
            }
            // 保存
            executorService.execute(() -> iSysLogService.insert(operLog));
        }
    }

    private String getReqParams(Object[] params, Object[] parameterTypes, Parameter[] parameters)
    {
        for (int i = 0; i < parameterTypes.length; i++)
        {
            HashMap<String, Object> map = new HashMap<>();
            Object paramValue = params[i];
            Object parameterType = parameterTypes[i];
            if (parameterType == MultipartFile.class)
            {
                map.put(parameters[i].getName(), paramValue);
                params[i] = map;
            }
            else
            {
                if (paramValue == null || StringUtils.isBlank(paramValue.toString()))
                {
                    continue;
                }
                map.put(parameters[i].getName(), paramValue);
                params[i] = map;
            }
        }
        System.out.println(JSONArray.toJSONString(params));
        return JSONArray.toJSONString(params);
    }

}
