package com.yj.oa.framework.aspect.enums;

/**
 * @author 永健
 */
public enum OperLogStatusEnum{
    SUCCESS_STATUS(0,"正常"),ERROR_STATUS(1,"异常");

    private Integer value;
    private String msg;

    OperLogStatusEnum(Integer value, String msg)
    {
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public static String getMsg(Integer i)
    {
        for (OperLogStatusEnum logEnum: OperLogStatusEnum.values())
        {
            if (i==logEnum.getValue())
            {
                return logEnum.getMsg();
            }
        }
        return null;
    }

    public static Boolean isEquals(Integer i)
    {
        for (OperLogStatusEnum logEnum: OperLogStatusEnum.values())
        {
            if (i==logEnum.getValue())
            {
                return true;
            }
        }
        return false;
    }


}
