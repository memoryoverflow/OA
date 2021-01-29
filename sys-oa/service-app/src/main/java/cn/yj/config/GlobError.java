package cn.yj.config;

import cn.yj.entity.R;
import cn.yj.tools.exception.GlobalExceptionHandler;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 18:07
 */
@Configuration
@RestControllerAdvice
public class GlobError extends GlobalExceptionHandler
{
    @ExceptionHandler(UnauthorizedException.class)
    public R handleException(UnauthorizedException e)
    {
        e.printStackTrace();
        return R.error("权限不足");
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R maxUploadSizeExceededException(MaxUploadSizeExceededException e)
    {
        e.printStackTrace();
        return R.error(e.getLocalizedMessage());
    }
}
