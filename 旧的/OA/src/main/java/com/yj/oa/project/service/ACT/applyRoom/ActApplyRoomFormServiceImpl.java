package com.yj.oa.project.service.ACT.applyRoom;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.project.mapper.ActHiProcinstMapper;
import com.yj.oa.project.mapper.ActHiTaskInstMapper;
import com.yj.oa.project.mapper.ApplyRoomFormMapper;
import com.yj.oa.project.mapper.MeetingRoomMapper;
import com.yj.oa.project.po.ActHiProcinst;
import com.yj.oa.project.po.ApplyRoomForm;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.hiActInst.IActHiActInstService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.aop.framework.AopContext;
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
public class ActApplyRoomFormServiceImpl implements IActApplyRoomFormService{

    //申请工作流的进程ID
    private final static String ProcessInstanceByKey = "apply_ID";


    /**
     * 开启任务相关的Service对象
     */
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;


    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;


    @Autowired
    IActHiActInstService iActHiActInstService;

    @Autowired
    ActHiTaskInstMapper actHiTaskInstMapper;


    @Autowired
    ApplyRoomFormMapper applyRoomFormMapper;


    @Autowired
    IdentityService identityService;


    @Autowired
    MeetingRoomMapper iMeetingRoomService;


    /**
     * @param applyRoom:预约会议室信息 启动申请流程
     */
    @Override
    public void apply(ApplyRoomForm applyRoom)
    {

        //设置任务发起人 （通过此id获取发起人的流程实例记录,统计申请记录次数）
        String uId = applyRoom.getProposer_Id();
        String proceId="";
        String taskId="";
        identityService.setAuthenticatedUserId(uId);


        //提交申请，完成任务。并且指定下一个任务代理人 传递变量参数到下一个节点
        Map<String, Object> map = new HashMap<>(2);
        map.put(CsEnum.activiti.AGENT.getValue(), uId);
        map.put(CsEnum.activiti.INITIATOR.getValue(), uId);

        // 预约表单信息持久化 表单的id 为 任务的formKey编号
        applyRoomFormMapper.insertSelective(applyRoom);

        //设置流程实例的FormKey 和表单的 id关联，之后用来查看 历史记录，资源文件。。。
        String businessKey = CsEnum.activiti.BUSINESS_KEY_APPLYROOM.getValue() + "" + applyRoom.getId();
        //开启申请流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessInstanceByKey,
                                                                                   businessKey, map);
        //Service中如果直接调用调用并非调用的是代理类中的方法，不会被切进去，事务就不会生效。
        // 必须要调用代理类才会被切进去 调用带来类需要aop配置：
        // <aop:aspectj-autoproxy proxy-target-class="true" expose-proxy="true"/>
//        ((ActApplyRoomFormServiceImpl) AopContext.currentProxy()).apply(applyRoom, processInstance.getId());



        //通过实例Id获取任务Id
        proceId=processInstance.getId();
        Task task = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        taskId = task.getId();

        //提交任务
        taskService.complete(taskId, ActUtil.setNextTaskVariable(applyRoom.getAgent_Id(),applyRoom.getId()));


        //修改房间状态为 预约中
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setStatus(CsEnum.meetRoom.MEET_ROOM_STATUS_APPLYING.getValue());
        meetingRoom.setMeetRoomName(applyRoom.getRoomName());
        iMeetingRoomService.updateByRoomName(meetingRoom);

        //添加实例Id
        applyRoom.setProcInstanId(proceId);
        applyRoomFormMapper.updateByPrimaryKeySelective(applyRoom);
    }






    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/21 15:48
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
                throw new Exception("无法删除，待审批任务！");
            }

        }
        //1.删除活动历史表 act_hi_actinst 数据
        iActHiActInstService.deleteByProcInstId(ids);

        //2.删除历史任务表 act_hi_taskinst 数据
        actHiTaskInstMapper.deleteByprocInstIds(ids);

        //3.删除历史进程表 act_hi_proceInst 数据
        int i =  actHiProcinstMapper.deleteByPrimaryKeys(ids);

        //4.删除表单
        applyRoomFormMapper.deleteByprocInstIds(ids);

        return i;
    }

    /**
     *
     * @描述: 根据主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/26 15:29
    */
    @Override
    public ApplyRoomForm selectByPrimaryKey(String id)
    {
        return applyRoomFormMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述:  更改
     *
     * @params:
     * @return:
     * @date: 2018/9/26 15:29
    */
    @Override
    public int updateByPrimaryKeySelective(ApplyRoomForm record)
    {
        return applyRoomFormMapper.updateByPrimaryKeySelective(record);
    }


}
