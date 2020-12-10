package cn.yj.activity;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IModelService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 17:19
 */
public class AppTest
{
    @Test
    public void Test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cn.yj.activity");
        IModelService iModelService = context.getBean(IModelService.class);
        iModelService.crateModel(new ModelVo("test","测试模型","test_key"));
    }
}


