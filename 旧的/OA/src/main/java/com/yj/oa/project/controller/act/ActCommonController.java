package com.yj.oa.project.controller.act;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.file.UploadFile;
import com.yj.oa.common.utils.ftp.FtpUtil;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.project.service.ACT.task.IActTaskService;
import com.yj.oa.project.service.leavForm.ILeavFormService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/task")
public class ActCommonController {

    private static final String prefix = "system/processImg";

    @Autowired
    IActTaskService iact_taskService;

    @Autowired
    ILeavFormService iLeavFormService;
    @Autowired
    IUserService iUserService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;


    @RequestMapping("/flowChart/{procInstId}")
    @Operlog(modal = "流程图", descr = "查看流程图")
    public String img(@PathVariable("procInstId") String procInstId, Model model,HttpServletResponse response)
{
        //流程定义Id
        String deploymentId = "";

        //通过实例id 获取任务
        Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();

        //获取当前条进程
        HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().
                processInstanceId(procInstId).singleResult();

        //通过 ProcessDefinitionId 来判断当查看的是哪个图片
        String processDefinitionIdTemp = result.getProcessDefinitionId();
        int i = processDefinitionIdTemp.indexOf(":");
        String proce_Id = processDefinitionIdTemp.substring(0, i - 1);

        if (task != null)
        {
            //通过任务Id获取当前实例任务的信息
            Map<String, Object> currentView = iact_taskService.getCurrentView(task.getId());

            //通过任务id获取流程定义
            ProcessDefinition processDefinition = (ProcessDefinition) currentView.get("processDefinition");
            //通过流程定义获取部署ID 通过部署ID获取图片
            deploymentId = processDefinition.getDeploymentId();
        } else
        {
            //获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
            deploymentId = historicProcessInstance.getDeploymentId();
        }


        //资源流程文件的名称 (.bpmn  .png )  资源
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);

        //获取图片名称
        String pngName = "";
        String pngNameSource = "";
        for (String s : list)
        {
            if (s.indexOf(".png") < 0)
            {
                continue;
            }

            if (s.indexOf(".png") >= 0)
            {
                pngNameSource = s;

                //判断是 请假图 还是会议室申请图片
                if (s.indexOf(proce_Id) >= 0)
                {
                    //解决本地启动 加载进去的文件 因为没启动一次都会添加多一个路径 为本地硬盘的路径
                    if (s.indexOf("\\") >= 0)
                    {
                        pngName = s.substring(s.lastIndexOf("\\") + 1, s.length());

                    } else
                    {
                        pngName = s;
                    }
                    break;
                }else {
                    pngName=pngNameSource;
                }
            }
        }

        //拿到图片的输入流
        InputStream in = repositoryService.getResourceAsStream(deploymentId, pngNameSource);
        boolean hashFile = false;

        //判断服务器上是否有图片
        try
        {
            hashFile = FtpUtil.isHashFile(pngName.replace("\\","-"));
            if (hashFile)
            {
                model.addAttribute("img", FtpUtil.filePath + "/" + pngName);
                System.out.println("流程图片路径：" + FtpUtil.filePath + "/" + pngName);
                return prefix + "/img";
            }
            //没有有就上传
            Map<String, InputStream> map = new HashMap<>();
            map.put(pngName, in);
            if (FtpUtil.uploadFile(map))
            {
                model.addAttribute("img", FtpUtil.filePath + "/" + pngName);
                return prefix + "/img";
            }else {
                getFlowChart(in,response);
            }
        } catch (IOException e)
        {
            return prefix + "/img";
        }
        return prefix + "/img";
    }

    /**
     *
     * @描述: 查看流程图
     *           如果实例结束就查看历史的
     * @params:
     * @return：
     * @date： 2018/9/23 13:47
     */
    public void getFlowChart(InputStream in, HttpServletResponse response) throws IOException
    {

        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/png");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);


        //将其输出页面
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream outputStream = null;
        try
        {
            outputStream = response.getOutputStream();
            while ((len = in.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e)
        {
            System.out.println("查看流程图失败！"+e);
        }
        in.close();
        outputStream.close();
    }
}
