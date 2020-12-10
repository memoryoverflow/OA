package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Position;
import com.yj.oa.project.service.position.IPositionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/position")
public class PositionController extends BaseController{
    private String prefix = "system/post/";

    @Autowired
    IPositionService iPositionService;

    /**
     *
     * @描述 ajax请求
     *
     * @date 2018/9/16 17:19
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Position> list(Position position)
    {
        return iPositionService.selectPositionList(position);
    }


    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("position:list")
    public String tolist()
    {
        return prefix + "post";
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Position pposition)
    {
        startPage();
        List<Position> positions = iPositionService.selectPositionList(pposition);
        return getDataTable(positions);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("position:add")
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
    @RequiresPermissions("position:del")
    @Operlog(modal = "岗位管理",descr = "删除岗位")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iPositionService.deleteByPrimarysKey(ids);
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
    @RequiresPermissions("position:update")
    @Operlog(modal = "岗位管理",descr = "添加岗位")
    @ResponseBody
    public AjaxResult addRole(Position pposition)
    {
        pposition.setCreateTime(new Date());
        int insert = 0;
        try
        {
            insert = iPositionService.insertSelective(pposition);
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
    @RequiresPermissions("position:update")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Position position = iPositionService.selectByPrimaryKey(id);
        model.addAttribute("post", position);
        return prefix + "edit";
    }


    /**
     *
     * @描述 修改保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("position:update")
    @Operlog(modal = "岗位管理",descr = "修改岗位信息")
    @ResponseBody
    public AjaxResult save(Position position)
    {
        int i = 0;
        try
        {
            i = iPositionService.updateByPrimaryKeySelective(position);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     * 校验名称唯一
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(Position position)
    {
        String uniqueFlag = "0";
        if (position != null)
        {
            uniqueFlag = iPositionService.checkPositionNameUnique(position);
        }
        return uniqueFlag;
    }

}
