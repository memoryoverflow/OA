package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Dept;

import java.util.List;

public interface DeptMapper {
    /**
     *
     * 批量删除
     */
    int deleteByPrimaryKeys(String[] ids) throws Exception;


    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Dept record);

    /**
     * 主键查询
     * @param depId
     * @return
     */
    Dept selectByPrimaryKey(String depId);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Dept record);


    /**
     *
     * @描述 部门列表
     *
     * @date 2018/9/15 13:39
     */
    List<Dept> selectDeptList(Dept dept);

     /**
      *
      * @描述 检验名称唯一性
      *
      * @date 2018/9/16 11:42
      */
     Dept checkDeptNameUnique(String deptName);

     /**
      *
      * @描述: 查询所有部门下的所有用户 用户归类 树状数据
      *
      * @date: 2018/9/27 11:25
     */
    List<Dept> selectDeptAndUser();
}