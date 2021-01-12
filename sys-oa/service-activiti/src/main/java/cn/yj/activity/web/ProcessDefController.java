package cn.yj.activity.web;

import cn.yj.activity.entity.po.ProcDfExtend;
import cn.yj.activity.service.IProcessDef;
import cn.yj.common.OperateLog;
import cn.yj.commons.utils.StringUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.ServiceException;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * <br>
 * 流程定义Controller
 * <p>
 * <p>
 * 列表、挂起、激活、获取节点信息
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
     * 2、挂起、激活
     *
     * @param processId 入参
     * @return 结果
     */
    @PostMapping("/active/{id}/{status}")
    @OperateLog(describe = "挂起/激活流程定义")
    public R create(@PathVariable("id") String processId, @PathVariable("status") int status)
    {
        if (SuspensionState.ACTIVE.getStateCode() == status)
        {
            processDef.activateProcessDefinitionById(processId);
            return success();
        }
        processDef.suspendProcessDefinitionById(processId);
        return success();
    }

    /**
     * 3、流程图查看
     *
     * @param deployId 部署Id
     * @return 结果
     */
    @GetMapping("/checkImg/{deployId}")
    @OperateLog(describe = "查看流程图")
    public void create(@PathVariable("deployId") String deployId, HttpServletResponse response) throws IOException
    {
        //获取图片的输入流
        InputStream in = processDef.getResourceImgStream(deployId);

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        BufferedImage bi = ImageIO.read(in);
        ImageIO.write(bi, "png", response.getOutputStream());
    }

    @OperateLog(describe = "查看流程图")
    @GetMapping("/checkImgByProcDefId/{procDefId}")
    public void checkImgByProcDefId(@PathVariable("procDefId") String procDefId, HttpServletResponse response) throws IOException
    {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        if (StringUtils.isNull(processDefinition)){
            throw new ServiceException("该流程定义Id有误会");
        }

        //获取图片的输入流
        InputStream in = processDef.getResourceImgStream(processDefinition.getDeploymentId());

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        BufferedImage bi = ImageIO.read(in);
        ImageIO.write(bi, "png", response.getOutputStream());
    }


    /**
     * 流程定义 编辑  扩展更新
     *
     * @param procDfExtend
     * @return
     */
    @PutMapping("/editExtendUpdate")
    public R editExtendUpdate(@RequestBody @Valid ProcDfExtend procDfExtend)
    {
        processDef.editExtendUpdate(procDfExtend);
        return success();
    }

    /**
     * 获取流程节点信息
     * @param procDefId 流程定义id
     * @return
     */
    @GetMapping("/getProcDefNodes")
    public R getProcDefNodes(@RequestParam("procDefId") String procDefId){
        return success(processDef.getProcDefNodesByProcDefId(procDefId));
    }


    /**
     * 获取第一个用户任务的节点
     * @param procDefId
     * @return
     */
    @GetMapping("/getProcDefFirstNode")
    public R getProcDefFirstNode(@RequestParam("procDefId")String procDefId){
        return success(processDef.getProcDefFirstUserTask(procDefId));
    }
}
