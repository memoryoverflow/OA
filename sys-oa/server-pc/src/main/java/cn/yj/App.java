package cn.yj;

import cn.yj.activity.EnableActivity;
import cn.yj.admin.frame.shiro.EnableShiroManager;
import cn.yj.params.check.EnableCheckMethodParams;
import cn.yj.tools.readconfig.EnableReadConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-28 20:54
 */
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
@EnableActivity
@MapperScan(basePackages = {"cn.yj.**.mapper", "cn.yj.core"})
@EnableReadConfig(classLoader = App.class)
@EnableCheckMethodParams
@EnableShiroManager
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
