package cn.yj.user.controller;


import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.commons.utils.MapUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.ConsVal;
import cn.yj.common.Status;
import cn.yj.user.entity.po.User;
import cn.yj.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/user")
public class UserController extends AbstractController<User>
{

    @Autowired
    IUserService thisService;


    @RequiresPermissions(value = {"user:list"})
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> param)
    {
        return success(thisService.findList(param, page(new OrderBy(OrderBy.Direction.ASC, "create_time"))));
    }

    /**
     * 下拉列表 字段：id name
     *
     * @return
     */
    @GetMapping("/listIdName")
    public R listIdName()
    {
        return success(thisService.listIdName());
    }

    @RequiresPermissions(value = {"user:save"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody User entity)
    {
        return result(thisService.insert(entity));
    }


    @RequiresPermissions(value = {"user:update"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody User entity)
    {
        return result(thisService.updateById(entity));
    }

    /**
     * 重置密码
     *
     * @param param key: id password
     * @return
     */
    @PutMapping("/reloadPwd")
    public R reloadPwd(@RequestBody Map<String, Object> param)
    {
        String[] keys = {"id", "password"};
        MapUtils.validateKeyValueIsEmpty(param, keys);

        String id = param.get("id").toString();
        String password = param.get("password").toString();

        if (StringUtils.isNull(id) || StringUtils.isNull(password))
        {
            throw new ServiceException("参数绑定异常");
        }
        return result(thisService.reloadPassword(id, password));
    }

    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PutMapping("/toBlack/{id}/{status}")
    public R toBlack(@PathVariable("id") String id, @PathVariable("status") Integer status)
    {
        if (StringUtils.isNull(Status.User.equals(status)))
        {
            throw new ServiceException("参数异常");
        }
        return result(thisService.updateUserInfoById(new User(id).setStatus(status)));
    }


    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @RequiresPermissions(value = {"user:del"})
    @DeleteMapping("/remove/{ids}")
    public R delete(@PathVariable("ids") String[] ids)
    {
        return result(thisService.removeByIds(ids));
    }
}

