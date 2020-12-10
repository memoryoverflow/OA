package com.yj.oa.common.exception.file;

import org.apache.commons.fileupload.FileUploadException;

/**
 * @author 永健
 * 文件名超长异常
 */
public class FileNameLengthException extends FileUploadException{

    private String msg;
    //文件名
    private String fileName;
    //文件长度
    private int length;
    //最大文件长度
    private int limitLength;

    public FileNameLengthException(String msg,String filename, int length, int maxLength)
    {
        super("file name : [" + filename + "], length : [" + length + "], max length : [" + maxLength + "]");

        this.msg=msg;
        this.fileName=filename;
        this.length=length;
        this.limitLength=maxLength;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getLimitLength()
    {
        return limitLength;
    }

    public void setLimitLength(int limitLength)
    {
        this.limitLength = limitLength;
    }
}
