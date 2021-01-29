package cn.yj.common.upload;

import cn.yj.common.upload.FileUpload;
import cn.yj.commons.utils.StringUtils;
import cn.yj.tools.exception.ServiceException;
import cn.yj.tools.readconfig.PropertiesUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-09-02 17:47
 */
class FastDFSUpload implements FileUpload
{
    private static StorageClient1 client;
    private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSUpload.class);

    private static String FAST_DFS_SERVER;

    static
    {
        // 建立连接
        try
        {
            //fastDFS方式
            String filePath = "config.yml";
            ClientGlobal.initByProperties(filePath);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            StorageServer storageServer = tracker.getStoreStorage(trackerServer);
            client = new StorageClient1(trackerServer, storageServer);
            String configValue = PropertiesUtils.getStringValue("fastdfs.tracker_servers");
            FAST_DFS_SERVER = "http://" + configValue.substring(0, configValue.indexOf(":")).concat("/");
        } catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error("FastDFs初始化异常", e);
        }
    }

    @Override
    public String upload(byte[] bytes, String objectName) throws IOException, MyException
    {

        //设置元信息
        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("fileName", objectName);
        metaList[1] = new NameValuePair("fileExtName", "人脸图片");
        metaList[2] = new NameValuePair("fileLength", String.valueOf(bytes.length));
        metaList[3] = new NameValuePair("作者", "永健");

        // 上传文件，获得fileId
        int i = objectName.lastIndexOf(".");
        String suffix = objectName.substring(i + 1);
        String fileId;
        try
        {
            fileId = client.upload_file1(bytes, suffix, metaList);
        } catch (IOException | MyException e)
        {
            fileId = client.upload_file1(bytes, suffix, metaList);
        }
        if (StringUtils.isEmpty(fileId))
        {
            throw new ServiceException("文件上传失败，fileId:" + fileId);
        }
        return FAST_DFS_SERVER.concat(fileId);
    }

    @Override
    public String upload(String base64, String name) throws Exception
    {
        return upload(base64ToByte(base64), name);
    }

    @Override
    public InputStream download(String fileName) throws Exception
    {
        return null;
    }

    @Override
    public String upload(MultipartFile file, String name) throws IOException, MyException
    {

        return upload(file.getBytes(), name);
    }

    @Override
    public String upload(MultipartFile file) throws IOException, MyException
    {
        return upload(file.getBytes(), file.getOriginalFilename());
    }

    @Override
    public String upload(InputStream inputStream, String name) throws IOException, MyException
    {
        return upload(inputTobyte(inputStream), name);
    }

    @Override
    public void delete(String name) throws IOException, MyException
    {
        if (name.contains(FAST_DFS_SERVER))
        {
            name.replace(FAST_DFS_SERVER, "");
        }
        client.delete_file1(name);
    }

    public static byte[] inputTobyte(InputStream inStream)
            throws IOException
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0)
        {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }


    /**
     * @param sBase64 base64
     * @param bHead   是否带有头
     * @return
     */
    private static byte[] base64ToByte(String sBase64, boolean bHead)
    {
        if (bHead & sBase64.indexOf(",") > 0)
        {
            sBase64 = sBase64.split(",")[1];
        }

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = null;

        try
        {
            b = decoder.decodeBuffer(sBase64);

            for (int i = 0; i < b.length; ++i)
            {
                if (b[i] < 0)
                {
                    b[i] = (byte) (b[i] + 256);
                }
            }
        } catch (Exception var7)
        {
            System.out.println(var7.getMessage());
        }

        return b;
    }

}
