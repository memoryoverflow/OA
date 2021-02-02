package cn.yj.user.service.impl;

import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.entity.po.Position;
import cn.yj.user.entity.po.User;
import cn.yj.user.mapper.PositionMapper;
import cn.yj.user.service.IPositionService;
import cn.yj.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 12:47
 */
@Service
@Transactional
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService
{

    @Resource
    IUserService iUserService;

    @Override
    @CheckObjectValue(keyValue = @KeyValue(type = Position.class, name = {"positionName"}))
    public boolean insert(Position position)
    {
        if (StringUtils.isNotNull(baseMapper.selectByName(position.getPositionName())))
        {
            throw new ServiceException("该岗位名称 [ " + position.getPositionName() + " ]已经在");
        }

        String deptCode = position.getPositionCode();
        if (StringUtils.isNotBlank(deptCode) && StringUtils.isNotNull(baseMapper.selectByCode(deptCode)))
        {
            throw new ServiceException("该岗位编码 [ " + position.getPositionName() + " ]已经在");
        }
        position.setId(UUIdUtils.getUUId32());
        position.setCreateTime(new Date());
        position.setUpdateTime(new Date());
        return super.insert(position);
    }

    @Override
    @CheckObjectValue(keyValue = @KeyValue(type = Position.class, name = {"id", "positionName"}))
    public boolean updateById(Position position)
    {
        Position positionById = baseMapper.selectById(position.getId());
        Position positionDb = baseMapper.selectByName(position.getId());
        if (StringUtils.isNotNull(positionDb) && !positionDb.getId().equals(position.getId()))
        {
            throw new ServiceException("该部门名称 [ " + position.getPositionName() + " ]已经在");
        }

        String postCode = position.getPositionCode();
        if (StringUtils.isNotBlank(postCode))
        {
            positionDb = baseMapper.selectByCode(postCode);
            if (StringUtils.isNotNull(positionDb) && !position.getId().equals(positionDb.getId()))
            {
                throw new ServiceException("该部门编码 [ " + position.getPositionCode() + " ]已经在");
            }
        }
        position.setUpdateTime(new Date());


        // 更新用户的部门编码code
        if (StringUtils.isNotBlank(position.getPositionCode()) && !positionById.getPositionCode().equals(position.getPositionCode()))
        {
            List<User> userListByPositionCode = iUserService.getUserListByPositionCode(positionById.getPositionCode());
            if (!userListByPositionCode.isEmpty())
            {
                userListByPositionCode.forEach(user -> {
                    user.setPositionCode(position.getPositionCode());
                    iUserService.updateUserInfoById(user);
                });
            }
        }

        return super.updateById(position);
    }

    @Override
    public List<Map<String, String>> listIdAndNameCode()
    {
        return baseMapper.listIdAndNameCode();
    }
}
