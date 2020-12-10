package cn.yj.user.service.impl;

import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.entity.po.Position;
import cn.yj.user.mapper.PositionMapper;
import cn.yj.user.service.IPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @CheckObjectValue(keyValue = @KeyValue(type = Position.class, name = {"deptName"}))
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
    @CheckObjectValue(keyValue = @KeyValue(type = Position.class, name = {"id", "deptName"}))
    public boolean updateById(Position position)
    {
        Position positionDb = baseMapper.selectByName(position.getPositionName());
        if (StringUtils.isNotNull(positionDb) && !positionDb.getId().equals(position.getId()))
        {
            throw new ServiceException("该部门名称 [ " + position.getPositionName() + " ]已经在");
        }

        String deptCode = position.getPositionCode();
        if (StringUtils.isNotBlank(deptCode))
        {
            positionDb = baseMapper.selectByCode(deptCode);
            if (StringUtils.isNotNull(positionDb) && !position.getId().equals(positionDb.getId()))
            {
                throw new ServiceException("该部门编码 [ " + position.getPositionCode() + " ]已经在");
            }
        }
        position.setUpdateTime(new Date());
        return super.updateById(position);
    }

    @Override
    public List<Map<String, String>> listIdAndNameCode()
    {
        return baseMapper.listIdAndNameCode();
    }
}
