package com.yj.oa.common.utils.file;

import java.text.DecimalFormat;

/**
 * @author 永健
 *  文件工具类
 */
public class FileUtil{
    private final static String prfiex = "office/";
    /**
    *定义GB的计算常量
    */
    private static int GB = 1024 * 1024 * 1024;
    /**
    *定义MB的计算常量
    */
    private static int MB = 1024 * 1024;
    /**
    *定义KB的计算常量
    */
    private static int KB = 1024;

    /**
     *
     * @描述 将文件大小转换为不同的单位
     *
     * @date 2018/9/19 23:40
     */
    public static String getFileSize(Long size)
    {
        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize = "";
        if (size / GB >= 1)
        {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB   ";
        }
        else if (size / MB >= 1)
        {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB   ";
        }
        else if (size / KB >= 1)
        {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB   ";
        }
        else
        {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     *
     * @描述 office文档后缀
     *
     * @date 2018/9/19 16:21
     */
    public static String getType(String type)
    {
        if (type.endsWith("document"))
        {
            return prfiex + "docx";
        }
        else if (type.endsWith("template"))
        {
            return prfiex+".dotx";
        }
        else if (type.endsWith("presentation"))
        {
            return prfiex+"pptx";
        }
        else if (type.endsWith("slideshow"))
        {
            return prfiex+"ppsx";
        }
        else if (type.endsWith("template"))
        {
            return prfiex+"potx";
        }
        else if (type.endsWith("sheet"))
        {
            return prfiex+"xlsx";
        }
        else {
            return type;
        }
    }
}
