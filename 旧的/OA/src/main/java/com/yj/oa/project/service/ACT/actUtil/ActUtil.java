package com.yj.oa.project.service.ACT.actUtil;

import com.yj.oa.common.constant.CsEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 永健
 */
public class ActUtil{


    /**
     *
     * @描述: 启动流程时候 设置启动人和代理人 变量
     *
     * @params:
     * @return：
     * @date： 2018/9/24 14:29
     */
    public static Map<String, Object> setStartVariable(String uId,String AgentId)
    {
        Map<String, Object> map = new HashMap<>(2);
        //任务节点代理人
        map.put(CsEnum.activiti.AGENT.getValue(), uId);
        //下一个代理人
        map.put(CsEnum.activiti.INITIATOR.getValue(), uId);
        return map;
    }



    /**
     *
     * @描述: 完成当前节点任务后 设置下一个节点的任务消息 代理人和表单id
     *
     * @params:
     * @return：
     * @date： 2018/9/24 14:28
     */
    public static Map<String, Object> setNextTaskVariable(String AgentId,String formKey)
    {
        Map<String, Object> map = new HashMap<>(2);
        //任务节点代理人
        map.put(CsEnum.activiti.AGENT.getValue(), AgentId);
        //下一个代理人
        map.put(CsEnum.activiti.FORMID.getValue(), formKey);
        return map;
    }

    /**
     *
     * @描述: 通过历史实例的 busseniess_Key中拿到表单id 从中获取
     *
     * @params:
     * @return:
     * @date: 2018/9/23 14:46
     */
    public static String getFormKeyFromHi(String businessKey)
    {
        int i = businessKey.lastIndexOf(":");
        String FormId=businessKey.substring(i+1,businessKey.length());
        return FormId;
    }

}
