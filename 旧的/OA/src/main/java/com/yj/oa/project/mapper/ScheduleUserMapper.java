package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ScheduleUser;

import java.util.List;

public interface ScheduleUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(List<ScheduleUser> userList);

}