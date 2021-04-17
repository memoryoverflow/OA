package cn.yj;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IModelService;
import cn.yj.admin.entity.po.Permission;
import cn.yj.admin.mapper.RolePermissionMapper;
import cn.yj.admin.service.IPermissionService;
import cn.yj.annotation.pagehelper.page.Page;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 17:19
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class ActivityModelTest{
    static IModelService iModelService;
    static RuntimeService runtimeService;
    static IdentityService identityService;
    static TaskService taskService;
    static IPermissionService permissionService;
    static RolePermissionMapper rolePermissionMapper;

    static {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cn.yj");
        iModelService = context.getBean(IModelService.class);
        runtimeService = context.getBean(RuntimeService.class);
        identityService = context.getBean(IdentityService.class);
        taskService = context.getBean(TaskService.class);
        permissionService = context.getBean(IPermissionService.class);
        rolePermissionMapper = context.getBean(RolePermissionMapper.class);
    }

    /**
     * model创建
     */
    @Test
    public void createModel() {
        iModelService.crateModel(new ModelVo("test-模型", "测试模型", "test_key"));
    }

    /**
     * 发起流程申请
     */
    @Test
    public void startedProcess() {

        // 1、设置发起人
        //identityService.setUserInfo();

        runtimeService.startProcessInstanceById("请假流程:1:30005");
    }

    @Test
    public void authAdmin() {
        Page<Permission> permissionPage = new Page<>();
        permissionPage.setPageSize(1000);
        Page<Permission> list = permissionService.findList(new HashMap<>(), permissionPage);

        list.getRows().forEach(p -> {
            rolePermissionMapper.insert("1", p.getId());
        });
    }
}
