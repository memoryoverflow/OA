package cn.yj.syslog.controller;


import cn.yj.common.AbstractController;
import cn.yj.common.OperateLog;
import cn.yj.entity.R;
import cn.yj.syslog.entity.SysLog;
import cn.yj.syslog.service.ISysLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 永健
 * @since 2019-12-01 20:36
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController extends AbstractController<SysLog>
{

    @Resource
    ISysLogService thisService;


    @GetMapping("/list")
    @OperateLog(describe = "查看操做日志列表")
    public R list(@RequestParam Map<String, Object> sysLog)
    {
        return success(thisService.findList(sysLog,page()));
    }

}

