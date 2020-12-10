package com.yj.oa.project.controller;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.framework.web.po.BasePo;
import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.meet.IMeetService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/meet")
public class MeetController extends BaseController{
    private final static String prefix = "system/meet";

    @Autowired
    IMeetService meetService;

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
    @RequiresPermissions("meet:list")
    public String toList(Model model)
    {
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("Rooms", meetingRooms);
        model.addAttribute("Users", users);
        return prefix + "/meet";
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
    public TableDataInfo tableList(Meet meet)
    {
        startPage();
        List<Meet> meets = meetService.selectByMeetList(meet);

        for (Meet meet1 : meets)
        {
            User user = iUserService.selectByPrimaryKey(meet1.getCreateBy());
            meet1.setCreateBy(user.getName());
        }

        return getDataTable(meets);

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
    @RequiresPermissions("meet:add")
    public String toAdd(Model model)
    {
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        model.addAttribute("Rooms", meetingRooms);
        return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params: meet：会议室对象；userIds:开会用户id
     * @return:
     * @date: 2018/9/26 21:16
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("meet:add")
    @ResponseBody
    public AjaxResult addSave(Meet meet, String[] userIds) throws Exception
    {
        if (StringUtils.isEmpty(userIds))
        {
            return error("请选择开会员工！");
        }

        meet.setCreateBy(getUserId());
        meet.setCreateTime(new Date());

        //把自己带上
        boolean contains = Arrays.asList(userIds).contains(getUserId());
        if (!contains)
        {
            String[] arrNew = new String[userIds.length + 1];
            for (int i=0;i<userIds.length;i++)
            {
                arrNew[i]=userIds[i];
            }
            arrNew[arrNew.length-1]=getUserId();
            return result(meetService.insertSelective(meet, arrNew));
        }

        return result(meetService.insertSelective(meet, userIds));
    }

    /**
     *
     * @描述: 删除会议
     *
     * @params:
     * @return:
     * @date: 2018/9/27 22:02
     */
    @RequestMapping("/del")
    @RequiresPermissions("meet:del")
    @Operlog(modal = "会议管理", descr = "删除会议")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        //1.判断是否是管理员
        if (User.isBoss(getUserId()))
        {
            //判断会议是否否进行中
            for (Integer id : ids)
            {
                Meet meet = meetService.selectByPrimaryKey(id);
                if (meet.getStatus().equals("1"))
                {
                    return error("会议进行中！");
                }
            }
        }

        //2.判断是否是本人 getUserId()
        for (Integer id : ids)
        {
            Meet meet = meetService.selectByPrimaryKey(id);
            if (!meet.getCreateBy().equals(getUserId()))
            {
                return error("非会议负责人，无法操作！");
            }
        }
        return result(meetService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("meet:update")
    public String toEdit(@PathVariable("id") Integer meetId, Model model)
    {
        Meet meet = meetService.selectByPrimaryKey(meetId);
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        model.addAttribute("Meet", meet);
        model.addAttribute("Rooms", meetingRooms);
        return prefix + "/edit";
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
    @RequiresPermissions("meet:update")
    @Operlog(modal = "会议管理", descr = "修改会议")
    @ResponseBody
    public AjaxResult editSave(Meet meet, String[] userIds)
    {
        return result(meetService.updateByPrimaryKeySelective(meet, userIds));
    }

    /**
     *
     * @描述: 通过会议id获取参加会议的员工 做编辑会会议的员工回显
     *
     * @params:
     * @return:
     * @date: 2018/9/27 21:02
     */
    @RequestMapping("/selectById/{meetId}")
    @ResponseBody
    public Meet selectById(@PathVariable("meetId") Integer meetId)
    {
        return meetService.selectByPrimaryKey(meetId);
    }
}
