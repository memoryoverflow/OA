package com.yj.oa.project.mapper;

import com.yj.oa.project.po.OperLog;

import java.util.List;

public interface OperLogMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(OperLog record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    OperLog selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OperLog record);

    /**
     * 列表
     * @param operLog
     * @return
     */
    List<OperLog> selectOperLogList(OperLog operLog);

}