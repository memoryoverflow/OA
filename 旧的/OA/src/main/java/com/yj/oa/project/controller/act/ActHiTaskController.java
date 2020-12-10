package com.yj.oa.project.controller.act;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.applyRoom.IActApplyRoomFormService;
import com.yj.oa.project.service.ACT.hiTaskInst.IActHiTaskinistService;
import com.yj.oa.project.service.ACT.task.IActTaskService;
import com.yj.oa.project.service.leavForm.ILeavFormService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 永健
 * 领导审批任务的历史任务
 */
@Controller
@RequestMapping("/hiTask")
public class ActHiTaskController extends BaseController{

    private String prefix = "system/actHiTask/";

    @Autowired
    IActTaskService iact_taskService;

    @Autowired
    IActHiTaskinistService iActHiTaskinistService;

    @Autowired
    IUserService iUserService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    @Autowired
    ILeavFormService iLeavFormService;

    @Autowired
    IActApplyRoomFormService iActApplyRoomFormService;

    @Autowired
    IActApplyRoomFormService iact_applyRoomFormService;


    /**
     *
     * @描述 跳转到任务列表页面
     *
     * @date 2018/9/22 11:00
     */
    @RequestMapping("/toMyHiTaskList")
    public String toTask()
    {
        return prefix + "actHiTask";
    }


    /**
     *
     * @描述: 从历史任务中通过任务ID 查看申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/24 10:52
     */
    @RequestMapping("/edit/{procInstId}")
    @Operlog(modal = "流程审批",descr = "查看历史表单信息")
    public String editForm(@PathVariable("procInstId") String procInstId, Model model)
    {

        //判断任务审批是请假类型 还是 会议室申请 通过 BusinessKey来判断
        HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().
                processInstanceId(procInstId).singleResult();


        //BusinessKey
        String key = result.getBusinessKey();
        //表单ID
        String formKey = ActUtil.getFormKeyFromHi(key);

        if (key.indexOf("0") == 0)
        {
            //会议室申请
            ApplyRoomForm applyRoomForm = iActApplyRoomFormService.selectByPrimaryKey(formKey);
            User user = iUserService.selectByPrimaryKey(applyRoomForm.getProposer_Id());
            applyRoomForm.setProposer_Id(user.getName());
            model.addAttribute("Form", applyRoomForm);
            return prefix + "editApplyRoomForm";
        }

        //请假申请
        LeaveForm leaveForm = iLeavFormService.selectByPrimaryKey(Integer.valueOf(formKey));
        System.out.println(leaveForm);
        User user = iUserService.selectByPrimaryKey(leaveForm.getProposer_Id());
        leaveForm.setProposer_Id(user.getName());
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("Form", leaveForm);
        model.addAttribute("users", users);
        return prefix + "editLeaveForm";
    }


    /**
     *
     * @描述 我的审批记录（已审批记录）
     *
     * @date 2018/9/21 22:50
     */

    @RequestMapping("/TableMyHiTasklList")
    @ResponseBody
    public TableDataInfo HitaskList(ActHiTaskInst actHiTaskInst)
    {
        //返回当前登录人的任务 getUserId()

        startPage();
        List<ActHiTaskInst> actHiTaskInsts = iActHiTaskinistService.selectActHiTaskInstList(actHiTaskInst);

        if (actHiTaskInst != null)
        {
            for (ActHiTaskInst hiTaskInst : actHiTaskInsts)
            {
                if (hiTaskInst.getAssignee() != null)
                {
                    User user = iUserService.selectByPrimaryKey(hiTaskInst.getAssignee());
                    hiTaskInst.setAssignee(user.getName());
                }
            }
        }
        return getDataTable(actHiTaskInsts);
    }


    /**
     *
     * @描述: 从任务表 act_hi_taskinst 通过实例id 批量删除 历史记录
     *
     * @params:
     * @return:
     * @date: 2018/9/26 11:03
     */
    @RequestMapping("/del")
    @ResponseBody
    @Operlog(modal = "流程审批",descr = "删除历史任务")
    public AjaxResult del(String[] ids)
    {
        int i = 0;
        try
        {
            i = iActHiTaskinistService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return i>0 ? success() : error();
    }

}
