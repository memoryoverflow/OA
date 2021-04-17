package cn.yj.activity.service.impl;

import cn.yj.activity.entity.Nodes;
import cn.yj.activity.entity.po.ProcDfExtend;
import cn.yj.activity.mapper.ProcDfExtendMapper;
import cn.yj.activity.service.IProcessDef;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.CheckObjectValue;
import cn.yj.params.check.annotation.KeyValue;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <br>
 * 流程定义接口
 *
 * @author 永健
 * @since 2020-12-08 20:29
 */
@Service
public class ProcessDefImpl extends ActivityBaseService implements IProcessDef
{

    @Autowired
    ProcDfExtendMapper procDfExtendMapper;

    @Override
    public Page<Object> listPage(String processName, Page page)
    {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(processName))
        {
            processDefinitionQuery.processDefinitionKey(processName);
        }
        int pageSize = page.getPageSize();
        long count = processDefinitionQuery.count();

        // 这里会出现哥死循环，自己引用自己，无限递归，导致Controller无法retunr
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(page.getPageNum() - 1, pageSize);
        List<Map<String, Object>> newResults = new ArrayList<>();
        for (ProcessDefinition p : processDefinitions)
        {
            Map<String, Object> map = new HashMap<>();
            ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) p;


            // 角色过滤
            ProcDfExtend procDfExtend = procDfExtendMapper.selectById(definitionEntity.getId());
            if (StringUtils.isNotNull(procDfExtend))
            {
                String useRole = procDfExtend.getUseRole();
                if (StringUtils.isNotBlank(useRole))
                {
                    boolean auth = false;
                    List<String> useRoles = Arrays.asList(useRole.split(","));
                    List<Map<String, Object>> userRoles = (List<Map<String, Object>>) getUserRoles();
                    for (Map<String, Object> mapRole : userRoles)
                    {
                        String id = mapRole.get("id").toString();
                        if (useRoles.contains(id))
                        {
                            auth = true;
                            break;
                        }
                    }

                    if (!auth)
                    {
                        continue;
                    }
                }
            }

