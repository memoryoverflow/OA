package com.yj.oa.common.utils.file;

import com.yj.oa.common.exception.file.FileNameLengthException;
import com.yj.oa.common.exception.file.FileSizeException;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.ftp.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 永健
 * 上传工具类封装
 */
public class UploadFile{

    private static Logger Log = LoggerFactory.getLogger(UploadFile.class);

    public static final String basepath = "http://106.14.226.138:7777/";
    /**
     * 默认大小 10M //字节
     */
    public static final long DEFAULT_MAX_SIZE = 10485760;
    /**
     * 默认的文件名最大长度
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;


    /**
     *
     * @描述 文件上传
     *
     * @date 2018/9/19 20:41
     */
    public static String upload(MultipartFile file) throws IOException, FileSizeException, FileNameLengthException
    {

        String fileId = getFileTimeId();
        //判断文件大小
        assertAllowed(file);
        //检查文件名字长度
        checkFileNameLength(file);
        //获得文件流
        InputStream input = file.getInputStream();
        //获得文件名字
        String name = fileId + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),
                                                                    file.getOriginalFilename().length());
        Map<String, InputStream> fileMap = new HashMap<>();
        fileMap.put(name, input);
        //调用方法执行上传
        return FtpUtil.uploadFile(fileMap) ? name : null;
    }


    /**
     * 头像上传
     *
     * @param file 图片文件
     */
    public static String uploadUserImg(
            MultipartFile file) throws FileNameLengthException, FileSizeException, IOException
    {
        return basepath + upload(file);
    }


    /**
     *
     * @描述 下载
     *
     * @date 2018/9/19 20:41
     */
    public static ResponseEntity<byte[]> download(String fileId, String fileName) throws IOException
    {
        return FtpUtil.fileDown(fileId, fileName);
    }


    /**
     *
     * @描述 文件删除
     *
     * @date 2018/9/19 23:47
     */
    public static boolean delFile(String[] ids) throws IOException
    {
        return FtpUtil.deleteFile(ids);
    }


    /**
     *
     * @描述 文件名长度校验
     *
     * @date 2018/9/19 10:55
     */
    public static void checkFileNameLength(MultipartFile file) throws FileNameLengthException
    {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthException("文件名字超长", file.getOriginalFilename(), fileNamelength,
                                              DEFAULT_FILE_NAME_LENGTH);
        }
    }


    /**
     *
     * @描述 文件大小校验
     *
     * @date 2018/9/19 10:55
     */
    public static void assertAllowed(MultipartFile file) throws FileSizeException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeException("文件大小超出范围", size,
                                        DEFAULT_MAX_SIZE);
        }
    }


    /**
     *
     * @描述 生成文件id 也就是文件上传到服务器的名字
     *
     * @date 2018/9/19 13:34
     */
    public static String getFileTimeId()
    {
        return DateUtils.DateToSTr(new Date()).replace(" ", "_").replace(":", "-");
    }

}
