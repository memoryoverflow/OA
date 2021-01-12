package cn.yj.syslog.service;

import cn.yj.common.ServiceImpl;
import cn.yj.syslog.entity.SysLog;
import cn.yj.syslog.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-17 18:32
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService
{
}
