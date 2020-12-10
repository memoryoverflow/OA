package com.yj.oa.common.exception.file;


import org.apache.commons.fileupload.FileUploadBase;

/**
 * @author 永健
 * 文件大小异常
 */
public class FileSizeException extends FileUploadBase.FileSizeLimitExceededException{


    private String msg;
    private long size;
    private long limitSize;

    public FileSizeException(String message, long actual, long permitted)
    {
        super(message, actual, permitted);
        this.msg=message;
        this.size=actual;
        this.limitSize=permitted;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public long getLimitSize()
    {
        return limitSize;
    }

    public void setLimitSize(long limitSize)
    {
        this.limitSize = limitSize;
    }
}
