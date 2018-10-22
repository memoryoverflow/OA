package com.yj.oa.common.constant;

/**
 * 枚举常量
 * @author 永健
 */
public interface CsEnum{


    /**
     * 用户头像默认
     */
    enum avatar{
        USER_AVATAR("/img/commonuserimg.jpg", "用户默认头像");
        private String value;
        private String descr;

        avatar(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }
    }


    /**
     * 用户常量
     */
    enum user{
        USER_USER_BLOCKED(1, "用户被锁定"), USER_IS_OK(0, "用户正常");
        private int value;
        private String descr;

        user(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (user u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 唯一值验证
     */
    enum unique{
        IS_UNIQUE("0", "唯一"), NOT_UNIQUE("1", "不是唯一值");
        private String value;
        private String descr;

        unique(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (unique u : values())
            {
                if (u.getValue().equals(value))
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 房间状态
     */
    enum meetRoom{
        MEET_ROOM_STATUS_FREE(0, "空闲"),
        MEET_ROOM_STATUS_APPLYING(1, "申请中"),
        MEET_ROOM_STATUS_USING(2, "使用中"),
        MEET_ROOM_STATUS_STOP(3, "停用");


        private int value;
        private String descr;

        meetRoom(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (meetRoom u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 会议室申请表单
     */
    enum ApplyRoomForm{
        APPLY_STATUS_AGREE(0, "同意申请"), APPLY_STATUS_DISAGREE(1, "拒绝申请");

        private int value;
        private String descr;

        ApplyRoomForm(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (ApplyRoomForm u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }


    /**
     * 工作日程
     */
    enum scheduled{
        SCHEDULE_NO_COMPLETE(0, "未完成"), SCHEDULE_YES_COMPLETE(1, "已完成");
        private int value;
        private String descr;

        scheduled(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (scheduled u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }


    /**
     * 工作流程 的 常量
     */
    enum activiti{

        AGENT("agent", "流程的代理人"),
        INITIATOR("initiator", "任务发起人字符窜"),
        FORMID("formId", "任务表的表单id字段 字符窜"),

        //工作流中的 busseniess_key
        BUSINESS_KEY_APPLYROOM("0:FormKey:", " 0:FormKey: :代表会议申请,FormKey 表单ID + 表单Id，0：代表当前实例 是 请假流程"),
        BUSINESS_KEY_LEAVE("1:FormKey:", " 1:FormKey: :代表会议申请,FormKey 表单ID + 表单Id，1：代表当前实例 是 会议室流程"),
        //请假流程表达式判断
        Leave_FLAG("flag", "排他网管的表达式字符窜 flag=true"),
        Leave_FLAG_FALSE("false", "提交请假申请"),
        Leave_FLAG_TRUE("true", "放弃请假申请");

        private String value;
        private String descr;

        activiti(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (activiti u : values())
            {
                if (u.getValue().equals(value))
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }


    /**
     * 请假表单状态
     */
    enum leavForm{
        Leave_status_GOING("1", "请假中"),
        Leave_status_succe("2", "请假成功"),
        Leave_status_false("3", "被拒绝请假"),
        Leave_FLAG_giveup("4", "放弃请假");
        private String value;
        private String descr;

        leavForm(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (leavForm u : values())
            {
                if (u.getValue().equals(value))
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 打卡状态
     */
    enum attend{

        ATTEND_ERROR(1, "迟到");
        private int value;
        private String descr;

        attend(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (attend u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 工作时间表
     */
    enum worktime{
        WORK_TIME_USIN(0, "未启用"),
        WORK_TIME_FREE(1, "启用当前工作时间表");
        private int value;
        private String descr;

        worktime(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (worktime u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }


    /**
     * 菜单默认父id
     */
    enum menu{
        MENU_PID(0, "顶级菜单父Id默认为0"),
        MENU_TYPE_ONE(1,"根目录，一级菜单"),
        MENU_TYPE_TWO(2,"菜单，二级菜单"),
        MENU_TYPE_THREE(3,"按钮");
        private int value;
        private String msg;

        menu(int value, String msg)
        {
            this.value = value;
            this.msg = msg;
        }

        public int getValue()
        {
            return value;
        }

        public String getMsg()
        {
            return msg;
        }
    }

}
