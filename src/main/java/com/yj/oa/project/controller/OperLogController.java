package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.OperLog;
import com.yj.oa.project.service.operlog.IOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/operlog")
public class OperLogController extends BaseController{
    private String prefix = "system/operlog/";
    @Autowired
    IOperLogService iOperLogService;


    /**
     *
     * @描述:  跳转
     *
     * @params:
     * @return:
     * @date: 2018/10/2 18:12
    */

    @RequestMapping("/tolist")
    public String toList(){
        return prefix+"operlog";
    }



    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(OperLog operLog)
    {
        System.out.println(operLog.getOperModal()+"...");
        startPage();
        List<OperLog> operLogs = iOperLogService.selectOperLogList(operLog);
        return getDataTable(operLogs);
    }




    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("operlog:del")
    @Operlog(modal = "操作日志",descr = "删除日志")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
//        try
//        {
//            // 删除前需要判断是否是本人发布的公告或这通知
//            iOperLogService.deleteByPrimaryKeys(ids);
//        }
//        catch (Exception e)
//        {
//            return error(e.getMessage());
//        }
//        return success();
        return error("不允许删除！");
    }



    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("operlog:update")
    @Operlog(modal = "操作日志",descr = "查看日志")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        OperLog operLog = iOperLogService.selectByPrimaryKey(id);
        model.addAttribute("operLog", operLog);
        return prefix + "edit";
    }


}
