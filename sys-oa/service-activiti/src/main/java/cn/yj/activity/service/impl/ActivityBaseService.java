package cn.yj.activity.service.impl;

import cn.yj.common.AbstractController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 16:23
 */
public abstract class ActivityBaseService extends AbstractController
{

    @Autowired
    protected RepositoryService repositoryService;


    @Autowired
    protected HistoryService historyService;


    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected IdentityService identityService;


    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;


    @Autowired
    protected TaskService taskService;


    @Autowired
    protected ObjectMapper objectMapper;

}
