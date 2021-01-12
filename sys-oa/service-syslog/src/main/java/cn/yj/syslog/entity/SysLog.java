package cn.yj.syslog.entity;

import cn.yj.common.BaseEntity;
import cn.yj.commons.utils.StringUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-17 17:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sys_log")
public class SysLog extends BaseEntity
{
    /**
     * 日志类型 0:info，1:error
     */
    private String type;

    /**
     * 功能
     */
    private String descr;

    /**
     * 主机
     */
    private String host;
    /**
     * 地点
     */
    private String ipAddress;

    /**
     * 请求url
     */
    private String url;
    /**
     * 方法
     */
    private String method;
    /**
     * 参数
     */
    private String params;
    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 操作人
     */
    private String operUser;


    /**
     * 操作浏览器
     */
    private String browser;


    /**
     * 操作系统
     */
    private String sys;

    public SysLog(String id)
    {
        super.setId(id);
    }

    public SysLog()
    {
    }

    public String getMapToParams(Map<String, String[]> paramMap)
    {
        if (paramMap == null)
        {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : paramMap.entrySet())
        {
            params.append(("".equals(params.toString()) ? "" : " , ") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue,
                    100));
        }
        return params.toString();
    }
}
