package com.yj.oa.project.service.ACT.hiprocInst;

import com.yj.oa.project.po.ActHiProcinst;

import java.util.List;

/**
 * @author 永健
 */
public interface IActHiProcinstService{
    /**
     * @ 描述 批量删除
     * @ param id
     * @date 2018/9/23 11:59
     */
    int deleteByPrimaryKeys(String[] id);

    /**
     * @ 描述 根据主键查询
     * @date 2018/9/23 12:01
     */
    ActHiProcinst selectByPrimaryKey(String id);

    /**
     *
     * @描述: 根据实例id查询 判断改实例是否已经结束
     *
     * @params:
     * @return：
     * @date： 2018/9/23 15:18
     */
    ActHiProcinst selectByProcInstId(String procInstId);


    /**
     * @ 描述 列表
     * @date 2018/9/23 12:02
     */
    List<ActHiProcinst> selectActHiProcinstList(ActHiProcinst actHiProcinst);
}
