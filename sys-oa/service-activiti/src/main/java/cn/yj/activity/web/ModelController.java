package cn.yj.activity.web;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IModelService;
import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * <br>
 * 流程模型Controller
 * <p>
 * <p>
 * 模型列表、创建、删除、发布、流程设计
 *
 * @author 永健
 * @since 2020-12-08 16:19
 */
@RestController
@RequestMapping("/activity/model")
public class ModelController extends AbstractController{

    @Autowired
    private IModelService modelService;


    /**
     * 1、模型列表
     *
     * @param modelName 模型名称
     * @return
     */
    @GetMapping("/list")
    public R list(String modelName) {
        return success(modelService.getListPageModel(modelName, page()));
    }


    /**
     * 2、流程模型创建
     *
     * @param modelVo 入参
     * @return 结果
     */
    @PostMapping("/create")
    @OperateLog(describe = "创建模型")
    public R create(@RequestBody @Valid ModelVo modelVo) {
        return success(modelService.crateModel(modelVo));
    }

    /**
     * 3、流程模型创建
     *
     * @param modelId 入参
     * @return 结果
     */
    @DeleteMapping("/delete/{modelId}")
    @OperateLog(describe = "删除模型")
    public R create(@PathVariable("modelId") String modelId) {
        modelService.deleteByModelId(modelId);
        return success();
    }

    /**
     * 4、流程发布
     *
     * @param modelId 入参
     * @return 结果
     */
    @PostMapping("/deploy/{modelId}")
    @OperateLog(describe = "部署模型")
    public R modelDeploy(@PathVariable("modelId") String modelId) throws IOException {
        modelService.modelDeploy(modelId);
        return success();
    }
}
