package cn.yj.syslog.aspect;

import cn.yj.common.OperateLog;
import cn.yj.syslog.service.ISysLogService;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 永健
 * @since 2021-04-15 14:29
 */
@Component
public class OperateStaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor{

    public OperateStaticMethodMatcherPointcutAdvisor(ISysLogService logService) {
        super.setAdvice(new OperateLogMethodInterceptor(logService));
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.getAnnotation(OperateLog.class) != null;
    }
}
