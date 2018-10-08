package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;

public class User extends BasePo{


    private String uid;

    private String pwd;
    private String loginName;


    private String name;


    private String sex;


    private Integer age;


    private Integer dept;


    private Integer position;


    private String hobby;


    private String tel;


    private String email;


    private String avatar;


    private Date createTime;

    private Integer status;


    private Integer isdel;


    private String self_introduction;

    private Integer role_ID;

    /**
     * 角色
     */
    private Role role;

    private Dept deptPo;

    /**
     * 岗位
     */
    private Position positionPo;


    /**
     * 判断是否是管理员
     */
    public static boolean isBoss(String uid)
    {
        return uid.equals("1111111111") ? true : false;
    }


    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getDept()
    {
        return dept;
    }

    public void setDept(Integer dept)
    {
        this.dept = dept;
    }

    public Integer getPosition()
    {
        return position;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public String getHobby()
    {
        return hobby;
    }

    public void setHobby(String hobby)
    {
        this.hobby = hobby;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getIsdel()
    {
        return isdel;
    }

    public void setIsdel(Integer isdel)
    {
        this.isdel = isdel;
    }

    public String getSelf_introduction()
    {
        return self_introduction;
    }

    public void setSelf_introduction(String self_introduction)
    {
        this.self_introduction = self_introduction;
    }

    public Integer getRole_ID()
    {
        return role_ID;
    }

    public void setRole_ID(Integer role_ID)
    {
        this.role_ID = role_ID;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Dept getDeptPo()
    {
        return deptPo;
    }

    public void setDeptPo(Dept deptPo)
    {
        this.deptPo = deptPo;
    }

    public Position getPositionPo()
    {
        return positionPo;
    }

    public void setPositionPo(Position positionPo)
    {
        this.positionPo = positionPo;
    }


    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", age=").append(age);
        sb.append(", dept=").append(dept);
        sb.append(", position=").append(position);
        sb.append(", hobby='").append(hobby).append('\'');
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", isdel=").append(isdel);
        sb.append(", self_introduction='").append(self_introduction).append('\'');
        sb.append(", role_ID=").append(role_ID);
        sb.append(", role=").append(role);
        sb.append(", deptPo=").append(deptPo);
        sb.append(", positionPo=").append(positionPo);
        sb.append('}');
        return sb.toString();
    }
}