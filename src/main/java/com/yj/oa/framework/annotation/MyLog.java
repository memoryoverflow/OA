package com.yj.oa.framework.annotation;

import java.lang.annotation.*;

/**
 * @author 永健
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog{
    /**
     * 描述业务操作
     */
    String modal() default "";

    /**
     * 操作类型
     */
    String descr() default "";

}
