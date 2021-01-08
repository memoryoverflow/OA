package cn.yj.activity.entity;

import cn.yj.commons.utils.StringUtils;
import org.activiti.bpmn.model.FlowElement;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-14 23:24
 */
public class Nodes
{
    public enum NodeType
    {
        START_EVENT("流程发起","require('@/assets/start.png')"), END_EVENT("流程结束","require('@/assets/end.png')"), USER_TASK("用户审批节点","require('@/assets/userTask.png')"), SEQ_FLOW("节点走向",""), ParallelGateway("并行网关","require('@/assets/ParallelGateway.png')"), ExclusiveGateway("排它网关","require('@/assets/ExclusiveGateway.png')");
        private String msg;
        private String icon;


        NodeType(String msg, String icon)
        {
            this.msg = msg;
            this.icon = icon;
        }

        public String getMsg()
        {
            return msg;
        }
        public String getIcon()
        {
            return icon;
        }
    }

    private String id;
    private List<FlowElement> flowElement;
    private NodeType nodeType;
    private String vueIcon;
    private String vueType;
    private String name;


    public Nodes(String id, FlowElement flowElement, NodeType nodeType)
    {
        this.id = id;
        this.flowElement = new ArrayList<>();
        this.flowElement.add(flowElement);
        this.nodeType = nodeType;
        this.vueIcon = nodeType.icon;
        this.name = StringUtils.isEmpty(flowElement.getName()) ? nodeType.getMsg() : flowElement.getName();
    }

    public Nodes(NodeType nodeType)
    {
        this.vueIcon = nodeType.icon;
        this.nodeType = nodeType;
    }

    public void putElement(FlowElement flowElement)
    {
        if (this.flowElement == null)
        {
            this.flowElement = new ArrayList<>();
        }
        this.flowElement.add(flowElement);
        this.name = StringUtils.isEmpty(flowElement.getName()) ? nodeType.getMsg() : flowElement.getName();
    }

    public Nodes(String id, String name, FlowElement flowElement, NodeType nodeType)
    {
        this.id = id;
        this.vueIcon = nodeType.icon;
        this.name = StringUtils.isEmpty(flowElement.getName()) ? nodeType.getMsg() : flowElement.getName();
        this.flowElement = new ArrayList<>();
        this.flowElement.add(flowElement);
        this.nodeType = nodeType;
        this.name = StringUtils.isEmpty(flowElement.getName()) ? nodeType.getMsg() : flowElement.getName();
    }

    public String getId()
    {
        return id;
    }

    public Nodes setId(String id)
    {
        this.id = id;
        return this;
    }

    public List<FlowElement> getFlowElement()
    {
        return flowElement;
    }

    public Nodes setFlowElement(List<FlowElement> flowElement)
    {
        this.flowElement = flowElement;
        return this;
    }

    public NodeType getNodeType()
    {
        return nodeType;
    }

    public Nodes setNodeType(NodeType nodeType)
    {
        this.nodeType = nodeType;
        return this;
    }

    public String getVueIcon()
    {
        return vueIcon;
    }

    public Nodes setVueIcon(String vueIcon)
    {
        this.vueIcon = vueIcon;
        return this;
    }

    public String getVueType()
    {
        return vueType;
    }

    public Nodes setVueType(String vueType)
    {
        this.vueType = vueType;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Nodes setName(String name)
    {
        this.name = name;
        return this;
    }
}
