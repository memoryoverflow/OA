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
 * @author ??
 */
public class FtpUtil
{
    private static Logger log = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * FTP???ip
     */
    private static final String host = "106.14.226.138";
    /**
     * FTP?????
     */
    private static final int port = 21;
    /**
     * FTP????
     */
    private static final String username = "ftpuser";
    /**
     * FTP????
     */
    private static final String password = "123";
    /**
     * FTP???????,/home/ftpuser/images ?????? ?????
     */
    private static final String basePath = "/home/ftpuser/images";
    /**
     * FTP??????????????????/2018/05/28???????basePath+filePath
     */
    public static final String filePath = "http://106.14.226.138:7777";


    /**
     *
     * @?? ????
     *
     * @date 2018/9/20 0:36
     */
    public static boolean uploadFile(Map<String, InputStream> FileMap) throws IOException
    {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        InputStream input = null;
        //???????????????????????
        List<String> uploadS = new ArrayList<>();

        //if????
        if (getConnect(ftp))
        {
            //????,??????
            int i = 0;
            String fileName = "";
            for (Map.Entry<String, InputStream> entry : FileMap.entrySet())
            {
                //???
                fileName = entry.getKey();
                //???
                input = entry.getValue();
                try
                {
                    //????
                    result = ftp.storeFile(fileName, input);

                    //????
                    if (result)
                    {
                        i++;
                        uploadS.add(fileName);
                    }
                } catch (IOException e)
                {
                    ftp.disconnect();
                    ftp.logout();
                    input.close();
                    return false;
                } finally
                {
                    log.info("upload" + (i) + " ");
                }
            }
        }

        closeConnect(ftp);
        input.close();
        return result;
    }

    /**
     *
     * @?? ????
     *
     * @date 2018/9/19 23:56
     */
    public static ResponseEntity<byte[]> fileDown(String fileId, String fileName) throws IOException
    {
        FTPClient ftp = new FTPClient();
        boolean b = false;
        if (getConnect(ftp))
        {

            //??????????
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs)
            {
                //??????????
                if (ff.getName().equals(fileId))
                {
                    log.info("$$$$$ ????", DateUtils.DateToSTr(new Date()));
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


                        log.info("$$$$$ ????", DateUtils.DateToSTr(new Date()));
                        return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        closeConnect(ftp);
                        in.close();
                        outputStream.close();
                    }
                }
            }
        }
        return new ResponseEntity<byte[]>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    /**
     * ?????????
     */
    public static boolean isHashFile(String filename) throws IOException
    {
        FTPClient ftp = new FTPClient();
        boolean b = false;
        if (getConnect(ftp))
        {
            //??????????
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
     * @?? ????
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
                } catch (IOException e)
                {
                    e.printStackTrace();
                } finally
                {
                    System.out.println("????" + count + "?");
                    closeConnect(ftp);
                }
            }

        }
        return bol;
    }


    /**
     *
     * @?? ????
     *
     * @date 2018/9/19 23:52
     */
    public static void closeConnect(FTPClient ftp) throws IOException
    {
        //??
        ftp.logout();
        //????
        if (ftp.isConnected())
        {
            ftp.disconnect();
        }
    }


    /**
     *
     * @?? ????
     *
     * @date 2018/9/19 23:52
     */
    public static boolean getConnect(FTPClient ftp) throws IOException
    {
        ftp.setControlEncoding("UTF-8");
        try
        {
            int reply;
            // ??FTP???
            ftp.connect(host, port);
            // ?????????????ftp.connect(host)???????FTP???
            // ??
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                log.error("????");
                return false;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //???????
        ftp.changeWorkingDirectory(basePath);
        //???????
        ftp.enterLocalPassiveMode();
        //???????????????
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return true;
    }


    //???????
    public static void mkdirs(String localPath)
    {
        java.io.File file2 = new java.io.File(localPath);
        if (!file2.exists() && !file2.isDirectory())
        {
            // ?????
            file2.mkdirs();
        }
    }


    /**
     *
     * @?? ???? ???????,??????
     *
     * @date 2018/9/20 0:06
     */
    public static String getFileName(String fileName) throws UnsupportedEncodingException
    {
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        //???????
        String userAgent = HttpHeaderUtil.getUserAgent();
        for (String keyWord : IEBrowserKeyWords)
        {
            if (userAgent.contains(keyWord))
            {
                //IE???? UTF-8
                return URLEncoder.encode(fileName, "UTF-8");
            }
        }
        //????? ISO-8859-1
        return new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    }


    //    public static void main(String[] a) throws IOException
    //    {
    //        java.io.File file = new java.io.File("F:\\win????\\Desktop\\OA??\\?????.png");
    //        FileInputStream fileInputStream = new FileInputStream(file);
    //        Map<String, InputStream> map = new HashMap();
    //        map.put(file.getName(), fileInputStream);
    //        uploadFile(map);
    //    }
}
