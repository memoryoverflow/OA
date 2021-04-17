package cn.yj.activity.service.impl;

import cn.yj.activity.entity.po.Form;
import cn.yj.activity.service.IFormService;
import cn.yj.activity.service.IProcInstService;
import cn.yj.activity.service.IProcessDef;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-12 14:54
 */
@Service
public class ProcInstServiceImpl extends ActivityBaseService implements IProcInstService
{

    @Autowired
    IFormService iFormService;

    @Autowired
    IProcessDef processDef;

    @Override
    public void startProcessInstanceById(@Require String procDefId)
    {
        getNodes(procDefId);

        Form form = iFormService.selectByProcDefId(procDefId);
        if (StringUtils.isNull(form))
        {
            throw new ServiceException("请先填写表单");
        }
        UserTask procDefFirstUserTask = processDef.getProcDefFirstUserTask(procDefId);

        // 任务人
        String assignee = procDefFirstUserTask.getAssignee();
        String agentUserCode = form.getAgentUserCode();
        if (StringUtils.isBlank(agentUserCode) && StringUtils.isBlank(assignee))
        {
            throw new ServiceException("请选择审批人");
        }

        Map<String, Object> params = null;
        if (StringUtils.isBlank(agentUserCode))
        {
            form.setAgentUserCode(agentUserCode);
            iFormService.updateById(form);
        }
        else
        {
            // 设置变量 下一个节点获取
            params = new HashMap<>();
            params.put("agentUserCode", form.getAgentUserCode());
            params.put("formId", form.getId());
        }
        // 流程发起人 （通过此id获取发起人的流程实例记录,统计申请记录次数）
        identityService.setAuthenticatedUserId(form.getCreateUserId());

        //  启动流程
        runtimeService.startProcessInstanceById(procDefId, form.getId(), params);
        //taskService.addCandidateUser();
        List<Task> list = taskService.createTaskQuery().processDefinitionId(procDefId).list();

    }


    private ObjectNode getNodes(String processDefId)
    {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) processDefinition;
        List<ActivityImpl> activities = definition.getActivities();
        activities.forEach(activity -> {
            System.out.println(activity);
        });

        return null;
    }
}
