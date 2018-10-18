package com.yj.oa.common.utils.ftp;

import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.HttpHeaderUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 *
 * @描述 ftp上传工具
 *
 * @date 2018/9/20 0:35
 */
public class FtpUtil{
    private static Logger log = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * FTP服务器ip
     */
    private static final String host = "106.14.226.138";
    /**
     * FTP服务器端口
     */
    private static final int port = 21;
    /**
     * FTP登录账号
     */
    private static final String username = "ftpuser";
    /**
     * FTP登录密码
     */
    private static final String password = "123";
    /**
     * FTP服务器基础目录,/home/ftpuser/images 图片上传到这 服务器路径
     */
    private static final String basePath = "/home/ftpuser/images";
    /**
     * FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     */
    public static final String filePath = "http://106.14.226.138:7777";


    /**
     *
     * @描述 文件上传
     *
     * @date 2018/9/20 0:36
     */
    public static boolean uploadFile(Map<String, InputStream> FileMap) throws IOException
    {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        InputStream input = null;
        //如果某个上传失败，将当前集合上传成功的图片删除
        List<String> uploadS = new ArrayList<>();

        //if连接成功
        if (getConnect(ftp))
        {
            //执行上传,遍历集合取值
            int i = 0;
            String fileName = "";
            for (Map.Entry<String, InputStream> entry : FileMap.entrySet())
            {
                //文件名
                fileName = entry.getKey();
                //文件流
                input = entry.getValue();
                try
                {
                    //上传操作
                    result = ftp.storeFile(fileName, input);

                    //上传成功
                    if (result)
                    {
                        i++;
                        uploadS.add(fileName);
                    }
                }
                catch (IOException e)
                {
                    ftp.disconnect();
                    ftp.logout();
                    input.close();
                    return false;
                }
                finally
                {
                    log.info("已成功上传" + (i) + " 个");
                }
            }
        }

        closeConnect(ftp);
        input.close();
        return result;
    }

    /**
     *
     * @描述 文件下载
     *
     * @date 2018/9/19 23:56
     */
    public static ResponseEntity<byte[]> fileDown(String fileId, String fileName) throws IOException
    {
        FTPClient ftp = new FTPClient();
        boolean b = false;
        if (getConnect(ftp))
        {

            //拿到服务器的所有文件
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs)
            {
                if (ff.getName().equals(fileId))
                {
                    log.info("$$$$$ 开始下载", DateUtils.DateToSTr(new Date()));
                    InputStream in = null;
                    ByteArrayOutputStream outputStream = null;
                    try
                    {
                        in = ftp.retrieveFileStream(ff.getName());
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        outputStream = new ByteArrayOutputStream();
                        while ((len = in.read(buffer)) > 0)
                        {
                            outputStream.write(buffer, 0, len);
                        }

                        byte[] bytes = outputStream.toByteArray();

                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.setContentDispositionFormData("attachment", getFileName(fileName));
                        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                        closeConnect(ftp);
                        in.close();
                        outputStream.close();

                        log.info("$$$$$ 下载完成", DateUtils.DateToSTr(new Date()));
                        return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ResponseEntity<byte[]>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    /**
     * 检查是否有图片名称
     */
    public static boolean isHashFile(String filename) throws IOException
    {
        FTPClient ftp = new FTPClient();
        boolean b = false;
        if (getConnect(ftp))
        {
            //拿到服务器的所有文件
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs)
            {
                if (ff.getName().equals(filename))
                {
                    return true;
                }
            }
        }
        closeConnect(ftp);
        return false;

    }


    /**
     *
     * @描述 删除文件
     *
     * @date 2018/9/20 0:41
     */
    public static boolean deleteFile(String[] FileName) throws IOException
    {
        FTPClient ftp = new FTPClient();
        boolean bol = false;
        if (getConnect(ftp))
        {
            int count = 0;
            for (String fileName : FileName)
            {
                try
                {
                    bol = ftp.deleteFile(fileName);
                    if (bol)
                    {
                        count++;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.out.println("删除了：" + count + "个");
                }
            }
            closeConnect(ftp);

        }
        return bol;
    }


    /**
     *
     * @描述 关闭连接
     *
     * @date 2018/9/19 23:52
     */
    public static void closeConnect(FTPClient ftp) throws IOException
    {
        //登出
        ftp.logout();
        //断开连接
        if (ftp.isConnected())
        {
            ftp.disconnect();
        }
    }


    /**
     *
     * @描述 创建连接
     *
     * @date 2018/9/19 23:52
     */
    public static boolean getConnect(FTPClient ftp) throws IOException
    {
        ftp.setControlEncoding("UTF-8");
        try
        {
            int reply;
            // 连接FTP服务器
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                log.error("连接失败");
                return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //切换到上传目录
        ftp.changeWorkingDirectory(basePath);
        //设置为被动模式
        ftp.enterLocalPassiveMode();
        //设置上传文件的类型为二进制类型
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return true;
    }


    //创建下载的位置
    public static void mkdirs(String localPath)
    {
        java.io.File file2 = new java.io.File(localPath);
        if (!file2.exists() && !file2.isDirectory())
        {
            // 没有则创建
            file2.mkdirs();
        }
    }


    /**
     *
     * @描述 文件下载 对文件进行编码,防止中文乱码
     *
     * @date 2018/9/20 0:06
     */
    public static String getFileName(String fileName) throws UnsupportedEncodingException
    {
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        //获取请求头代理
        String userAgent = HttpHeaderUtil.getUserAgent();
        for (String keyWord : IEBrowserKeyWords)
        {
            if (userAgent.contains(keyWord))
            {
                //IE内核统一 UTF-8
                return URLEncoder.encode(fileName, "UTF-8");
            }
        }
        //火狐等其他 ISO-8859-1
        return new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    }


//    public static void main(String[] a) throws IOException
//    {
//        java.io.File file = new java.io.File("F:\\win桌面文件\\Desktop\\OA规划\\管理员权限.png");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        Map<String, InputStream> map = new HashMap();
//        map.put(file.getName(), fileInputStream);
//        uploadFile(map);
//    }
}
