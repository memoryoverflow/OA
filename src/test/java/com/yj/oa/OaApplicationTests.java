package com.yj.oa;

import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.json.JsonUtils;
import com.yj.oa.project.mapper.PermissionMapper;
import com.yj.oa.project.mapper.RolePermissionMapper;
import com.yj.oa.project.mapper.UserMapper;
import com.yj.oa.project.po.Attend;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.RolePermission;
import com.yj.oa.project.po.User;
import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.service.attend.IAttendService;
import com.yj.oa.project.service.attendCount.IAttendCountService;
import com.yj.oa.project.service.menu.IPermissionService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.yj.oa.common.utils.DateUtils.StrToDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests{

    @Autowired
    RolePermissionMapper mapper;

    @Autowired
    IAttendCountService iAttendService;

    @Autowired
    TaskService taskService;

    @Autowired
    PermissionMapper permissionMapper;

    @Test
    public void contextLoads()
    {

//        Date date=DateUtils.StrToDate("2018-10-10 11:12:11");
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        System.out.println(c.get(Calendar.HOUR_OF_DAY));
//        System.out.println(c.get(Calendar.MINUTE));
//        System.out.println(c.get(Calendar.SECOND));
    }
}