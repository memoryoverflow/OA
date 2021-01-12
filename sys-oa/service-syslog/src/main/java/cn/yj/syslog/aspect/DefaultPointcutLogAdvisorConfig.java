package cn.yj.syslog.aspect;

import cn.yj.syslog.service.ISysLogService;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-12 20:26
 */
@Configuration
public class DefaultPointcutLogAdvisorConfig
{
    private static final String EXECUTION = "@within(org.springframework.web.bind.annotation.RestController)|| @within(org.springframework.stereotype.Controller)";

    @Bean(value = "cn.yj.syslog.aspect.DefaultPointcutAdvisorConfig.log")
    public DefaultPointcutAdvisor defaultPointcutAdvisor(ISysLogService iSysLogService)
    {
        OperateLogMethodInterceptor interceptor = new OperateLogMethodInterceptor(iSysLogService);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EXECUTION);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
        return advisor;
    }

}
