package com.yj.oa.project.po;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;
import java.util.Map;

/**
 * @author 永健
 */
public class OperLog extends BasePo{
    private String id;
    /**
     * 操作模块
     */
    private String operModal;

    /** 日志类型 0:info，1:erro */
    private Integer type;
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
     * 请求时间
     */
    private Date time;
    /**
     * 操作人
     */
    private String operUser;

    /** 部门Id*/
    private String deptId;

    /** 状态：0：正常，1:异常*/
    private Integer status;


    private User user;
    private Dept dept;

    /** 操作浏览器*/
    private String browser;
    /** 操作系统*/
    private String sys;


    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getSys()
    {
        return sys;
    }

    public void setSys(String sys)
    {
        this.sys = sys;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Dept getDept()
    {
        return dept;
    }

    public void setDept(Dept dept)
    {
        this.dept = dept;
    }

    /**
     * 设置请求参数
     */
    public String getMapToParams(Map<String, String[]> paramMap)
    {
        if (paramMap == null)
        {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet())
        {
            params.append(("".equals(params.toString()) ? "" : " , ") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue,
                                           100));
        }
       return params.toString();
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOperModal()
    {
        return operModal;
    }

    public void setOperModal(String operModal)
    {
        this.operModal = operModal;
    }

    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getParams()
    {
        return params;
    }

    public void setParams(String params)
    {
        this.params = params;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public String getOperUser()
    {
        return operUser;
    }

    public void setOperUser(String operUser)
    {
        this.operUser = operUser;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }
}
