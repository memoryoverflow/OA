package cn.yj.common.upload;

import cn.yj.commons.utils.StringUtils;
import cn.yj.tools.exception.ServiceException;
import cn.yj.tools.readconfig.PropertiesUtils;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-09-02 17:43
 */
class MinioUpload implements FileUpload
{
    private static MinioClient minioClient;
    private static String BUCKET_NAME;
    private static String ENDPOINT;
    private static String ACCESS_KEY;
    private static String SECRET_KEY;

    static
    {
        try
        {
            ENDPOINT = PropertiesUtils.getStringValue("file.minio.end.point");
            ACCESS_KEY = PropertiesUtils.getStringValue("file.minio.accessKey");
            SECRET_KEY = PropertiesUtils.getStringValue("file.minio.secretKey");
            BUCKET_NAME = PropertiesUtils.getStringValue("file.minio.bucket");
            minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            bucketExists();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void bucketExists() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, InvalidObjectPrefixException
    {
        if (!minioClient.bucketExists(BUCKET_NAME))
        {
            minioClient.makeBucket(BUCKET_NAME);
            minioClient.setBucketPolicy(BUCKET_NAME, "*", PolicyType.READ_WRITE);
        }
    }

    @Override
    public String upload(byte[] bytes, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException
    {
        if (StringUtils.isNull(bytes))
        {
            return "";
        }
        return upload(new ByteArrayInputStream(bytes), objectName);
    }

    @Override
    public String upload(String base64, String name) throws Exception
    {
        byte[] bytes = base64ToByte(base64, true);
        return upload(bytes, name);
    }

    @Override
    public InputStream download(String fileName) throws Exception
    {
        return minioClient.getObject(BUCKET_NAME, getBucketName(fileName));
    }

    @Override
    public String upload(MultipartFile file, String name) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException
    {
        return upload(file.getInputStream(), name);
    }

    @Override
    public String upload(MultipartFile file) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException
    {
        String objectName = file.getOriginalFilename();

        String suffix = objectName.substring(objectName.lastIndexOf("."));
        objectName = String.valueOf(System.currentTimeMillis()).concat(suffix);

        return upload(file.getInputStream(), objectName);
    }

    @Override
    public String upload(InputStream inputStream, String name) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException
    {
        if (StringUtils.isNull(inputStream))
        {
            return "";
        }
        minioClient.putObject(BUCKET_NAME, name, inputStream, "");
        return getHttpUrl(name);
    }

    @Override
    public void delete(String name) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, ServiceException
    {

        minioClient.removeObject(BUCKET_NAME, getBucketName(name));
    }

    private String getBucketName(String name)
    {
        if (StringUtils.isNotBlank(name))
        {
            int i = name.lastIndexOf("/");
            if (i > 0)
            {
                name = name.substring(i + 1);
            }
        }
        return name;
    }


    private String getHttpUrl(String objectName)
    {
        return ENDPOINT.concat("/").concat(BUCKET_NAME).concat("/").concat(objectName);
    }
}
