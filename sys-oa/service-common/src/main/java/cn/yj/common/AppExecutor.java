package cn.yj.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-09-12 21:08
 */
public class AppExecutor
{
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 50,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10000));

    public static void exec(Runnable runnable)
    {
        threadPoolExecutor.execute(runnable);
    }
}
