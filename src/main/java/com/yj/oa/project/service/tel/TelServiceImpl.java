package com.yj.oa.project.service.tel;

import com.yj.oa.project.mapper.TelMapper;
import com.yj.oa.project.po.Tel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class TelServiceImpl implements ITelService{
    @Autowired
    TelMapper telMapper;


    /**
     *
     * @描述:  批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] id)
    {
        return telMapper.deleteByPrimaryKeys(id);
    }


    /**
     *
     * @描述:  添加
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int insertSelective(Tel record)
    {
        return telMapper.insertSelective(record);
    }


    /**
     *
     * @描述:  主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public Tel selectByPrimaryKey(Integer id)
    {
        return telMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述:  修改
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:45
     */
    @Override
    public int updateByPrimaryKeySelective(Tel record)
    {
        return telMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述:  个人便签列表
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:50
     */
    @Override
    public List<Tel> selectTelList(Tel Tel)
    {
        return telMapper.selectTelList(Tel);
    }
}
