package com.yj.oa.project.controller.act;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.ActHiProcinst;
import com.yj.oa.project.po.ApplyRoomForm;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.applyRoom.IActApplyRoomFormService;
import com.yj.oa.project.service.ACT.hiprocInst.IActHiProcinstService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author 永健
 * 会议室申请表单 controller
 */
@Controller
@RequestMapping("/applyRoomForm")
public class ActApplyRoomController extends BaseController{
    private String prefix = "system/applyRoomForm/";

    @Autowired
    IActApplyRoomFormService iactapplyService;
    @Autowired
    IUserService iUserService;

    @Autowired
    IActApplyRoomFormService iActApplyRoomFormService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    IActApplyRoomFormService iact_applyRoomFormService;

    @Autowired
    IMeetingRoomService iMeetingRoomService;

    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

    @Autowired
    IActHiProcinstService iActHiProcinstService;


    /**
     *
     * @描述:跳转到列表页面
     *
     * @params:
     * @return：
     * @date： 2018/9/23 12:52
     */
    @RequestMapping("/toMyApplyList")
    public String tolist()
    {
        return prefix + "applyRoom";
    }


    /**
     *
     * @描述 表单填写页面
     *
     * @date 2018/9/22 14:43
     */
    @RequestMapping("/toAdd/{roomName}")
    public String toAddapplyRoomForm(@PathVariable("roomName") String roomName, Model model)
    {
        //返回用户列表 选择审批人
        List<User> users = iUserService.selectByUser(new User());

        model.addAttribute("roomName", roomName);
        model.addAttribute("users", users);

        return "system/room/addApplyForm";
    }

    /**
     *
     * @描述: 修改保存会议室申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/23 13:28
     */
    @RequestMapping("/editSave")
    @Operlog(modal = "会议管理",descr = "修改申请表单")
    @ResponseBody
    public AjaxResult editSave(ApplyRoomForm applyRoomForm)
    {
        //判断当前流程审批是否完成 完成：不允许修改
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                applyRoomForm.getProcInstanId()).singleResult();
        Date endTime = historicProcessInstance.getEndTime();
        if (endTime != null)
        {
            return error("该申请已审批！");
        }
        iActApplyRoomFormService.updateByPrimaryKeySelective(applyRoomForm);
        return success();
    }

    /**
     *
     * @描述: 个人历史 会议室表单申请记录
     *
     * @params: actHiProcinst:历史实体
     * @return：TableDataInfo：分页表格实体
     * @date： 2018/9/23 12:36
     */
    @RequestMapping("/TableMyApplRoomMyHiList")
    @ResponseBody
    public TableDataInfo myApplyHi(ActHiProcinst actHiProcinst)
    {
        startPage();
        //getUserId()
        actHiProcinst.setStartActId(getUserId());
        actHiProcinst.setBusinessKey(CsEnum.activiti.BUSINESS_KEY_APPLYROOM.getValue());
        List<ActHiProcinst> actHiProcinsts = iActHiProcinstService.selectActHiProcinstList(actHiProcinst);
        return getDataTable(actHiProcinsts);
    }



    /**
     *
     * @描述: 查看会议申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/23 13:28
     */
    @RequestMapping("/edit/{procInstId}")
    public String edit(@PathVariable("procInstId") String procInstId, Model model)
    {
        //通过实例Id获取任务Id 拿到任务中的表单key 查询出表单内容
        Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
        String formKey = "";
        ApplyRoomForm applyRoomForm = new ApplyRoomForm();

        if (task == null)
        {
            //任务已完成,在历史记录 获取表单Id 查看表单信息；
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                    procInstId).singleResult();
            String businessKey = historicProcessInstance.getBusinessKey();
            formKey = ActUtil.getFormKeyFromHi(businessKey);

        }
        else
        {
            formKey = task.getFormKey();
        }
        applyRoomForm = iActApplyRoomFormService.selectByPrimaryKey(formKey);
        model.addAttribute("RoomForm", applyRoomForm);
        return prefix + "editApplyRoomForm";
    }








    /**
     *
     * @描述 提交预约申请
     *
     * @date 2018/9/22 14:41
     */
    @RequestMapping("/submitApply")
    @Operlog(modal = "会议管理",descr = "提交会议室申请表单")
    @ResponseBody
    public AjaxResult submitApply(ApplyRoomForm applyRoom)
    {

        //判断房间使用状况
        MeetingRoom meetingRoom = iMeetingRoomService.selectByRoomName(applyRoom.getRoomName());
        Integer status = meetingRoom.getStatus();
        if (status == CsEnum.meetRoom.MEET_ROOM_STATUS_FREE.getValue())
        {
            //设置申请人ID  getUserId()
            applyRoom.setProposer_Id(getUserId());
            applyRoom.setCreateTime(new Date());
            iactapplyService.apply(applyRoom);
            return success();
        }
        else if (status == CsEnum.meetRoom.MEET_ROOM_STATUS_APPLYING.getValue())
        {
            return error(" 会议室已预约中！");
        }
        else if (status == CsEnum.meetRoom.MEET_ROOM_STATUS_USING.getValue())
        {
            return error(" 会议室使用中！");
        }
        else
        {
            return error(" 会议室已停用！");
        }
    }






    /**
     *
     * @描述: 从历史 实例进程表act_hi_proceinst 里面 通过进程实例ID删除请假记录
     *
     * @params:
     * @return:
     * @date: 2018/9/26 11:14
     */
    @RequestMapping("/del")
    @Operlog(modal = "会议管理",descr = "删除会议室申请记录")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {
        int i = 0;
        try
        {
            i = iActApplyRoomFormService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return i>0 ?success():error();
    }

}
