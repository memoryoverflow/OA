package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Notice;
import com.yj.oa.project.po.Position;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NoticeMapper{
    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/18 20:15
     */
    int deleteByPrimaryKeys(Integer[] positionId) throws RuntimeException;

    /**
     *
     * @描述插入
     *
     * @date 2018/9/18 20:15
     */
    int insertSelective(Notice record);

    /**
     *
     * @描述 根据id查询
     *
     * @date 2018/9/18 20:15
     */
    Notice selectByPrimaryKey(Integer id);

    /**
     *
     * @描述 修改
     *
     * @date 2018/9/18 20:15
     */
    int updateByPrimaryKeySelective(Notice record);

    /**
     * 列表
     * @param record
     * @return
     */
    List<Notice> selectNoticeList(Notice record);

}