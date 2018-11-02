package com.yj.oa.project.service.operlog;

import com.yj.oa.project.mapper.OperLogMapper;
import com.yj.oa.project.po.OperLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class OperLogServiceImpl implements IOperLogService{
    private Logger log= LoggerFactory.getLogger(OperLogServiceImpl.class);

    @Autowired
    OperLogMapper operLogMapper;

    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/18 21:13
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] positionId) throws RuntimeException
    {
        try
        {
            return operLogMapper.deleteByPrimaryKeys(positionId);
        }
        catch (RuntimeException e)
        {
            log.error("$$$$$ 删除公失败=[{}]",e);
            throw new RuntimeException("操作失败！");
        }
    }

    /**
     *
     * @描述 天加
     *
     * @date 2018/9/18 20:19
     */
    @Override
    public int insertSelective(OperLog record)
    {
        System.out.println(record);
        return operLogMapper.insertSelective(record);
    }


    /**
     *
     * @描述 根据id查
     *
     * @date 2018/9/18 20:19
     */
    @Override
    public OperLog selectByPrimaryKey(Integer id)
    {
        return operLogMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述 修改
     *
     * @date 2018/9/18 20:20
     */
    @Override
    public int updateByPrimaryKeySelective(OperLog record)
    {
        return operLogMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述  列表
     *
     * @date 2018/9/18 20:20
     */
    @Override
    public List<OperLog> selectOperLogList(OperLog record)
    {
        return operLogMapper.selectOperLogList(record);
    }
}
