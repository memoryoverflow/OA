package cn.yj.activity.service.impl;

import cn.yj.activity.service.IProcessDef;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.commons.utils.StringUtils;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;

/**
 * <br>
 * 流程定义接口
 * @author 永健
 * @since 2020-12-08 20:29
 */
@Service
public class ProcessDefImpl extends ActivityBaseService implements IProcessDef
{

    @Override
    public Page<Object> listPage(String processName, Page page)
    {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(processName))
        {
            processDefinitionQuery.processDefinitionKey(processName);
        }
        return page.setRows(processDefinitionQuery.listPage(page.getPageNum()-1,page.getPageSize()));
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId)
    {
        repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId)
    {

    }
}
