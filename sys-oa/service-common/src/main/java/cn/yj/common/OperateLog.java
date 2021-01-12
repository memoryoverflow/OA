package cn.yj.common;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-12-17 17:03
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog
{
    /**
     * 接口功能描述
     */
    String describe() default "";

}
