package com.yj.oa.framework.aspect;

import com.yj.oa.common.utils.HttpHeaderUtil;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.ShiroUtils;
import com.yj.oa.framework.aspect.enums.OperLogStatusEnum;
import com.yj.oa.framework.aspect.enums.OperLogTypeEnum;
import com.yj.oa.project.po.OperLog;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.operlog.IOperLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 系统日志切点类
 *
 * @author 永健
 */
@Aspect
@Component
public class SystemLogAspect{
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);


    @Autowired
    IOperLogService logServerce;

    @Autowired
    private ThreadPoolExecutor threadPoolTaskExecutor;


    /**
     * 方法规则拦截
     */
    @Pointcut("execution(* com.yj.oa.project.controller..*(..))")
    public void controllerAspect()
    {
    }


    /**
     * 方法正常运行后 执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning("controllerAspect() && @annotation(operlog)")
    public void AfterReturning(JoinPoint joinPoint, Operlog operlog)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        this.HandelLog(joinPoint, request, null);

    }


    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "controllerAspect() && @annotation(operlog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e, Operlog operlog)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        this.HandelLog(joinPoint, request, e);
    }


    /**
     * 保存日志线程
     *
     * @author lin.r.x
     */
    private static class SaveLogThread extends Thread{

        private OperLog log;
        private IOperLogService logServerce;


        public SaveLogThread(OperLog log, IOperLogService logServerce)
        {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.logServerce = logServerce;
        }

        @Override
        public void run()
        {
            logServerce.insertSelective(log);
        }

    }


    /**
     *
     * @描述: 处理封装 OperLog
     *
     * @date: 2018/10/2 14:37
     */
    public void HandelLog(JoinPoint joinPoint, HttpServletRequest request, Throwable e)
    {

        //用户
        User user = ShiroUtils.getUser();

        // 获得注解
        Operlog operlog = getControllerMethodDescription2(joinPoint);
        //描述
        String descr = operlog.descr();
        //模块
        String modal = operlog.modal();

        OperLog operLog = new OperLog();
        operLog.setDescr(descr);
        operLog.setOperModal(modal);

        //设置 ip
        String ip = HttpHeaderUtil.getIpAddr(request);
        operLog.setHost(ip);
        //设置ip 所在地址
        operLog.setIpAddress(HttpHeaderUtil.getAddress(ip));

        //操作时间
        operLog.setTime(new Date());

        //url
        String requestURI = request.getRequestURI();
        operLog.setUrl(requestURI);


        System.out.println(user);

        //操作人
        if (!StringUtils.isNull(user))
        {
            operLog.setOperUser(user.getUid());
            Integer deptId = user.getDept();
            if (deptId!=null)
            {
                //用户部门
                operLog.setDeptId(String.valueOf(deptId));
            }
        }

        //操作系统
        String userAgent = request.getHeader("User-Agent");
        String browser = HttpHeaderUtil.getBrowser(userAgent);
        operLog.setBrowser(browser);

        //浏览器
        String sys = HttpHeaderUtil.getSys(userAgent);
        operLog.setSys(sys);

        //操作方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String type = request.getMethod();
        operLog.setMethod(className + "/" + methodName + "()" + "/" + type);

        //请求参数
        Map<String, String[]> params = request.getParameterMap();
        operLog.setParams(operLog.getMapToParams(params));


        operLog.setStatus(OperLogStatusEnum.SUCCESS_STATUS.getValue());
        operLog.setType(OperLogTypeEnum.SUCCESS_TYPE.getValue());

        if (!StringUtils.isNull(e))
        {
            operLog.setStatus(OperLogStatusEnum.ERROR_STATUS.getValue());
            operLog.setType(OperLogTypeEnum.ERROR_TYPE.getValue());
            operLog.setErrorMsg(e.getMessage());
        }


        threadPoolTaskExecutor.execute(new SaveLogThread(operLog, logServerce));

    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     *
     * @return 方法描述
     */
    public static Operlog getControllerMethodDescription2(JoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Operlog annotation = method.getAnnotation(Operlog.class);
        return annotation;
    }

}


