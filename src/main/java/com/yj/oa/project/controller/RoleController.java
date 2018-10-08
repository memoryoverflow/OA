package com.yj.oa.project.controller;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.ShiroUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Dept;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Role;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.dept.IDeptService;
import com.yj.oa.project.service.menu.IPermissionService;
import com.yj.oa.project.service.role.IRoleService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Ids;

import java.util.Date;
import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    private String prefix = "system/role/";


    @Autowired
    IUserService iUserService;

    @Autowired
    IRoleService iRoleService;

    @Autowired
    IPermissionService iPermissionService;

    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("role:list")
    public String tolist()
    {
        return prefix + "role";
    }


    /**
     *
     * @描述 ajax请求所有
     *
     * @date 2018/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Role> list(Role role)
    {
        List<Role> roles = iRoleService.selectRoleList(role);
        return roles;
    }

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Role role)
    {
        //开启分页
        startPage();
        List<Role> roles = iRoleService.selectRoleList(role);
        return getDataTable(roles);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("role:add")
    public String toAdd(Model model)
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
    @RequiresPermissions("role:del")
    @Operlog(modal = "角色管理",descr = "删除角色")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iRoleService.deleteByPrimaryKeys(ids);
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
    @RequiresPermissions("role:update")
    @Operlog(modal = "角色管理",descr = "添加角色")
    @ResponseBody
    public AjaxResult addRole(Role role, Integer[] ids)
    {
        role.setCreateTime(new Date());
        int insert = 0;
        try
        {
            if (StringUtils.isEmpty(ids))
            {
                ids = new Integer[0];
            }
            insert = iRoleService.insert(role, ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        //清空缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        return  result(insert);
    }


    /**
     *
     * @描述: 根据ID 获取u他的所有权限 做回显
     *
     * @params: roleId 角色Id
     * @return:
     * @date: 2018/9/27 14:04
     */
    @RequestMapping("/selectById/{roleId}")
    @ResponseBody
    public Role selectById(@PathVariable("roleId") Integer roleId)
    {
        Role role = iRoleService.selectByPrimaryKey(roleId);
        return role;
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("role:update")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Role role = iRoleService.selectByPrimaryKey(id);
        model.addAttribute("Role", role);
        return prefix + "edit";
    }

    /**
     *
     * @描述 编辑修改权限页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/editPower/{id}")
    @RequiresPermissions("role:update")
    public String editPower(@PathVariable("id") Integer id, Model model)
    {
        Role role = iRoleService.selectByPrimaryKey(id);
        model.addAttribute("Role", role);
        return prefix + "editPower";
    }


    /**
     *
     * @描述 修改角色信息保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("role:update")
    @Operlog(modal = "角色管理",descr = "修改角色信息")
    @ResponseBody
    public AjaxResult save(Role role)
    {
        int i = 0;
        try
        {
            i = iRoleService.updateByPrimaryKeySelective(role);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     *
     * @描述 修改角色权限信息保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editPowerSave")
    @RequiresPermissions("role:update")
    @Operlog(modal = "角色管理",descr = "修改角色权限")
    @ResponseBody
    public AjaxResult editPowerSave(Role role, Integer[] ids)
    {
        int i = 0;
        try
        {
            if (StringUtils.isEmpty(ids))
            {
                ids = new Integer[0];
            }
            i = iRoleService.updateByPrimaryKeyPowerSelective(role, ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        //清空缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        //如果用户正在修改的角色id 是当前用户的角色id 则刷新 subject的User信息
        if (role.getRoleId().equals(getRoleId()))
        {
            ShiroUtils.reloadUser(iUserService.selectByPrimaryKey(getUserId()));
        }
        return result(i);
    }


    /**
     * 校验名称唯一
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Role role)
    {
        String uniqueFlag = "0";
        if (role != null)
        {
            uniqueFlag = iRoleService.checkRoleNameUnique(role);
        }
        return uniqueFlag;
    }
}
