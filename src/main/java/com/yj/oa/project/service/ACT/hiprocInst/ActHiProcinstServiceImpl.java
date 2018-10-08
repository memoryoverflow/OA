package com.yj.oa.project.service.ACT.hiprocInst;

import com.yj.oa.project.mapper.ActHiProcinstMapper;
import com.yj.oa.project.po.ActHiProcinst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class ActHiProcinstServiceImpl implements IActHiProcinstService{


    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;


    /**
     *
     * @描述 : 批量删除
     *
     * @params:
     * @return：
     * @date： 2018/9/23 12:30
     */

    @Override
    public int deleteByPrimaryKeys(String[] id)
    {
        return actHiProcinstMapper.deleteByPrimaryKeys(id);
    }

    /**
     *
     * @描述:根据主键查询
     *
     * @params:
     * @return：
     * @date： 2018/9/23 12:29
     */
    @Override
    public ActHiProcinst selectByPrimaryKey(String id)
    {
        return actHiProcinstMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 根据实例id查询 判断是否已经结束了改流程
     *
     * @params:
     * @return：
     * @date： 2018/9/23 15:19
     */
    @Override
    public ActHiProcinst selectByProcInstId(String procInstId)
    {
        return actHiProcinstMapper.selectByProcInstId(procInstId);
    }


    /**
     *
     * @描述: 列表
     *
     * @params:
     * @return：
     * @date： 2018/9/23 12:30
     */
    @Override
    public List<ActHiProcinst> selectActHiProcinstList(ActHiProcinst actHiProcinst)
    {
        return actHiProcinstMapper.selectActHiProcinstList(actHiProcinst);
    }
}
