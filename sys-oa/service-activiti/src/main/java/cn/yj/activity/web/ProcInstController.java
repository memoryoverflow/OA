package cn.yj.activity.web;

import cn.yj.activity.service.IProcInstService;
import cn.yj.common.OperateLog;
import cn.yj.commons.utils.MapUtils;
import cn.yj.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <br>
 * 流程实例
 *
 * @author 永健
 * @since 2020-12-11 17:30
 */
@RestController
@RequestMapping("/procInst")
public class ProcInstController extends ActivityBaseController
{

    @Autowired
    private IProcInstService iProcInstService;

    @OperateLog(describe = "启动流程实例")
    @PostMapping("/start")
    public R startProcInstId(@RequestBody Map<String, Object> params)
    {
        String[] key = {"procDefId"};
        MapUtils.validateKeyValueIsEmpty(params, key);
        iProcInstService.startProcessInstanceById(params.get("procDefId").toString());
        return success();
    }

}
