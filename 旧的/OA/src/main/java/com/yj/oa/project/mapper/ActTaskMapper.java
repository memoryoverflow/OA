package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ActTask;

import java.util.List;

public interface ActTaskMapper{

    /**
     *
     * @描述 任务列表
     *
     * @date 2018/9/21 22:47
     */
    List<ActTask> selectACTTaskList(ActTask record);


    /**
     * 批量删除
     *
     * @param ids id数组
     */
    int deletByProcInstS(String[] ids);
}