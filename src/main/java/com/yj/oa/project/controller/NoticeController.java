package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Notice;
import com.yj.oa.project.service.notice.INoticeService;
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
@RequestMapping("/notice")
public class NoticeController extends BaseController{
    private String prefix = "system/notice/";
    @Autowired
    INoticeService iNoticeService;

    /**
     *
     * @描述 ajax请求
     *
     * @date 2018/9/16 17:19
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Notice> list(Notice notice)
    {
        return iNoticeService.selectNoticeList(notice);
    }


    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("notice:list")
    public String tolist()
    {
        return prefix + "notice";
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Notice pnotice)
    {
        startPage();
        List<Notice> notices = iNoticeService.selectNoticeList(pnotice);
        return getDataTable(notices);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("notice:add")
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
    @RequiresPermissions("notice:del")
    @Operlog(modal = "公告管理",descr = "删除公告")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            // 删除前需要判断是否是本人发布的公告或这通知
            iNoticeService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 添加保存
     *
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("notice:update")
    @Operlog(modal = "公告管理",descr = "公告发布")
    @ResponseBody
    public AjaxResult addRole(Notice notice)
    {
        notice.setCreateTime(new Date());
//        登录人
        notice.setCreateBy(String.valueOf(getUserId()));
        int insert = 0;
        try
        {
            insert = iNoticeService.insertSelective(notice);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(insert);
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("notice:update")
    @Operlog(modal = "公告管理",descr = "查看公告")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Notice notice = iNoticeService.selectByPrimaryKey(id);
        model.addAttribute("notice", notice);
        return prefix + "edit";
    }


    /**
     *
     * @描述 修改保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("notice:update")
    @Operlog(modal = "公告管理",descr = "修改公告")
    @ResponseBody
    public AjaxResult save(Notice notice)
    {
        int i = 0;
        try
        {
            System.out.println(notice);
            notice.setCreateTime(new Date());
//            登录人
            notice.setCreateBy(getUser().getName());
            i = iNoticeService.updateByPrimaryKeySelective(notice);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }
}
