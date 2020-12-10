package com.yj.oa.project.controller;

import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.WorkTime;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.workTime.IWorkTimeService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 */
@Controller
@RequestMapping("/worktime")
public class WorkTimeController extends BaseController{
    private final static String prefix = "system/worktime";

    @Autowired
    IWorkTimeService iWorkTimeService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IMeetingRoomService iMeetingRoomService;


    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("workTime:list")
    public String toList()
    {
        return prefix + "/worktime";
    }


    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(WorkTime workTime)
    {
        startPage();
        List<WorkTime> workTimes = iWorkTimeService.selectWorkTimeList(workTime);
        return getDataTable(workTimes);
    }


    /**
     *
     * @描述: 添加页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("workTime:add")
    public String toAdd()
    {
        return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:16
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("workTime:add")
    @Operlog(modal = "工作时间管理", descr = "添加工作时间")
    @ResponseBody
    public AjaxResult addSave(WorkTime workTime) throws Exception
    {

        workTime.setCreateTime(new Date());
        int i = 0;
        try
        {
            i = iWorkTimeService.insertSelective(workTime);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }

    /**
     *
     * @描述: 删除
     *
     * @params:
     * @return:
     * @date: 2018/9/27 22:02
     */
    @RequestMapping("/del")
    @RequiresPermissions("workTime:del")
    @Operlog(modal = "工作时间管理", descr = "删除工作时间")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        int i = 0;
        try
        {
            i = iWorkTimeService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     *
     * @描述: 编辑页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("workTime:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        WorkTime workTime = iWorkTimeService.selectByPrimaryKey(id);
        model.addAttribute("workTime", workTime);
        return prefix + "/edit";
    }


    /**
     *  启用或这停用当前工作时间表
     * @param workTime
     * @return
     */
    @RequestMapping("/updateStatus")
    @Operlog(modal = "工作时间管理",descr = "启用/停用工作时间")
    @ResponseBody
    public AjaxResult startOrEndWorkTime(WorkTime workTime)
    {
        int i= 0;
        try
        {
            i = iWorkTimeService.startOrEndWorkTime(workTime);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     *
     * @描述: 修改保存
     *
     * @params:
     * @return:
     * @date: 2018/9/27 21:01
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("workTime:update")
    @Operlog(modal = "工作时间管理", descr = "修改工作时间")
    @ResponseBody
    public AjaxResult editSave(WorkTime workTime)
    {
        int i = 0;
        try
        {
            i = iWorkTimeService.updateByPrimaryKeySelective(workTime);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }
}
