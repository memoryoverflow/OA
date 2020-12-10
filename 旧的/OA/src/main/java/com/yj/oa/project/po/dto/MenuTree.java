package com.yj.oa.project.po.dto;

import java.util.List;

/**
 * @author 永健
 */
public class MenuTree{
    private Integer id;

    private String name;

    private Integer pid;

    private String url;


    private String icon;

    private List<MenuTree> children;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public List<MenuTree> getChildren()
    {
        return children;
    }

    public void setChildren(List<MenuTree> children)
    {
        this.children = children;
    }
}
