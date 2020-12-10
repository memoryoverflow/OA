package com.yj.oa.framework.aspect.enums;

/**
 * @author 永健
 */
public enum OperLogTypeEnum{
    SUCCESS_TYPE(0,"info"),ERROR_TYPE(1,"error");

    private Integer value;
    private String msg;

    OperLogTypeEnum()
    {
    }

    private OperLogTypeEnum(Integer value, String msg)
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
        for (OperLogTypeEnum logEnum: OperLogTypeEnum.values())
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
        for (OperLogTypeEnum logEnum: OperLogTypeEnum.values())
        {
            if (i==logEnum.getValue())
            {
                return true;
            }
        }
        return false;
    }


}
