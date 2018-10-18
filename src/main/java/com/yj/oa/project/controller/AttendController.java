package com.yj.oa.project.controller;

import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.mapper.LeaveFormMapper;
import com.yj.oa.project.po.Attend;
import com.yj.oa.project.po.WorkTime;
import com.yj.oa.project.service.attend.IAttendService;
import com.yj.oa.project.service.attendCount.IAttendCountService;
import com.yj.oa.project.service.workTime.IWorkTimeService;
import com.yj.oa.project.service.workTime.WorkTimeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/attend")
public class AttendController extends BaseController{
    private String prefix = "system/attend/";
    @Autowired
    IAttendService iAttendService;

    @Autowired
    IWorkTimeService iWorkTimeService;

    @Autowired
    IAttendCountService iAttendCountService;




    /**
     *
     * @描述: 跳转
     *
     * @params:
     * @return:
     * @date: 2018/10/2 18:12
     */

    @RequestMapping("/tolist")
    public String toList(Model model)
    {
        //考勤时间工作
        WorkTime workShif = iWorkTimeService.selectUsing();
        model.addAttribute("workShif", workShif);
        return prefix + "attend";
    }

    /**
     * 计算当天的打卡时间差
     */
    @RequestMapping("/CalculationAttend")
    @ResponseBody
    public Map<String, Long> Calculation(Integer attendId)
    {

        WorkTime workShif = iWorkTimeService.selectUsing();
        Attend attend = iAttendService.selectByPrimaryKey(attendId);
        return WorkTimeUtils.getCurrentAttendTime(workShif,attend);
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Attend attend)
    {
        startPage();
        List<Attend> attends = iAttendService.selectAttendList(attend);
        for (Attend a:attends)
        {
            System.out.println(a);
        }
        return getDataTable(attends);
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("attend:del")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            // 删除前需要判断是否是本人发布的公告或这通知
            iAttendService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("attend:update")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Attend attend = iAttendService.selectByPrimaryKey(id);
        model.addAttribute("attend", attend);
        return prefix + "edit";
    }


    /**
     *
     * @描述 修改保存
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("attend:update")
    @Operlog(modal = "考勤记录",descr = "修改信息")
    @ResponseBody
    public AjaxResult edit(Attend attend)
    {
        return result(iAttendService.updateByPrimaryKey(attend));
    }





    /**
     * 打考勤
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("attend:add")
    @ResponseBody
    public AjaxResult addSave(Attend attend)
    {
        attend.setUserId(getUserId());
        attend.setDeptId(String.valueOf(getUser().getDept()));
        int i = 0;
        try
        {
            i = iAttendService.insertSelective(attend);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }

        return result(i);
    }


    /**
     * 判断当天是否已经签到过,返回签到时间
     */
    @RequestMapping("/isAttend")
    @ResponseBody
    public Date isAttend()
    {
        Attend attend = iAttendService.selectSaveDayIsAttend(getUserId());
        WorkTime workShif = iWorkTimeService.selectUsing();
        Date date = new Date();
        long currDate = date.getTime();

        if (attend != null)
        {

            //早打卡 打卡开始时间 < 当前打卡时间 < 下班时间
            if (WorkTimeUtils.attendStartMorTime(date,workShif) <= currDate && currDate < WorkTimeUtils.MorWorkEndTime(date,
                    workShif))
            {
                if (attend.getAttendMorStart() != null)
                {
                    return attend.getAttendMorStart();
                }
            }
            //中午下班打卡
            else if (WorkTimeUtils.leaveMorStartTime(date,workShif) <= currDate && currDate <= WorkTimeUtils.leaveMorEnddate(date,
                    workShif))
            {
                if (attend.getAttendMorLeave() != null)
                {
                    return attend.getAttendMorLeave();
                }
            }
            //下午上班打卡
            else if (WorkTimeUtils.attendAfterNoonStatrTime(date,
                    workShif) <= currDate && currDate < WorkTimeUtils.attendAfterNoonEndTime(date,workShif))
            {
                if (attend.getAttendNoonStart() != null)
                {
                    return attend.getAttendNoonStart();
                }
            }
            else if (WorkTimeUtils.AttendAfterNoonLeaveStartTime(date,
                    workShif) <= currDate && currDate <= WorkTimeUtils.AttendAfterNoonLeaveEndTime(date,workShif))
            {
                if (attend.getAttendNoonLeave() != null)
                {
                    return attend.getAttendNoonLeave();
                }
            }
        }
        return null;
    }


    /**
     * 获取工作时间，返回前台
     */
    @RequestMapping("/getWorkTime")
    @ResponseBody
    public Map<String, Long> getWorkTime()
    {

        WorkTime workShif = iWorkTimeService.selectUsing();
        Date date = new Date();
        Map<String, Long> map = new HashMap<>();

        if (workShif != null)
        {

            //早上上班时间
            map.put("morWorkStartTime", WorkTimeUtils.MorWorkStartTime(date,workShif));
            // 早上下班时间
            map.put("morWorkEndTime", WorkTimeUtils.MorWorkEndTime(date,workShif));

            //早上开始打卡时间
            map.put("attendStartMorTime", WorkTimeUtils.attendStartMorTime(date,workShif));
            //早上结束打卡时间
            map.put("attendEndMorTime", WorkTimeUtils.attendEndMorTime(date,workShif));

            //早上下班打卡开始时间
            map.put("leavMorStartTime", WorkTimeUtils.leaveMorStartTime(date,workShif));
            //早上下班打卡结束时间
            map.put("leavMorEndtTime", WorkTimeUtils.leaveMorEnddate(date,workShif));


            //下午上班打卡开始时间
            map.put("attendAfterNoonStatrTime", WorkTimeUtils.attendAfterNoonStatrTime(date,workShif));
            //下午上班打卡结束时间
            map.put("attendAfterNoonEndTime", WorkTimeUtils.attendAfterNoonEndTime(date,workShif));

            //下午上班开始时间
            map.put("afterNoonStarWorkTime", WorkTimeUtils.AfterNoonStarWorkTime(date,workShif));
            //下午上班结束时间
            map.put("afterNonEndWorkTime", WorkTimeUtils.AfterNonEndWorkTime(date,workShif));


            //下午下班开始打卡时间
            map.put("attendAfterNoonStartTime", WorkTimeUtils.AttendAfterNoonLeaveStartTime(date,workShif));
            //下午下班结束打卡时间
            map.put("attendAfterNoonEndTime", WorkTimeUtils.AttendAfterNoonLeaveEndTime(date,workShif));

        }
        return map;
    }



}
