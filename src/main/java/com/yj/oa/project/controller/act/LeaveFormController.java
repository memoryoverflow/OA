package com.yj.oa.project.controller.act;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.hiprocInst.IActHiProcinstService;
import com.yj.oa.project.service.ACT.task.IActTaskService;
import com.yj.oa.project.service.leavForm.ILeavFormService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
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
 * 请假
 */

@Controller
@RequestMapping("/leave")
public class LeaveFormController extends BaseController{
    private final static String prefix = "system/leave/";

    @Autowired
    ILeavFormService iLeavFormService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IActHiProcinstService iActHiProcinstService;
    @Autowired
    IActTaskService iActTaskService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

    /**
     *
     * @描述 ajax请求获得 数据 treeData,添加角色时候需用
     *
     * @date 2018/9/16 17:19
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<LeaveForm> list()
    {
        return iLeavFormService.selectLeavFormList(new LeaveForm());
    }


    /**
     *
     * @描述: 编辑页面
     *
     * @params:
     * @return:
     * @date: 2018/9/24 19:08
     */
    @RequestMapping("/edit/{procInstId}")
    public String edit(@PathVariable("procInstId") String procInstId, Model model)
    {
        //通过实例Id获取任务Id 拿到任务中的表单key 查询出表单内容
        Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
        String formKey = "";
        LeaveForm leaveForm = new LeaveForm();
        if (task == null)
        {
            //任务已完成,在历史记录 获取表单Id 查看表单信息；
            HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                    procInstId).singleResult();
            String businessKey = result.getBusinessKey();
            formKey = ActUtil.getFormKeyFromHi(businessKey);

        }
        else
        {
            formKey = task.getFormKey();
        }
        leaveForm = iLeavFormService.selectByPrimaryKey(Integer.valueOf(formKey));
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);
        model.addAttribute("Form", leaveForm);
        System.out.println(leaveForm);
        return prefix + "editLeaveForm";
    }


    /**
     *
     * @描述: 修改保存申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/23 13:28
     */
    @RequestMapping("/editSave")
    @Operlog(modal = "请假管理", descr = "修改请假表单")
    @ResponseBody
    public AjaxResult editSave(LeaveForm leaveForm)
    {
        //判断当前流程审批是否完成 完成：不允许修改
        HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                leaveForm.getProcInstanId()).singleResult();
        if (result.getEndTime() == null)
        {
            iLeavFormService.updateByPrimaryKeySelective(leaveForm);
            return success();
        }
        return error("该申请已审批！");
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
    @Operlog(modal = "请假管理", descr = "删除请假记录")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {
        int i = 0;
        try
        {
            i = iLeavFormService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     *
     * @描述: 检测当前任务是不是当前员工的请假任务
     *   是就显示提交申请
     *
     * @params:
     * @return:
     * @date: 2018/9/24 19:59
     */
    @RequestMapping("/checkAgent")
    @ResponseBody
    public String checkAgent(String proceId)
    {

        //任务为空说明当前人的请假任务完成
        Task task = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        if (task != null)
        {
            String assignee = task.getAssignee();
            //当前登录人 getUserId()
            if (assignee.equals(getUserId())&&!User.isBoss(getUserId()))
            {
                return "true";
            }
        }
        return "false";
    }


    /**
     *
     * @描述 请假记录列表页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toMyLeaveList")
    public String toMyLeaveList()
    {
        return prefix + "leave";
    }


    /**
     *
     * @描述: 个人历史 请假表单申请记录
     *
     * @params: actHiProcinst:历史实体
     * @return：TableDataInfo：分页表格实体
     * @date： 2018/9/23 12:36
     */
    @RequestMapping("/TableMyLeaveHiList")
    @ResponseBody
    public TableDataInfo myLeavRecordHi(ActHiProcinst actHiProcinst)
    {
        startPage();

        //如果是boss就显示所有
        if (!User.isBoss(getUserId()))
        {
            actHiProcinst.setStartActId(getUserId());
        }

        actHiProcinst.setBusinessKey(CsEnum.activiti.BUSINESS_KEY_LEAVE.getValue());
        List<ActHiProcinst> actHiProcinsts = iActHiProcinstService.selectActHiProcinstList(actHiProcinst);
        return getDataTable(actHiProcinsts);
    }


    /**
     *
     * @描述 请假表单填写页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model)
    {
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);
        return prefix + "add";
    }


    /**
     *
     * @描述 填写表单后保存
     * @Auto
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @Operlog(modal = "请假管理", descr = "填写请假表单")
    @ResponseBody
    public AjaxResult addSave(LeaveForm leaveForm)
    {
        leaveForm.setCreateTime(new Date());
        //getUserId()
        leaveForm.setProposer_Id(getUserId());


        if (User.isBoss(getUserId()))
        {
            return error("你是 Boos !");
        }


        try
        {
            iLeavFormService.fillForm(leaveForm);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述: 提交申请
     *
     * @params: 实例Id
     * @return:
     * @date: 2018/9/24 15:50
     */
    @RequestMapping("/submit")
    @Operlog(modal = "请假管理", descr = "提交请假申请")
    @ResponseBody
    public AjaxResult submit(String procInstId)
    {
        iLeavFormService.submit(procInstId);
        return success();
    }


    /**
     *
     * @描述: 放弃申请，结束流程
     *
     * @params: 实例Id
     * @return:
     * @date: 2018/9/24 15:50
     */
    @RequestMapping("/giveUp")
    @Operlog(modal = "请假管理", descr = "放弃请假申请")
    @ResponseBody
    public AjaxResult giveUp(String procInstId)
    {
        iLeavFormService.giveUp(procInstId);
        return success();
    }
}
