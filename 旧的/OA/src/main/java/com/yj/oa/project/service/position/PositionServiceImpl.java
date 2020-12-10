package com.yj.oa.project.service.position;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.PositionMapper;
import com.yj.oa.project.mapper.UserMapper;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Position;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.dept.DeptServiceImpl;
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
public class PositionServiceImpl implements IPositionService{
    private Logger log= LoggerFactory.getLogger(DeptServiceImpl.class);
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    UserMapper userMapper;

    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/18 19:44
     */
    @Override
    public int deleteByPrimarysKey(Integer[] positionId)
    {
        // 当前岗位是否已经分配
        for (Integer id :
                positionId)
        {
            User user = new User();
            user.setPosition(id);
            List<User> users = userMapper.selectByUser(user);
            if (users.size()>0)
            {
                throw new RuntimeException("岗位已分配，无法删除！");
            }
        }

        try
        {
            return positionMapper.deleteByPrimaryKeys(positionId);
        }
        catch (Exception e)
        {
            log.error("$$$$$ 删除岗位失败=[{}]",e);
            throw new RuntimeException("操作失败！");
        }


    }


    /**
     *
     * @描述 添加
     *
     * @date 2018/9/18 21:47
     */
    @Override
    public int insertSelective(Position record)
    {
        return positionMapper.insertSelective(record);
    }

    /**
     *
     * @描述  查询根据id
     *
     * @date 2018/9/18 21:47
     */
    @Override
    public Position selectByPrimaryKey(Integer positionId)
    {
        return positionMapper.selectByPrimaryKey(positionId);
    }


    /**
     *
     * @描述  更改
     *
     * @date 2018/9/18 21:47
     */
    @Override
    public int updateByPrimaryKeySelective(Position record)
    {
        return positionMapper.updateByPrimaryKeySelective(record);
    }


    /**
     *
     * @描述 列表
     *
     * @date 2018/9/15 14:35
     */
    @Override
    public List<Position> selectPositionList(Position position)
    {
        return positionMapper.selectPositionList(position);
    }

    /**
     *
     * @描述 校验是否唯一
     *
     * @date 2018/9/18 19:52
     */
    @Override
    public String checkPositionNameUnique(Position position)
    {
        Integer postId = StringUtils.isNull(position.getPositionId()) ? -1 : position.getPositionId();
        Position info = positionMapper.checkPositionNameUnique(position.getPositionName());
        if (StringUtils.isNotNull(info) && !info.getPositionId().equals(postId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }
}
