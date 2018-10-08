package com.yj.oa.project.service.meet;

import com.yj.oa.project.po.Meet;

import java.util.List;

/**
 * @author 永健
 */
public interface IMeetService{

    Meet selectByPrimaryKey(Integer id);

    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Meet record,String[] userIds);

    int updateByPrimaryKeySelective(Meet record,String[] userIds);

    List<Meet> selectByMeetList(Meet meet);

    List<Meet> selectMyMeetList(String uId);
}
