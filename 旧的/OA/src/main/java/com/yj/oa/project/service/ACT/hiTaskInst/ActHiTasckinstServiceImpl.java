package com.yj.oa.project.service.ACT.hiTaskInst;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.ActHiProcinst;
import com.yj.oa.project.po.ActHiTaskInst;
import com.yj.oa.project.service.ACT.hiActInst.IActHiActInstService;
import com.yj.oa.project.service.ACT.hiprocInst.IActHiProcinstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class ActHiTasckinstServiceImpl implements IActHiTaskinistService{

    @Autowired
    ActHiTaskInstMapper actHiTaskInstMapper;

    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;

    @Autowired
    LeaveFormMapper leaveFormMapper;

    @Autowired
    ActHiActinstMapper actHiActinstMapper;

    @Autowired
    ApplyRoomFormMapper applyRoomFormMapper;

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return：
     * @date： 2018/9/23 16:09
     */
    @Override
    public int deleteByPrimaryKeys(String[] array) throws Exception
    {
        //数组去重复
        String[] ids = StringUtils.arrayToNoRepeat(array);
        //判断当前任务是否在执行中
        for (String id : ids)
        {
            ActHiProcinst actHiProcinst = actHiProcinstMapper.selectByPrimaryKey(id);
            if (actHiProcinst.getEndTime() == null)
            {
                throw new Exception("任务待审中！");
            }

        }
        try
        {
            //1.删除活动历史表 act_hi_actinst 数据
            actHiActinstMapper.deleteByProcInstId(ids);

            //2.删除历史任务表 act_hi_taskinst 数据
            int i = actHiTaskInstMapper.deleteByprocInstIds(ids);

            //3.删除历史进程表 act_hi_proceInst 数据
            actHiProcinstMapper.deleteByPrimaryKeys(ids);

            //4.删除表单数据
            applyRoomFormMapper.deleteByprocInstIds(ids);
            leaveFormMapper.deleteByprocInstIds(ids);

            return i;
        }
        catch (Exception e)
        {
            throw new RuntimeException("操作失败！");
        }
    }


    /**
     *
     * @描述: 主键查询
     *
     * @params:
     * @return：
     * @date： 2018/9/23 16:10
     */
    @Override
    public ActHiTaskInst selectByPrimaryKey(String id)
    {
        return actHiTaskInstMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 用户历史任务列表
     *
     * @params:
     * @return：
     * @date： 2018/9/23 16:10
     */
    @Override
    public List<ActHiTaskInst> selectActHiTaskInstList(ActHiTaskInst actHiTaskInst)
    {
        return actHiTaskInstMapper.selectActHiTaskInstList(actHiTaskInst);
    }
}