            map.put("extendForm", procDfExtend);
            map.put("id", definitionEntity.getId());
            map.put("tenantId", definitionEntity.getTenantId());
            map.put("category", definitionEntity.getCategory());
            map.put("description", definitionEntity.getDescription());
            map.put("diagramResourceName", definitionEntity.getDiagramResourceName());
            map.put("name", definitionEntity.getName());
            map.put("key", definitionEntity.getKey());
            map.put("version", definitionEntity.getVersion());
            map.put("suspensionState", definitionEntity.getSuspensionState());
            map.put("deploymentId", definitionEntity.getDeploymentId());
            map.put("resourceName", definitionEntity.getResourceName());
            newResults.add(map);
        }

        page.setRows(newResults);
        page.setPageSize(pageSize);
        page.setTotal(count);
        page.setPages((int) ((count % pageSize == 0) ? (count / pageSize) : ((count / pageSize) + 1)));
        return page;
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId)
    {
        repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId)
    {
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
    }

    @Override
    public InputStream getResourceImgStream(String deployId)
    {
        List<String> deploymentResourceNames =
                repositoryService.getDeploymentResourceNames(deployId);
        String imgName = "";
        String[] imgSuffix = {".png", ".jpg", ".jpeg"};
        for (String sourceName : deploymentResourceNames)
        {
            if (Arrays.asList(imgSuffix).contains(sourceName.substring(sourceName.lastIndexOf("."))))
            {
                imgName = sourceName;
                break;
            }
        }
        return repositoryService.getResourceAsStream(deployId, imgName);
    }

    @CheckObjectValue(keyValue = {@KeyValue(type = ProcDfExtend.class, name = {"id", "type"})})
    @Override
    public void editExtendUpdate(ProcDfExtend procDfExtend)
    {
        if (StringUtils.isNull(procDfExtendMapper.selectById(procDfExtend.getId())))
        {
            procDfExtendMapper.insert(procDfExtend);
            return;
        }
        procDfExtendMapper.updateById(procDfExtend);
    }

    @Override
    public LinkedList<Nodes> getProcDefNodesByProcDefId(@Require String procDefId)
    {
        Collection<FlowElement> flowElements = getFlowElements(procDefId);
        LinkedList<Nodes> elementList = new LinkedList<>();
        if (flowElements.isEmpty())
        {
            throw new ServiceException("该流程没有任何节点");
        }
        flowElements.forEach(flowElement -> {
            Nodes.NodeType nodeType = Nodes.NodeType.SEQ_FLOW;
            if (flowElement instanceof ParallelGateway)
            {
                // 并行网关
                nodeType = Nodes.NodeType.ParallelGateway;
            }
            else if (flowElement instanceof ExclusiveGateway)
            {
                // 排他网关
                nodeType = Nodes.NodeType.ExclusiveGateway;
            }
            else if (flowElement instanceof EndEvent)
            {
                // 结束节点
                nodeType = Nodes.NodeType.END_EVENT;
            }else if (flowElement instanceof UserTask){
                nodeType = Nodes.NodeType.USER_TASK;
            }
            else if (flowElement instanceof StartEvent){
                nodeType = Nodes.NodeType.START_EVENT;
            }
            elementList.add(new Nodes(flowElement.getId(), flowElement.getName(), flowElement, nodeType));
        });
        //return sortFlowElements(flowElements);
        return elementList;
    }

    private LinkedList<Nodes> sortFlowElements(Collection<FlowElement> flowElements)
    {
        LinkedList<Nodes> elementList = new LinkedList<>();

        // 转成map
        Map<String, FlowElement> flowElementMap = flowElements.stream().collect(Collectors.toMap(FlowElement::getId, flowElement -> flowElement));

        StartEvent startEvent = null;
        for (FlowElement flowElement : flowElements)
        {
            // 任务节点
            if (flowElement instanceof StartEvent)
            {
                startEvent = (StartEvent) flowElement;
                break;
            }
        }
        filterNode(startEvent, flowElements, flowElementMap, elementList);
        return elementList;
    }


    private void filterSeqNode(List<SequenceFlow> sequenceFlows, Collection<FlowElement> flowElements, Map<String, FlowElement> flowElementMap, LinkedList<Nodes> elementList)
    {

        if (StringUtils.isNull(sequenceFlows) || sequenceFlows.isEmpty())
        {
            return;
        }


        // 存储线节点
        Nodes node = new Nodes(Nodes.NodeType.SEQ_FLOW);

        // 将节点排好顺序
        sequenceFlows.forEach(node::putElement);
        elementList.add(node);

        // 找线之后的节点
        sequenceFlows.forEach(e -> {
            for (FlowElement flowElement : flowElements)
            {
                if (flowElement.getId().equals(e.getTargetRef()))
                {
                    filterNode((FlowNode) flowElementMap.get(flowElement.getId()), flowElements, flowElementMap, elementList);
                }
            }
        });


    }

    private void filterNode(FlowNode flowNode, Collection<FlowElement> flowElements, Map<String, FlowElement> flowElementMap, LinkedList<Nodes> elementList)
    {
        if (StringUtils.isNull(flowNode))
        {
            return;
        }
        Nodes.NodeType nodeType = Nodes.NodeType.START_EVENT;
        if (flowNode instanceof ParallelGateway)
        {
            nodeType = Nodes.NodeType.ParallelGateway;
        }
        else if (flowNode instanceof ExclusiveGateway)
        {
            nodeType = Nodes.NodeType.ExclusiveGateway;
        }
        else if (flowNode instanceof EndEvent)
        {
            nodeType = Nodes.NodeType.END_EVENT;
        }
        elementList.add(new Nodes(flowNode.getId(), flowNode, nodeType));
        filterSeqNode(flowNode.getOutgoingFlows(), flowElements, flowElementMap, elementList);
    }


    @Override
    public UserTask getProcDefFirstUserTask(@Require String procDefId)
    {
        Collection<FlowElement> flowElements = getFlowElements(procDefId);
        StartEvent startEvent = null;
        for (FlowElement flowElement : flowElements)
        {
            if (flowElement instanceof StartEvent)
            {
                startEvent = (StartEvent) flowElement;
                break;
            }
        }
        // 获得中间连线 startEvent  --> 第一个任务节点。
        SequenceFlow sequenceFlow = startEvent.getOutgoingFlows().get(0);

        UserTask userTask = null;
        for (FlowElement flowElement : flowElements)
        {
            if (flowElement instanceof UserTask && flowElement.getId().equals(sequenceFlow.getTargetRef()))
            {
                userTask = (UserTask) flowElement;
                break;
            }
        }
        return userTask;
    }

    private Collection<FlowElement> getFlowElements(String procDefId)
    {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        if (StringUtils.isNull(bpmnModel))
        {
            throw new ServiceException("流程定义Id有误");
        }

        // 取出所有节点 包含 连线 任务节点排它网关
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        return flowElements;
    }
}
