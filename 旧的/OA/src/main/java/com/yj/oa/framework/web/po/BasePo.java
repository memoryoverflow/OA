package com.yj.oa.framework.web.po;


import java.util.Date;

/**
 * 公共字段，时间查询
 */
public class BasePo {

    private Date beginTime;
    private Date overTime;

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getOverTime()
    {
        return overTime;
    }

    public void setOverTime(Date overTime)
    {
        this.overTime = overTime;
    }
}
