package com.yj.oa.project.service.ACT.hiActInst;

import com.yj.oa.project.mapper.ActHiActinstMapper;
import com.yj.oa.project.po.ActHiActinst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class ActHiActInstServiceImpl implements IActHiActInstService{

    @Autowired
    ActHiActinstMapper actHiActinstMapper;

    /**
     *
     * @描述: 通过 实例Id 删除与该实例历史活动相关信息
     *
     * @params:
     * @return:
     * @date: 2018/9/23 20:49
    */
    @Override
    public int deleteByProcInstId(String[] procInstIds)
    {
        return actHiActinstMapper.deleteByProcInstId(procInstIds);
    }

    /**
     *
     * @描述: 通过实例Id 获取该活动信息
     *
     * @params:
     * @return:
     * @date: 2018/9/23 20:50
    */
    @Override
    public List<ActHiActinst> selectByByProcInstId(String procInstId)
    {
        return actHiActinstMapper.selectByByProcInstId(procInstId);
    }
}
