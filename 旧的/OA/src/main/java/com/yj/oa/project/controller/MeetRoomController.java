package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Dept;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/room")
public class MeetRoomController extends BaseController{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String prefix = "system/room/";


    @Autowired
    private IMeetingRoomService iMeetingRoomService;

    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    public String tolist()
    {
        return prefix + "room";
    }


    /**
     *
     * @描述 ajax请求
     *
     * @date 2018/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<MeetingRoom> list(MeetingRoom meetingRoom)
    {
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(meetingRoom);
        return meetingRooms;
    }

    /**
     *
     * @描述 列表页
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(MeetingRoom meetingRoom)
    {
        //开启分页
        startPage();
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(meetingRoom);
        return getDataTable(meetingRooms);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("meetRoom:list")
    public String toAdd()
    {
        return prefix + "add";
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("meetRoom:del")
    @Operlog(modal = "会议室管理",descr = "删除会议室")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iMeetingRoomService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 执行保存操作
     *
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("meetRoom:add")
    @Operlog(modal = "会议室管理",descr = "添加会议室")
    @ResponseBody
    public AjaxResult addMeetingRoom(MeetingRoom meetingRoom)
    {
        meetingRoom.setCreateTime(new Date());
        return result(iMeetingRoomService.insertSelective(meetingRoom));
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("meetRoom:update")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        MeetingRoom meetingRoom = iMeetingRoomService.selectByPrimaryKey(id);
        model.addAttribute("room", meetingRoom);
        return prefix + "edit";

    }

    /**
     *
     * @描述 修改保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("meetRoom:update")
    @Operlog(modal = "会议室管理",descr = "修改会议室")
    @ResponseBody
    public AjaxResult save(MeetingRoom meetingRoom)
    {
        return result(iMeetingRoomService.updateByPrimaryKeySelective(meetingRoom));
    }


    /**
     * 校验部门名称
     */
    @PostMapping("/checkRoomNameUnique")
    @ResponseBody
    public String checkMeetingRoomNameUnique(MeetingRoom meetingRoom)
    {
        String uniqueFlag = "0";
        if (meetingRoom != null)
        {
            uniqueFlag = iMeetingRoomService.checkRoomNameUnique(meetingRoom);
        }
        return uniqueFlag;
    }
}
