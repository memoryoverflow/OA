package cn.yj.activity.web;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IProcessDef;
import cn.yj.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <br>
 * 流程定义Controller
 * <p>
 * <p>
 * 列表、挂起、激活
 *
 * @author 永健
 * @since 2020-12-08 16:19
 */
@RestController
@RequestMapping("/activity/processDef")
public class ProcessDefController extends ActivityBaseController
{

    @Autowired
    private IProcessDef processDef;


    /**
     * 1、模型列表
     *
     * @param processName 名称
     * @return
     */
    @GetMapping("/list")
    public R list(String processName)
    {
        return success(processDef.listPage(processName, page()));
    }


    /**
     * 2、挂起
     *
     * @param modelVo 入参
     * @return 结果
     */
    @PostMapping("/create")
    public R create(@RequestBody @Valid ModelVo modelVo)
    {
        return success();
    }

    /**
     * 3、激活
     *
     * @param modelId 入参
     * @return 结果
     */
    @DeleteMapping("/delete/{modelId}")
    public R create(@PathVariable("modelId") String modelId)
    {
        return success();
    }
}
