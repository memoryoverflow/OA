package com.yj.oa.project.service.operlog;

import com.yj.oa.project.po.Notice;
import com.yj.oa.project.po.OperLog;

import java.util.List;

/**
 * @author 永健
 */
public interface IOperLogService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(OperLog record);

    OperLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperLog record);

    List<OperLog> selectOperLogList(OperLog operLog);

}
