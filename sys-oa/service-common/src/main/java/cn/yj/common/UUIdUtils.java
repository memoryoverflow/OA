package cn.yj.common;

import java.util.UUID;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 15:20
 */
public class UUIdUtils
{
    public static String getUUId32(){
        return UUID.randomUUID().toString();
    }
}
