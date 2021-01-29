package cn.yj.common.upload;

import cn.yj.tools.exception.ServiceException;
import cn.yj.tools.readconfig.PropertiesUtils;

import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-14 11:50
 */
public class LocalUpload implements FileUpload
{
    private static String UPLOAD_PATH;
    private static String LOCAL_ADDRESS = "http://127.0.0.1:%s%s/localFile/";

    static
    {
        UPLOAD_PATH = PropertiesUtils.getStringValue("file.local.path");
        LOCAL_ADDRESS = String.format(LOCAL_ADDRESS, PropertiesUtils.getIntValue("server.port"), PropertiesUtils.getStringValue("server.servlet.context-path")).concat("%s");
        File file = new File(UPLOAD_PATH);
        if (file.isDirectory() && !file.exists())
        {
            file.mkdirs();
        }
    }

    @Override
    public String upload(byte[] bytes, String objectName) throws ServiceException, IOException
    {
        IOUtils.write(bytes, new FileOutputStream(UPLOAD_PATH.concat("/").concat(objectName)));
        return String.format(LOCAL_ADDRESS, objectName);
    }

    @Override
    public String upload(String base64, String name) throws Exception
    {
        return upload(base64ToByte(base64, true), name);
    }

    @Override
    public InputStream download(String fileName) throws Exception
    {
        return new FileInputStream(UPLOAD_PATH.concat("/").concat(fileName));
    }

    @Override
    public String upload(MultipartFile file, String name) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException, ServiceException
    {
        IOUtils.copy(file.getInputStream(), new FileOutputStream(UPLOAD_PATH.concat("/").concat(name)));
        return String.format(LOCAL_ADDRESS, name);
    }

    @Override
    public String upload(MultipartFile file)
    {
        throw new ServiceException("该方法没有实现");
    }

    @Override
    public String upload(InputStream inputStream, String name) throws IOException
    {
        IOUtils.copy(inputStream, new FileOutputStream(UPLOAD_PATH.concat("/").concat(name)));
        return String.format(LOCAL_ADDRESS, name);
    }


    @Override
    public void delete(String name)
    {
        File file = new File(UPLOAD_PATH.concat("/").concat(name));
        if (file.exists())
        {
            file.delete();
        }
    }
}
