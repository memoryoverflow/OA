package cn.yj.admin.service;

import cn.yj.admin.entity.po.Position;
import cn.yj.common.baseDao.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IPositionService extends IService<Position>
{

    List<Map<String,String>> listIdAndNameCode();
}
