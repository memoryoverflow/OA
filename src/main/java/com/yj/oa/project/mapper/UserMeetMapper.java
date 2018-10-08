package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.UserMeet;

import java.util.List;

public interface UserMeetMapper {

    int deleteByMeetIdKeys(Integer[] id);


    int insertSelective(List<UserMeet> lsit);



}