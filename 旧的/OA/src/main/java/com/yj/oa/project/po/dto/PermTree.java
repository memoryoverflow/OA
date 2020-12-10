package com.yj.oa.project.po.dto;

import java.util.List;

/**
 * @author 永健
 */
public class PermTree{
    private int id;
    private String name;
    /**
     * 默认不选中
     */
    private String checked="false";
    private List<PermTree> children;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getChecked()
    {
        return checked;
    }

    public void setChecked(String checked)
    {
        this.checked = checked;
    }

    public List<PermTree> getChildren()
    {
        return children;
    }

    public void setChildren(List<PermTree> children)
    {
        this.children = children;
    }
}
