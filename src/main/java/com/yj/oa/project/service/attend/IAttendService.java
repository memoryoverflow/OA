package com.yj.oa.project.service.attend;

import com.yj.oa.project.po.Attend;
import com.yj.oa.project.po.OperLog;

import java.util.List;

/**
 * @author 永健
 */
public interface IAttendService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Attend record) throws Exception;

    Attend selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Attend record);
    List<Attend> selectAttendList(Attend attend);

    Attend selectSaveDayIsAttend(String userId);

}
