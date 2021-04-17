package cn.yj.admin.entity.vo;

import cn.yj.admin.entity.po.User;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * 数据table
 * @author 永健
 * @since 2020-11-28 21:45
 */
public class UserVo extends User
{
    /**
     * 用户角色
     */
    private List<Map<String, Object>> roles;

    /**
     * 部门
     */
    private Map<String, String> dept;

    /**
     * 岗位
     */
    private Map<String, String> position;

    public List<Map<String, Object>> getRoles()
    {
        return roles;
    }

    public UserVo setRoles(List<Map<String, Object>> roles)
    {
        this.roles = roles;
        return this;
    }

    public Map<String, String> getDept()
    {
        return dept;
    }

    public UserVo setDept(Map<String, String> dept)
    {
        this.dept = dept;
        return this;
    }

    public Map<String, String> getPosition()
    {
        return position;
    }

    public UserVo setPosition(Map<String, String> position)
    {
        this.position = position;
        return this;
    }
}
