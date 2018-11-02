package com.yj.oa.framework.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.oa.common.constant.Constants;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.HttpHeaderUtil;
import com.yj.oa.common.utils.ServletUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.ShiroUtils;
import com.yj.oa.framework.web.page.PageDomain;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.page.TableSupport;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.User;
import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.service.menu.IPermissionService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 */
public class BaseController{


    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message)
    {
        return AjaxResult.error(code, message);
    }

    /**
     * 根据修改搜影响的行数返回结果
     */
    public AjaxResult result(int i)
    {
        return i > 0 ? success() : error();
    }


    public User getUser()
    {
        return ShiroUtils.getUser();
    }


    public String getUserId()
    {
        return getUser().getUid();
    }

    public String getName()
    {
        return getUser().getName();
    }

    public String getPwd()
    {
        return getUser().getPwd();
    }

    public String getRoleId()
    {
        return getUser().getRole_ID().toString();
    }


    /**
     * 更新菜单信息
     */
    public void updateMenuSession(IPermissionService iPermissionService)
    {
        List<MenuTree> menuTreeList = iPermissionService.selectMenuTree(getUserId());
        ServletUtils.getSession().setAttribute(Constants.MENU_SESSION, menuTreeList);
    }


    /**
     *
     * @描述 根据日期生成用户Id
     *
     * @date 2018/9/15 20:41
     */
    public static String createUID()
    {
        String s = DateUtils.DateToSTr2(new Date());
        String UID = s.replace("-", "").replace(":", "").replace(" ", "").trim();
        return UID;
    }

}
