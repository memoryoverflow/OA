package cn.yj.admin.frame.shiro.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-04-02 10:03
 */
public class ShiroDefaultWebSessionManagerConfig
{
    @Bean("sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager()
    {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        // 禁用 session 管理
        return defaultWebSessionManager;
    }
}
