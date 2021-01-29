package cn.yj.common.upload;

import cn.yj.commons.utils.StringUtils;
import cn.yj.tools.exception.ServiceException;
import cn.yj.tools.readconfig.PropertiesUtils;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-09-03 11:31
 */
public class FileUploadHandler
{
    private static String fileImgUploadType;
    private static FileUpload FILEUPLOAD;

    private static final String MINIO = "MINIO";
    private static final String FASTDFS = "FASTDFS";
    private static final String LOCAL = "LOCAL";

    static
    {
        fileImgUploadType = PropertiesUtils.getStringValue("file.upload-type", LOCAL);
    }

    public static FileUpload getInstant()
    {
        if (StringUtils.isNotNull(FILEUPLOAD))
        {
            return FILEUPLOAD;
        }

        if (fileImgUploadType.equals(LOCAL))
        {
            FILEUPLOAD = new LocalUpload();
        }
        else if (fileImgUploadType.equals(MINIO))
        {
            FILEUPLOAD = new MinioUpload();
        }
        else
        {
            FILEUPLOAD = new FastDFSUpload();
            throw new ServiceException("该实现为处理");
        }

        return FILEUPLOAD;

    }
}
