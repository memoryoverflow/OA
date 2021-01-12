package cn.yj.activity.web;

import cn.yj.activity.entity.po.Form;
import cn.yj.activity.entity.vo.FormVo;
import cn.yj.activity.service.IFormService;
import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:46
 */
@RestController
@RequestMapping("/form")
public class FormController extends AbstractController<Form>
{
    @Autowired
    IFormService iFormService;

    @GetMapping("/list")
    public R list(Map<String, Object> params)
    {
        return success(iFormService.findList(params, page(new OrderBy(OrderBy.Direction.DESC, "create_time"))));
    }

    @GetMapping("/selectMyForm")
    public R selectMyForm(Map<String, Object> params)
    {
        params.put("createUserId", getCurrentUserId());
        return success(iFormService.findList(params, page(new OrderBy(OrderBy.Direction.DESC, "create_time"))));
    }

    @OperateLog(describe = "新增表单")
    @PostMapping("/save")
    public R save(@RequestBody @Valid Form form)
    {
        form.setCreateUserId(getCurrentUserId());
        return success(iFormService.insert(form));
    }

    /**
     * 表单更新
     *
     * @param form
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody @Valid Form form)
    {
        return success(iFormService.updateById(form));
    }

    @PutMapping("/remove/{ids}")
    public R remove(@PathVariable("ids") String[] ids)
    {
        return success(iFormService.removeByIds(ids
        ));
    }

    @GetMapping("/getFormById")
    public R getFormById(@RequestParam("procDefId") String id)
    {
        return success(iFormService.selectByProcDefId(id));
    }
}
