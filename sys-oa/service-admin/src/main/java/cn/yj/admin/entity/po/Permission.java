package cn.yj.admin.entity.po;

import cn.yj.common.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@Data
public class Permission extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int DELETED_VAL=0;
    public static final int STATUS_VAL=0;
    public static final String PARENT_ID_VAL="0";

    private Integer deleted;

    @NotBlank(message = "目录名称必填")
    private String perName;

    private String parentId;


    private String parentName;


    private Integer type;


    private String url;


    private String code;

    private String router;


    @NotBlank(message = "请选择图标")
    private String icon;

    private boolean outJoin;

    private Integer status;

    @NotNull(message = "请选择序号")
    private Integer sort;


}
