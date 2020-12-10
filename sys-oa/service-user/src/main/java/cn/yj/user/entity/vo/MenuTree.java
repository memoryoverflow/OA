package cn.yj.user.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2019-10-28 15:24
 */
public class MenuTree implements Serializable
{
    private String id;
    private String perName;
    private String parentId;
    private String parentName;
    private String router;
    private String icon;
    private String url;
    private String code;
    private Integer sort;
    private boolean outJoin;
    private String type;
    private List<MenuTree> children;

    public boolean isOutJoin()
    {
        return outJoin;
    }

    public MenuTree setOutJoin(boolean outJoin)
    {
        this.outJoin = outJoin;
        return this;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPerName()
    {
        return perName;
    }

    public void setPerName(String perName)
    {
        this.perName = perName;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getRouter()
    {
        return router;
    }

    public void setRouter(String router)
    {
        this.router = router;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getUrl()
    {
        return url;
    }

    public MenuTree setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }


    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
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
