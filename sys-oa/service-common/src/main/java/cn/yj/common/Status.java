
package cn.yj.common;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2019-11-25 14:34
 */
public interface Status
{

    enum Form
    {
        表单填写(100), 表单审核中(101), 同意(102), 拒绝(103),取消(104);
        private int code;

        Form(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }

        public Form setCode(int code)
        {
            this.code = code;
            return this;
        }

        public static String equals(int code)
        {
            Form[] values = values();
            for (Form value : values)
            {
                if (value.getCode() == (code))
                {
                    return value.name();
                }
            }
            return null;
        }
    }

    enum User
    {
        正常(1), 禁用(-1);
        private Integer code;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        User(Integer i)
        {
            this.code = i;
        }

        public static String equals(Integer code)
        {
            User[] values = values();
            for (User value : values)
            {
                if (value.getCode().equals(code))
                {
                    return value.name();
                }
            }
            return null;
        }
    }

    enum SysInterFace
    {
        正常(1), 禁用(0);
        private Integer code;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        SysInterFace(Integer i)
        {
            this.code = i;
        }

        public static String equals(Integer code)
        {
            SysInterFace[] values = values();
            for (SysInterFace value : values)
            {
                if (value.getCode().equals(code))
                {
                    return value.name();
                }
            }
            return null;
        }
    }

    enum Menu
    {
        目录(1), 菜单(2), 按钮(3);
        private Integer code;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        Menu(Integer i)
        {
            this.code = i;
        }

        public static String equals(Integer code)
        {
            Menu[] values = values();
            for (Menu value : values)
            {
                if (value.getCode().equals(code))
                {
                    return value.name();
                }
            }
            return null;
        }
    }

}
