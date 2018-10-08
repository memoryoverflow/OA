package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Position;

import java.util.List;

public interface PositionMapper {
    /**
     * 删除
     * @param positionId
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKeys(Integer[] positionId) throws Exception;

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Position record);

    /**
     * 主键查询
     * @param positionId
     * @return
     */
    Position selectByPrimaryKey(Integer positionId);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * 列表
     * @param position
     * @return
     */
    List<Position> selectPositionList(Position position);

    /**
     * 名称唯一
     * @param name
     * @return
     */
    Position checkPositionNameUnique(String name);
}