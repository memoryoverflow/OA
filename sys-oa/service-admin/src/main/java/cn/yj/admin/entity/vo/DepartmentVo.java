package cn.yj.admin.entity.vo;

import cn.yj.admin.entity.po.Department;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:56
 */
@Data
public class DepartmentVo extends Department
{
    /**
     * 部门负责人
     */
    private Map<String,Object> user;
    private List<Department> children;
}
