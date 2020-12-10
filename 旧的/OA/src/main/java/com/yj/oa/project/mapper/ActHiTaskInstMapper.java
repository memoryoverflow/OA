package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ActHiTaskInst;

import java.util.List;

public interface ActHiTaskInstMapper{

    /**
     *
     * @描述:  删除批量
     *
     * @params:
     * @return:
     * @date: 2018/9/23 16:01
    */
    int deleteByprocInstIds(String[] ids);

    /**
     *
     * @描述: 主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/23 16:02
    */
    ActHiTaskInst selectByPrimaryKey(String id);

    /**
     *
     * @描述: 根据审批人查询历史审批记录
     *
     * @params:
     * @return:
     * @date: 2018/9/23 16:02
    */
    List<ActHiTaskInst> selectActHiTaskInstList(ActHiTaskInst actHiTaskInst);



}