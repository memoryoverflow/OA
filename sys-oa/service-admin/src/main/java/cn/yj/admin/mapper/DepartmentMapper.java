package cn.yj.admin.mapper;

import cn.yj.admin.entity.po.Department;
import cn.yj.common.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:54
 */
public interface DepartmentMapper extends BaseMapper<Department>
{
    Department selectByName(String name);

    @Select("select * from tb_department where dept_code=#{deptCode}")
    Department selectByCode(String deptCode);

    List<Department> treeData();

    @Select("select id,dept_name as deptName from tb_department")
    List<Map<String, Object>> listIdName();
}
