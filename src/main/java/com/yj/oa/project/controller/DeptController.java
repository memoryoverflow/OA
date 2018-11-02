package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Dept;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.dept.IDeptService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController{

    private String prefix = "system/dept/";

    @Autowired
    IDeptService iDeptService;

    @Autowired
    IUserService iUserService;


    /**
     *
     * @描述 页面跳转到部门
     *
     * @date 2018/9/16 10:59
     */

    @RequestMapping("/tolist")
    @RequiresPermissions("dept:list")
    public String tolist()
    {
        return prefix + "dept";
    }


    /**
     *
     * @描述 ajax请求的所有部门
     *
     * @date 2018/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Dept> list(Dept dept)
    {
        List<Dept> depts = iDeptService.selectDeptList(dept);
        return depts;
    }

    /**
     *
     * @描述 部门列表页
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Dept dept)
    {
        //开启分页
        startPage();
        List<Dept> depts = iDeptService.selectDeptList(dept);
        return getDataTable(depts);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */
    @RequiresPermissions("dept:add")
    @RequestMapping("/toAdd")
    public String toAdd(Model model)
    {
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);
        return prefix + "add";
    }


    /**
     *
     * @描述: 查询所有部门下的所有用户 用户归类 树状数据
     *
     * @date: 2018/9/27 11:25
     */
    @RequestMapping("/getDeptAndUserTreeData")
    @ResponseBody
    public List<Object> DeptAndUserTreeData()
    {
        List<Dept> depts = iDeptService.selectDeptAndUser();

        List<User> users=new ArrayList<>();
        LinkedList<Object> deptList = new LinkedList<>();
        for (Dept dept : depts)
        {
            Map<String, Object> deptMap = new HashMap();
            deptMap.put("name", dept.getDeptName());
            deptMap.put("id", null);

            users = dept.getUsers();

            LinkedList<Object> userlist = new LinkedList<>();
            for (User user : users)
            {
                Map<String, Object> userMap = new HashMap();
                userMap.put("name",user.getName());
                userMap.put("id",user.getUid());
                userMap.put("icon","/img/timg.jpg");
                userlist.add(userMap);
            }
            deptMap.put("children",userlist);
            deptList.add(deptMap);
        }

        return deptList;
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("dept:del")
    @ResponseBody
    @Operlog(modal = "部门管理",descr = "删除部门")
    public AjaxResult del(String[] ids)
    {
        try
        {
            iDeptService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 执行保存操作
     *
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @Operlog(modal = "部门管理",descr = "添加部门")
    @RequiresPermissions("dept:add")
    @ResponseBody
    public AjaxResult addDept(Dept dept)
    {
        dept.setCreateTime(new Date());
        return  result(iDeptService.insertSelective(dept));
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("dept:update")
    public String edit(@PathVariable("id") String id, Model model)
    {
        Dept dept = iDeptService.selectByPrimaryKey(id);
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);

        model.addAttribute("Dept", dept);
        return prefix + "edit";

    }

    /**
     *
     * @描述 修改保存
     *
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("dept:update")
    @Operlog(modal = "部门管理",descr = "修改信息")
    @ResponseBody
    public AjaxResult save(Dept dept)
    {
        int i = 0;
        try
        {
            i = iDeptService.updateByPrimaryKeySelective(dept);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Dept dept)
    {
        String uniqueFlag = "0";
        if (dept != null)
        {
            uniqueFlag = iDeptService.checkDeptNameUnique(dept);
        }
        return uniqueFlag;
    }
}
