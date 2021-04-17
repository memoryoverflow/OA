package cn.yj.admin.service;

import cn.yj.admin.entity.po.Department;
import cn.yj.common.baseDao.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IDepartmentService extends IService<Department>
{

    /**
     * 树形表格
     *
     * @return Department
     */
    List<Department> treeData();

    /**
     * 只查询两个字段的接口
     * @return
     */
    List<Map<String,Object>> listIdName();
}
