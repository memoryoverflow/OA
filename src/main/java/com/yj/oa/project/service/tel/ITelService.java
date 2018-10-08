package com.yj.oa.project.service.tel;

import com.yj.oa.project.po.Note;
import com.yj.oa.project.po.Tel;

import java.util.List;

/**
 * @author 永健
 */
public interface ITelService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Tel record);

    Tel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tel record);

    List<Tel> selectTelList(Tel tel);
}
