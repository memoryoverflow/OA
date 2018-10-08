package com.yj.oa.project.service.file;

import com.yj.oa.project.po.Files;

import java.util.List;

/**
 * @author 永健
 */
public interface IFileService{
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Files file);

    /**
     *
     * @描述 删除
     *
     * @date 2018/9/19 11:57
     */
    public int deleteByPrimaryKeys(String[] ids);

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Files> selectFileList(Files file);

    void downloadCountAddOne(Files files);
}
