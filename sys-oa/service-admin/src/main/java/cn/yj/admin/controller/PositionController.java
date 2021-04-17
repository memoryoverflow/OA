package cn.yj.admin.controller;


import cn.yj.admin.entity.po.Position;
import cn.yj.admin.service.IPositionService;
import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @OperateLog(describe = "岗位列表")
    @RequiresPermissions(value = {"post:list"})
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> param)
    {
        return success(thisService.findList(param, page(new OrderBy(OrderBy.Direction.ASC, "create_time"))));
    }

    @GetMapping("/listIdAndNameCode")
    public R listAnd()
    {
        return success(thisService.listIdAndNameCode());
    }

    @OperateLog(describe = "新增岗位")
    @RequiresPermissions(value = {"post:add"})
    @PostMapping("/save")
    public R insertSave(@Valid @RequestBody Position entity)
    {
        return result(thisService.insert(entity));
    }

    @OperateLog(describe = "修改岗位")
    @RequiresPermissions(value = {"post:update"})
    @PutMapping("/update")
    public R editSave(@Valid @RequestBody Position entity)
    {
        return result(thisService.updateById(entity));
    }

    @OperateLog(describe = "删除岗位")
    @DeleteMapping("/remove/{ids}")
    @RequiresPermissions(value = {"post:del"})
    public R removeByIds(@PathVariable("ids") Serializable[] ids)
    {
        return success(thisService.removeByIds(ids));
    }

}

