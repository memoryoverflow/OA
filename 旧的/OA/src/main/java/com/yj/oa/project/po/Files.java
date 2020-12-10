package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

public class Files extends BasePo{

    private String fileId;


    private String fileName;


    //文件描述
    private String descr;

    //文件类型
    private String fileType;

    private String fileSize;

    private String uploadUser;


    private Date createTime;

    private int downloadCount;

    public int getDownloadCount()
    {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount)
    {
        this.downloadCount = downloadCount;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(String fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getUploadUser()
    {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser)
    {
        this.uploadUser = uploadUser;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("File{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", descr='").append(descr).append('\'');
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append(", uploadUser='").append(uploadUser).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}