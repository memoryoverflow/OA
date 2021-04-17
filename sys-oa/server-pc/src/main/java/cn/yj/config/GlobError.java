package cn.yj.config;

import cn.yj.entity.R;
import cn.yj.params.check.ParamsIsNullException;
import cn.yj.tools.exception.GlobalExceptionHandler;
import org.apache.shiro.authz.UnauthorizedException;
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
public class GlobError extends GlobalExceptionHandler{
    @ExceptionHandler(UnauthorizedException.class)
    public R handleException(UnauthorizedException e) {
        e.printStackTrace();
        return R.error("权限不足");
    }

    @ExceptionHandler({ParamsIsNullException.class})
    public R handleRRException(ParamsIsNullException e) {
        return R.error(String.format("缺少参数：%s", e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return R.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R IllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return R.error("请求参数有误");
    }
}
