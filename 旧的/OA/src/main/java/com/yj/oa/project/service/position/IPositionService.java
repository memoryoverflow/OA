package com.yj.oa.project.service.position;

import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Position;

import java.util.List;

/**
 * @author 永健
 * @date 2018/9/15 14:34
 * @描述
 */
public interface IPositionService {
    /**
     *
     * @描述 根据主键删除
     *
     * @date 2018/9/18 19:32
     */
    int deleteByPrimarysKey(Integer[] positionId);


    /**
     *
     * @描述 插入
     *
     * @date 2018/9/18 19:32
     */
    int insertSelective(Position record);

    /**
     *
     * @描述  根据主键查询
     *
     * @date 2018/9/18 19:33
     */
    Position selectByPrimaryKey(Integer positionId);

    /**
     *
     * @描述 字段不为空更新
     *
     * @date 2018/9/18 19:33
     */
    int updateByPrimaryKeySelective(Position record);


    /**
     *
     * @描述 根据对象所有字段查询
     *
     * @date 2018/9/18 19:34
     */
    List<Position> selectPositionList(Position position);


    /**
     *
     * @描述 校验名称是否唯一
     *
     * @date 2018/9/18 19:51
     */
    String checkPositionNameUnique(Position position);
}
