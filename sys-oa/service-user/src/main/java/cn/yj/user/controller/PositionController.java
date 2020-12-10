package cn.yj.user.controller;


import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.entity.R;
import cn.yj.user.ConsVal;
import cn.yj.user.entity.po.Position;
import cn.yj.user.service.IPositionService;
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
@RequestMapping("/post")
public class PositionController extends AbstractController<Position>
{

    @Autowired
    IPositionService thisService;


    @RequiresPermissions(value = {"post:list"})
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> param)
    {
        return success(thisService.findList(param, page(new OrderBy(OrderBy.Direction.ASC, "create_time"))));
    }

    @RequiresPermissions(value = {"post:list"})
    @GetMapping("/listIdAndNameCode")
    public R listAnd()
    {
        return success(thisService.listIdAndNameCode());
    }

    @RequiresPermissions(value = {"post:add"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody Position entity)
    {
        return result(thisService.insert(entity));
    }


    @RequiresPermissions(value = {"post:update"})
    @RequiresRoles(value = {ConsVal.SUPER_ADMIN_CODE})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody Position entity)
    {
        return result(thisService.updateById(entity));
    }


    @DeleteMapping("/remove/{ids}")
    @RequiresPermissions(value = {"post:del"})
    public R removeByIds(@PathVariable("ids") Serializable[] ids)
    {
        return success(thisService.removeByIds(ids));
    }

}

