package com.yj.oa.project.service.ACT.task;


import com.yj.oa.project.po.ActTask;
import com.yj.oa.project.po.ApplyRoomForm;
import com.yj.oa.project.po.LeaveForm;

import java.util.List;
import java.util.Map;

/**
 * @author 永健
 */
public interface IActTaskService{
    /**
     *
     * @描述 任务列表
     *
     * @date 2018/9/21 22:47
     */
    List<ActTask> selectACTTaskList(ActTask record);

    /**
     *
     * @描述: 请假审批
     *
     * @params:
     * @return:
     * @date: 2018/9/24 17:20
    */

    public void LeaveApproval(LeaveForm leaveForm, String taskId);

    public void RoomApproval(ApplyRoomForm applyRoomForm, String taskId) throws Exception;
    /**
     *
     * @描述:  删除任务
     *
     * @params:
     * @return:
     * @date: 2018/9/26 14:50
    */
    public int deletByProcInstS(String[] ids);


    /**
     *
     * @描述 通过任务Id 获取当前节点的所有信息
     *
     * @date 2018/9/22 11:35
     */
    Map<String,Object> getCurrentView(String taskId);
}
