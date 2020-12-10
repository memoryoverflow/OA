package com.yj.oa.project.controller;

import com.yj.oa.common.exception.file.FileNameLengthException;
import com.yj.oa.common.exception.file.FileSizeException;
import com.yj.oa.common.utils.file.FileUtil;
import com.yj.oa.common.utils.file.UploadFile;
import com.yj.oa.common.utils.ftp.FtpUtil;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Files;
import com.yj.oa.project.service.file.IFileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 永健
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController{
    private Logger Log= LoggerFactory.getLogger(this.getClass());
    private String prefix = "system/file/";

    @Autowired
    IFileService iFileService;

    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("file:list")
    public String tolist()
    {
        return prefix + "file";
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Files file)
    {
        startPage();
        List<Files> files = iFileService.selectFileList(file);
        return getDataTable(files);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2018/9/16 11:37
     */

    @RequestMapping("/toAdd")
    @RequiresPermissions("file:upload")
    public String toAdd()
    {
        return prefix + "add";
    }


    /**
     *
     * @描述 上传文件
     *
     * @date 2018/9/19 13:00
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("file:upload")
    @Operlog(modal = "文件管理",descr = "上传文件")
    @ResponseBody
    public AjaxResult addSave(MultipartFile file, Files fileBean)
    {
        if (file.isEmpty())
        {
            return error("请选择文件！");
        }

       // 上传
        String fileId = null;
        try
        {
            fileId = UploadFile.upload(file);
        }
        catch (IOException e)
        {
            return error();
        }
        catch (FileSizeException e)
        {
            return error(e.getMsg());
        }
        catch (FileNameLengthException e)
        {
            return error(e.getMsg());
        }


        if (fileId == null)
        {
            return error("上传失败！请再试试！");
        }

        iFileService.insertSelective(getFileBean(file, fileId, fileBean,getUser().getName()));

        return success();
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("file:del")
    @Operlog(modal = "文件管理",descr = "删除文件")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {

        // 1.删除前需要判断是否是本人发布的公告或这通知


        //2.删除数据库
        int i = iFileService.deleteByPrimaryKeys(ids);

        if (i > 0)
        {
            // 3.删除服务器上的文件
            try
            {
                UploadFile.delFile(ids);
            }
            catch (IOException e)
            {
                Log.error("文件已从数据库删除,删除服务器文件出现异常", e.toString());
            }
        }
        return success();
    }



    /**
     *
     * @描述 文件下载
     *
     * @date 2018/9/19 12:09
     */
    @RequestMapping("/download")
    @RequiresPermissions("file:download")
    @Operlog(modal = "文件管理",descr = "下载文件")
    public ResponseEntity<byte[]> download(String fileId, String fileName)
    {
        try
        {
            Files files = new Files();
            files.setFileId(fileId);
            iFileService.downloadCountAddOne(files);
            return UploadFile.download(fileId, fileName);
        }
        catch (IOException e)
        {
            return null;
        }
    }


    /**
     *
     * @描述 上传类 封装Files Bean
     *
     * @date 2018/9/19 15:20
     */
    public static Files getFileBean(MultipartFile file, String fileId, Files fileBean,String userName)
    {
        fileBean.setCreateTime(new Date());
        fileBean.setFileName(file.getOriginalFilename());
        fileBean.setFileId(fileId);
        fileBean.setFileType(FileUtil.getType(file.getContentType()));
//        登录人
        fileBean.setUploadUser(userName);

//        将字节单位转换为MB
        fileBean.setFileSize(FileUtil.getFileSize(file.getSize()));
        return fileBean;
    }


}
