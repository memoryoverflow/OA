package cn.yj.common.upload;

import cn.yj.tools.exception.ServiceException;
import io.minio.errors.*;
import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-09-02 17:38
 */
public interface FileUpload
{

    String upload(byte[] bytes, String objectName) throws IOException, ServiceException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, MyException;

    /**
     * @param base64 base带头的
     * @param name   文件名
     * @return 访问地址
     * @throws Exception 错误
     */
    String upload(String base64, String name) throws Exception;

    /**
     * 下载
     * @param fileName 文件名
     * @return 文件流
     * @throws Exception
     */
    InputStream download(String fileName) throws Exception;

    String upload(MultipartFile file, String name) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException, ServiceException, MyException;

    String upload(MultipartFile file) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException, ServiceException, MyException;

    String upload(InputStream inputStream, String name) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, ServiceException, MyException;

    void delete(String name) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, ServiceException, MyException;

    default byte[] base64ToByte(String sBase64, Boolean bHead) throws Exception
    {
        if (bHead & sBase64.indexOf(",") > 0)
        {
            sBase64 = sBase64.split(",")[1];
        }

        return base64ToByte(sBase64);
    }

    default byte[] base64ToByte(String sBase64) throws Exception
    {
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            byte[] b = decoder.decodeBuffer(sBase64);

            for (int i = 0; i < b.length; ++i)
            {
                if (b[i] < 0)
                {
                    b[i] = (byte) (b[i] + 256);
                }
            }

            return b;
        } catch (Exception var7)
        {
            throw var7;
        } finally
        {
            ;
        }
    }

}
