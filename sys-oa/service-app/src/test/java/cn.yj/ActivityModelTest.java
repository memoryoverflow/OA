package cn.yj;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IModelService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 17:19
 */


public class ActivityModelTest
{
    static IModelService iModelService;
    static RuntimeService runtimeService;
    static IdentityService identityService;
    static TaskService taskService;
    static
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cn.yj");
        iModelService = context.getBean(IModelService.class);
        runtimeService = context.getBean(RuntimeService.class);
        identityService = context.getBean(IdentityService.class);
        taskService = context.getBean(TaskService.class);
    }

    /**
     * model创建
     */
    @Test
    public void createModel()
    {
        iModelService.crateModel(new ModelVo("test-模型", "测试模型", "test_key"));
    }

    /**
     * 发起流程申请
     */
    @Test
    public void startedProcess(){

        // 1、设置发起人
        //identityService.setUserInfo();

        runtimeService.startProcessInstanceById("请假流程:1:30005");
    }
}
