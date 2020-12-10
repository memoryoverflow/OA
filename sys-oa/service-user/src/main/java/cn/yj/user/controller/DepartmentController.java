package cn.yj.user.controller;


import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.entity.R;
import cn.yj.user.ConsVal;
import cn.yj.user.entity.po.Department;
import cn.yj.user.entity.po.Position;
import cn.yj.user.entity.po.Role;
import cn.yj.user.service.IDepartmentService;
import cn.yj.user.service.IRoleService;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */

@RestController
@RequestMapping("/dept")
public class DepartmentController extends AbstractController<Department>
{

    @Autowired
    IDepartmentService thisService;

    //@RequiresPermissions(value = {"dept:list"})
    @GetMapping("/treeData")
    public R treeData()
    {
        return success(thisService.treeData());
    }


    /**
     * 只查询两个字段 id、deptName 下拉列表用
     * @return
     */
    @RequiresPermissions(value = {"dept:list"})
    @GetMapping("/listIdName")
    public R listIdName()
    {
        return success(thisService.listIdName());
    }

    @RequiresPermissions(value = {"dept:add"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody Department entity)
    {
        return result(thisService.insert(entity));
    }


    @RequiresPermissions(value = {"dept:update"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody Department entity)
    {
        return result(thisService.updateById(entity));
    }


    @DeleteMapping("/remove/{ids}")
    public R removeByIds(@PathVariable("ids") Serializable[] ids)
    {
        return success(thisService.removeByIds(ids));
    }

}

