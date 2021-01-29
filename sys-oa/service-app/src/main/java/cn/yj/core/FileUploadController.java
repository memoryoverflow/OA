package cn.yj.core;

import cn.yj.common.upload.FileUploadHandler;
import cn.yj.commons.utils.StringUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.ServiceException;
import io.minio.errors.*;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 14:53
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController
{
}
