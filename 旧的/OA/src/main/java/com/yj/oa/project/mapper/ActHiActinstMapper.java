package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ActHiActinst;

import java.util.List;

public interface ActHiActinstMapper{


    /**
     * 删除活动历史信息
     * @param procInstId id
     * @return
     */
    int deleteByProcInstId(String[] procInstId);

    /**
     * 根据进程实例id获取当前 实例的活动信息
     * @param procInstId 实例id
     * @return
     */
    List<ActHiActinst> selectByByProcInstId(String procInstId);

}