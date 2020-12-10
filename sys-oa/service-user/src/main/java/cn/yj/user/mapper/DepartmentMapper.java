package cn.yj.user.mapper;

import cn.yj.common.BaseMapper;
import cn.yj.user.entity.po.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:54
 */
public interface DepartmentMapper<T extends Department> extends BaseMapper<T>
{
    Department selectByName(String name);

    @Select("select * from tb_department where dept_code=#{deptCode}")
    Department selectByCode(String deptCode);

    List<Department> treeData();

    @Select("select id,dept_name as deptName from tb_department")
    List<Map<String, Object>> listIdName();
}
