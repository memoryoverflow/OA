package com.yj.oa.project.controller;

import com.yj.oa.common.constant.Constants;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.service.PermissionService;
import com.yj.oa.project.mapper.WorkTimeMapper;
import com.yj.oa.project.po.*;
import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.service.ACT.task.IActTaskService;
import com.yj.oa.project.service.meet.IMeetService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.menu.IPermissionService;
import com.yj.oa.project.service.note.INoteService;
import com.yj.oa.project.service.notice.INoticeService;
import com.yj.oa.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/oa")
public class IndexController extends BaseController{

    private final static String prefix = "main/";

    @Autowired
    private IMeetService iMeetService;

    @Autowired
    INoticeService iNoticeService;

    @Autowired
    IMeetingRoomService iMeetingRoomService;

    @Autowired
    IActTaskService iActTaskService;

    @Autowired
    IUserService iUserService;

    @Autowired
    INoteService iNoteService;

    @Autowired
    WorkTimeMapper workShifMapper;

    @Autowired
    IPermissionService iPermissionService;


    /**
     * 未授权页面
     */
    @RequestMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }


    /**
     * 首页跳转 登录成功进入此页面，加载左侧菜单
     */
    @RequestMapping("/index")
    public String index(Model model, HttpSession session)
    {
        List<MenuTree> menuTreeList = (List<MenuTree>) session.getAttribute(Constants.MENU_SESSION);
        if (StringUtils.isEmpty(menuTreeList))
        {
            menuTreeList = iPermissionService.selectMenuTree(getUserId());
            session.setAttribute(Constants.MENU_SESSION,menuTreeList);
        }
        model.addAttribute("menus", menuTreeList);

        model.addAttribute("user", getUser());
        return "index";
    }


    /**
     *
     * @描述: 首页 公告 会议
     *
     * @params:
     * @return:
     * @date: 2018/9/28 10:53
     */
    @RequestMapping("/main")
    public String toMain(Model model)
    {
        // 会议列表    getUserId()
        List<Meet> meets = iMeetService.selectMyMeetList(getUserId());

        //公告列表
        List<Notice> notices = iNoticeService.selectNoticeList(new Notice());

        //便签列表
        Note note = new Note();
        note.setCreateBy(getUserId());
        List<Note> notes = iNoteService.selectNoteList(note);

        //待办事项列表
        ActTask actTask = new ActTask();
        actTask.setAssignee(getUserId());
        List<ActTask> actTasks = iActTaskService.selectACTTaskList(actTask);
        for (ActTask task : actTasks)
        {
            User user = iUserService.selectByPrimaryKey(task.getAssignee());
            task.setAssignee(user.getName());
        }


        //考勤时间工作
        WorkTime workShif = workShifMapper.selectUsing();

        model.addAttribute("notice", notices);
        model.addAttribute("meets", meets);
        model.addAttribute("notes", notes);
        model.addAttribute("Task", actTasks);
        model.addAttribute("workShif", workShif);


        return "main";
    }


    /**
     *
     * @描述 查看公告详情
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/editNotice/{id}")
    public String editNotice(@PathVariable("id") Integer id, Model model)
    {
        Notice notice = iNoticeService.selectByPrimaryKey(id);
        model.addAttribute("notice", notice);
        return prefix + "editNotice";
    }


    /**
     *
     * @描述: 编辑会议页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/editMeet/{id}")
    public String editMeet(@PathVariable("id") Integer meetId, Model model)
    {
        Meet meet = iMeetService.selectByPrimaryKey(meetId);
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        model.addAttribute("Meet", meet);
        model.addAttribute("Rooms", meetingRooms);
        return prefix + "editMeet";
    }


}
