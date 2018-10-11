package com.yj.oa.project.service.leavForm;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.ActHiProcinst;
import com.yj.oa.project.po.ApplyRoomForm;
import com.yj.oa.project.po.LeaveForm;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.hiActInst.IActHiActInstService;
import com.yj.oa.project.service.ACT.hiprocInst.IActHiProcinstService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 永健
 */
@Service
@Transactional
public class LeavFormServiceImpl implements ILeavFormService{
    //请假工作流的进程ID
    private final static String ProcessInstanceByKey = "leaveProce_ID";

    @Autowired
    LeaveFormMapper leaveFormMapper;
    /**
     * 开启任务相关的Service对象
     */
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    IdentityService identityService;


    @Autowired
    ActHiActinstMapper actHiActinstMapper;

    @Autowired
    ActHiTaskInstMapper actHiTaskInstMapper;


    @Autowired
    ApplyRoomFormMapper applyRoomFormMapper;

    @Autowired
    IMeetingRoomService iMeetingRoomService;

    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;


    /**
     *
     * @描述: 批量删除，用户删除个人的请假消息，不删除请假表单记录，
     *   只删除请假进程记录，管理员此处需要留一份
     *
     * @params:
     * @return：
     * @date： 2018/9/24 13:43
     */
    @Override
    public int deleteByPrimaryKeys(String[] ids) throws Exception
    {

        //判断当前任务是否在执行中
        for (String id:ids)
        {
            ActHiProcinst actHiProcinst = actHiProcinstMapper.selectByPrimaryKey(id);
            if (actHiProcinst.getEndTime()==null)
            {
                throw new Exception("任务待审中！");
            }

        }



        //1.删除活动历史表 act_hi_actinst 数据
        actHiActinstMapper.deleteByProcInstId(ids);


        //2.删除历史任务表 act_hi_taskinst 数据
        actHiTaskInstMapper.deleteByprocInstIds(ids);

        //3.删除历史进程表 act_hi_proceInst 数据
        int i =  actHiProcinstMapper.deleteByPrimaryKeys(ids);

        //4.删除表单
        leaveFormMapper.deleteByprocInstIds(ids);

        return i;
    }


    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return：
     * @date： 2018/9/24 14:49
     */
    @Override
    public void insertSelective(LeaveForm record)
    {

    }

    /**
     *
     * @描述: 主键查询
     *
     * @params:
     * @return：
     * @date： 2018/9/24 14:49
     */
    @Override
    public LeaveForm selectByPrimaryKey(Integer id)
    {
        return leaveFormMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 更改
     *
     * @params:
     * @return:
     * @date: 2018/9/24 13:45
     */
    @Override
    public int updateByPrimaryKeySelective(LeaveForm record)
    {
        return leaveFormMapper.updateByPrimaryKeySelective(record);
    }


    /**
     *
     * @描述: 列表
     *
     * @params:
     * @return:
     * @date: 2018/9/24 13:45
     */
    @Override
    public List<LeaveForm> selectLeavFormList(LeaveForm leaveForm)
    {
        return leaveFormMapper.selectLeavFormList(leaveForm);
    }

    /**
     *
     * @描述: 1.填写表单(开启第一个任务) 2.然后获取任务，是否提交申请 3.不提交-->结束流程 ，提交--> 经理审批 4.老板审批  5.结束流程
     *
     *
     * @params:
     * @return：
     * @date： 2018/9/24 14:34
     */
    @Override
    public void fillForm(LeaveForm leaveForm)
    {
        String uId = leaveForm.getProposer_Id();
        String agent =uId;
        String businessKey = "";
        String formId = "";
        String proceId = "";
        String taskId = "";

        //1.设置流程启动人 （通过此id获取发起人的流程实例记录,统计申请记录次数）
        identityService.setAuthenticatedUserId(uId);

        //2.预约表单信息持久化 表单的id 为 任务的formKey编号
        leaveFormMapper.insertSelective(leaveForm);

        //3.设置流程实例的FormKey 和表单的 id关联，之后用来查看 历史记录，资源文件。。。
        formId = leaveForm.getId();
        businessKey = CsEnum.activiti.BUSINESS_KEY_LEAVE.getValue() + "" + formId;

        //4.启动申请流程,设置变量到下一个节点
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey(ProcessInstanceByKey,
                                          businessKey,
                                          ActUtil.setNextTaskVariable(uId,
                                                                      formId));
        leaveForm.setProcInstanId(processInstance.getId());
        leaveFormMapper.updateByPrimaryKeySelective(leaveForm);
    }


    /**
     *
     * @描述: 提交表单申请
     *
     * @params: agent:下一个任务代理人；taskId:任务id; formKey:表单id
     * @return:
     * @date: 2018/9/24 15:43
     */
    public void submit(String proceId)
    {
        //通过proceId获取任务
        Task task = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        String formKey=task.getFormKey();

        String assignee = task.getAssignee();

        //下一个任务的代理人还是自己（提交申请请假表单的代理人）
        Map<String, Object> map = ActUtil.setNextTaskVariable(assignee, formKey);
        map.put(CsEnum.activiti.Leave_FLAG.getValue(), CsEnum.activiti.Leave_FLAG_TRUE.getValue());
        //2.完成填写任务
        taskService.complete(task.getId(), map);

        //3.完成提交申请任务
        Task task2 = taskService.createTaskQuery().processInstanceId(proceId).singleResult();


        //指定下一个 任务代理人 （表单中的那一个）
        LeaveForm Form = leaveFormMapper.selectByPrimaryKey(Integer.valueOf(formKey));
        taskService.complete(task2.getId(),ActUtil.setNextTaskVariable(Form.getAgent_Id(),task2.getFormKey()));


        //修改表单状态
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setId(task.getFormKey());
        //1：代表申请中
        leaveForm.setStatus(Integer.parseInt(CsEnum.leavForm.Leave_status_GOING.getValue()));
        leaveFormMapper.updateByPrimaryKeySelective(leaveForm);
    }


    /**
     *
     * @描述: 放弃表单申请
     *
     * @params: agent:下一个任务代理人；taskId:任务id; formKey:表单id
     * @return:
     * @date: 2018/9/24 15:43
     */
    public void giveUp(String proceId)
    {

        //放弃申请
        Task task = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        Map<String, Object> map = ActUtil.setNextTaskVariable(task.getAssignee(), task.getFormKey());
        map.put(CsEnum.activiti.Leave_FLAG.getValue(),CsEnum.activiti.Leave_FLAG_FALSE.getValue());

        taskService.complete(task.getId(),map);

        //结束任务
        Task task2 = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        taskService.complete(task2.getId());


        //4：代表放弃请假
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setId(task.getFormKey());
        leaveForm.setStatus(Integer.parseInt(CsEnum.leavForm.Leave_FLAG_giveup.getValue()));
        leaveFormMapper.updateByPrimaryKeySelective(leaveForm);
    }

}
