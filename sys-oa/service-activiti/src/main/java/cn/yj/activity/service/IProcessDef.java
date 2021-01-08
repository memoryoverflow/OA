package cn.yj.activity.service;


import cn.yj.activity.entity.Nodes;
import cn.yj.activity.entity.po.ProcDfExtend;
import cn.yj.annotation.pagehelper.page.Page;
import org.activiti.bpmn.model.UserTask;

import java.io.InputStream;
import java.util.LinkedList;

/**
 * <br>
 * 流程定义接口
 * @author 永健
 * @since 2020-12-08 20:29
 */
public interface IProcessDef
{
    Page<Object> listPage(String processName,Page page);

    /**
     * 流程定义激活
     * @param processDefinitionId 流程定义Id
     */
    void activateProcessDefinitionById(String processDefinitionId);


    /**
     * 挂起流程定义
     * 如果流程定义处于挂起状态，则无法启动新的流程实例
     * @param processDefinitionId
     */
    void suspendProcessDefinitionById(String processDefinitionId);

    InputStream getResourceImgStream(String deployId);

    void editExtendUpdate(ProcDfExtend procDfExtend);

    /**
     * 获取流程的各个节点
     * @return
     */
    LinkedList<Nodes> getProcDefNodesByProcDefId(String procDefId);
    UserTask getProcDefFirstUserTask(String procDefId);
}
