package cn.yj.user.controller;


import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.entity.R;
import cn.yj.user.ConsVal;
import cn.yj.user.entity.po.Role;
import cn.yj.user.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController extends AbstractController<Role>
{

    @Autowired
    IRoleService thisService;


    @RequiresPermissions(value = {"role:list"})
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> param)
    {
        return success(thisService.findList(param, page(new OrderBy(OrderBy.Direction.ASC, "create_time"))));
    }

    @GetMapping("/listIdNameAll")
    public R listAll()
    {
        return success(thisService.listIdNameAll());
    }

    @RequiresPermissions(value = {"role:add"})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody Role entity)
    {
        return result(thisService.insert(entity));
    }


    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @RequiresPermissions(value = {"role:update"})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody Role entity)
    {
        return result(thisService.updateById(entity));
    }

    /**
     * 给角色授权
     *
     * @param roleId  角色id
     * @param menuIds 菜单Id
     * @return
     */
    @PutMapping("/{roleId}/toRoleAuth/{menuIds}")
    public R toRoleAuth(@PathVariable("roleId") String roleId, @PathVariable("menuIds") String[] menuIds)
    {
        return result(thisService.toRoleAuth(roleId, menuIds));
    }

    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @DeleteMapping("/remove/{ids}")
    @RequiresPermissions(value = {"role:del"})
    public R removeByIds(@PathVariable("ids") Serializable[] ids)
    {
        return success(thisService.removeByIds(ids));
    }


}

