package com.yj.oa.project.service.leavForm;

import com.yj.oa.project.po.LeaveForm;

import java.util.List;

/**
 * @author 永健
 */
public interface ILeavFormService{
    /**
     *
     * 批量删除
     * @mbggenerated
     */
    int deleteByPrimaryKeys(String[] ids) throws Exception;

    /**
     *添加
     * @mbggenerated
     */
    void insertSelective(LeaveForm record);

    /**
     *主键查询
     *
     * @mbggenerated
     */
    LeaveForm selectByPrimaryKey(Integer id);

    /**
     * 修改状态
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LeaveForm record);


    /**
     * 列表
     * @mbggenerated
     */
    List<LeaveForm> selectLeavFormList(LeaveForm leaveForm);


    /**
     * 填写表单
     * @mbggenerated
     */
    void fillForm(LeaveForm leaveForm);

    /**
     * 提交表单
     * @mbggenerated
     */
    void submit(String proceId);

    /**
     * 提交表单
     * @mbggenerated
     */
    void giveUp(String proceId);
}
