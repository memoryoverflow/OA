package cn.yj.syslog.aspect;

import cn.yj.common.*;
import cn.yj.commons.utils.ServletUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.syslog.entity.SysLog;
import cn.yj.syslog.service.ISysLogService;
import com.alibaba.fastjson.JSONArray;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-17 17:34
 */
public class OperateLogMethodInterceptor implements MethodInterceptor{

    private ISysLogService iSysLogService;

    public OperateLogMethodInterceptor(ISysLogService iSysLogService) {
        this.iSysLogService = iSysLogService;
    }

    private static volatile Set<HttpServletRequest> LOCAL = ConcurrentHashMap.newKeySet();

    private static final Set<String> fields;

    static {
        fields = new HashSet<>();
        fields.add("password");
    }

    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        OperateLog annotation = method.getAnnotation(OperateLog.class);
        if (annotation == null) {
            return methodInvocation.proceed();
        }
        HttpServletRequest request = ServletUtils.getRequest();
        System.out.println(request);
        Throwable throwable = null;
        if (!LOCAL.contains(request)) {
            Object proceed;
            try {
                LOCAL.add(request);
                proceed = methodInvocation.proceed();
            } catch (Throwable e) {
                throwable = e;
                throw throwable;
            } finally {
                executorService.execute(new Job(throwable, methodInvocation, fillSysLog(request), annotation));
            }
            return proceed;
        }
        else {
            LOCAL.remove(request);
        }
        return methodInvocation.proceed();
    }

    private SysLog fillSysLog(HttpServletRequest request) {
        SysLog operaLog = new SysLog(UUIdUtils.getUUId32());
        operaLog.setHost(HttpHeaderUtil.getIpAddr(request));
        operaLog.setUrl(request.getRequestURI());
        String userAgent = HttpHeaderUtil.getUserAgent(request);
        operaLog.setSys(HttpHeaderUtil.getSys(userAgent));
        operaLog.setBrowser(HttpHeaderUtil.getBrowser(userAgent));
        operaLog.setMethod(request.getMethod());
        return operaLog;
    }

    class Job implements Runnable{
        private Throwable e;
        private Method method;
        private MethodInvocation methodInvocation;
        private String operateUser;
        private OperateLog operateLog;
        private SysLog sysLog;
        private String params;

        public Job(Throwable e, MethodInvocation method, SysLog sysLog, OperateLog operateLog) {
            this.e = e;
            this.method = method.getMethod();
            this.methodInvocation = method;
            this.operateLog = operateLog;
            this.sysLog = sysLog;
        }

        @Override
        public void run() {
            this.params = getReqParams(methodInvocation.getArguments(), method.getParameterTypes(), methodInvocation.getMethod().getParameters());
            this.doHandle();
        }

        public void doHandle() {

            // 操作用户
            if (ShiroUtils.getCurrentUser() != null) {
                operateUser = ((LoginUser) ShiroUtils.getCurrentUser()).getLoginName();
            }
            sysLog.setOperUser(operateUser);
            String className = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();
            String type = sysLog.getMethod();
            sysLog.setMethod(className + "/" + methodName + "()/" + type);

            // ip所在地
            sysLog.setIpAddress(HttpHeaderUtil.getAddress(sysLog.getHost()));

            sysLog.setOperUser(operateUser);
            sysLog.setDescr(operateLog.describe());
            sysLog.setParams(this.params);
            sysLog.setType("正常");
            sysLog.setCreateTime(new Date());
            sysLog.setUpdateTime(sysLog.getCreateTime());

            if (e != null) {
                sysLog.setErrorMsg(e.getMessage());
                StringBuffer errMsg = new StringBuffer();
                if (e.getStackTrace().length > 0) {
                    sysLog.setType("异常");
                    StackTraceElement stackTraceElement = e.getStackTrace()[0];
                    int lineNumber = stackTraceElement.getLineNumber();
                    String fileName = stackTraceElement.getFileName();
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    e.printStackTrace(printWriter);
                    errMsg.append("<p>").append("url:").append(sysLog.getUrl()).append("</p>");
                    errMsg.append("<p>").append("参数:").append(sysLog.getParams()).append("</p>");
                    errMsg.append("<p>").append("方法:").append(sysLog.getMethod()).append("</p>");
                    errMsg.append("<p>").append("错误文件:").append(fileName).append("->").append(lineNumber).append(" 出现错误");
                    errMsg.append("<p>").append("错误信息:").append(stringWriter);
                    sysLog.setErrorMsg(stringWriter.toString());
                }
            }
            System.out.println(sysLog);
            iSysLogService.insert(sysLog);
        }
    }

    private String getReqParams(Object[] params, Object[] parameterTypes, Parameter[] parameters) {
        for (int i = 0; i < parameterTypes.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            Object paramValue = params[i];
            Object parameterType = parameterTypes[i];
            if (parameterType == MultipartFile.class) {
                String name = parameters[i].getName();
                map.put(name, paramValue);
                params[i] = map;
            }
            else {
                if (paramValue == null || StringUtils.isBlank(paramValue.toString())) {
                    continue;
                }
                String name = parameters[i].getName();

                if (fields.contains(name)) {
                    paramValue = "******";
                }
                else {
                    Class<?> aClass = paramValue.getClass();
                    if (aClass == Map.class || aClass == HashMap.class || aClass == ConcurrentHashMap.class || aClass == LinkedHashMap.class) {
                        Map mv = (Map) paramValue;
                        mv.forEach((k, v) -> {
                            if (fields.contains(k)) {
                                mv.put(k, "*****");
                            }
                        });
                        paramValue = mv;
                    }
                    else {
                        // 当作javaBean对象去解析
                        try {
                            Field[] declaredFields = aClass.getSuperclass().getDeclaredFields();
                            Field[] allFields = aClass.getDeclaredFields();
                            Map<String, Field> fieldsMap = Arrays.stream(allFields).collect(Collectors.toMap(Field::getName, field -> field));
                            Map<String, Field> fieldsMap2 = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, field -> field));
                            fieldsMap.putAll(fieldsMap2);
                            Object finalParamValue = paramValue;
                            fieldsMap.forEach((k, v) -> {
                                if (fields.contains(k)) {
                                    v.setAccessible(true);
                                    try {
                                        v.set(finalParamValue, "******");
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (SecurityException e) {
                            //e.printStackTrace();
                        }
                    }
                }
                map.put(name, paramValue);
                params[i] = map;
            }
        }
        String mParams = JSONArray.toJSONString(params);
        System.out.println(mParams);
        return mParams;
    }

    private final static Set<Class<?>> BASI_DATA_TYPE = new HashSet<>();

    static {
        BASI_DATA_TYPE.add(String.class);
        BASI_DATA_TYPE.add(Integer.class);
        BASI_DATA_TYPE.add(Long.class);
        BASI_DATA_TYPE.add(Float.class);
        BASI_DATA_TYPE.add(Boolean.class);
        BASI_DATA_TYPE.add(Double.class);
        BASI_DATA_TYPE.add(Short.class);
        BASI_DATA_TYPE.add(Byte.class);
    }

    private boolean isBasicDataType(Class<?> type) {
        return BASI_DATA_TYPE.contains(type);
    }

}
