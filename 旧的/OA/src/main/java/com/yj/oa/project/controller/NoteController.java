package com.yj.oa.project.controller;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.Note;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.meet.IMeetService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.note.INoteService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Not;
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
@RequestMapping("/note")
public class NoteController extends BaseController{
    private final static String prefix = "system/note";

    @Autowired
    INoteService iNoteService;

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
    @RequiresPermissions("note:list")
    public String toList(Model model)
    {
        return prefix + "/note";
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
    public TableDataInfo tableList(Note note)
    {
        startPage();
        note.setCreateBy(getUserId());
        List<Note> notes = iNoteService.selectNoteList(note);
        return getDataTable(notes);

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
    public String toAdd(Model model)
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
    @RequiresPermissions("note:add")
    @Operlog(modal = "便签管理",descr = "提艾那几")
    @ResponseBody
    public AjaxResult addSave(Note note) throws Exception
    {
        note.setCreateBy(getUserId());
        note.setCreateTime(new Date());
        return result(iNoteService.insertSelective(note));
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
    @RequiresPermissions("note:del")
    @Operlog(modal = "便签管理",descr = "删除便签")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(iNoteService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("note:update")
    @Operlog(modal = "便签管理",descr = "查看便签")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Note note = iNoteService.selectByPrimaryKey(id);
        model.addAttribute("note", note);
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
    @RequiresPermissions("note:update")
    @Operlog(modal = "便签管理",descr = "修改便签")
    @ResponseBody
    public AjaxResult editSave(Note note)
    {
        return result(iNoteService.updateByPrimaryKeySelective(note));
    }
}
