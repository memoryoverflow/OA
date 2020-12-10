package com.yj.oa.common.utils;

import com.yj.oa.project.po.Attend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 永健
 * 日期工具类
 */
public class DateUtils{

    private static Logger log = LoggerFactory.getLogger(DateUtils.class);
    private static Calendar calendar = Calendar.getInstance();


    /**
     *
     * @描述 date to str
     *
     * @date 2018/9/15 20:49
     */
    public static String DateToSTr(Date date)
    {
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = aDate.format(date);
        return format;
    }


    /**
     * 转换为年月日字符窜
     */
    public static String DateToSTr2(Date date)
    {
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd");
        String format = aDate.format(date);
        return format;
    }


    /**
     *
     * @描述: 字符窜转日期
     *
     * @params:
     * @return:
     * @date: 2018/9/29 11:28
     */
    public static Date StrToDate(String date)
    {
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try
        {
            parse = aDate.parse(date);
        }
        catch (ParseException e)
        {
            log.error("日期格式化出错=[{}]", date);
        }
        return parse;
    }


    /**
     * 获取上一个月
     *
     * @param date 当前日期的上一个月
     */
    public static Date getPreMoth(Date date)
    {
        //获取上个月日期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date pre = c.getTime();
        return pre;
    }


    /**
     * 获取周几
     */
    public static String getTodayWeek()
    {
        //获取当前时间
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (week < 0)
        {
            week = 7;
        }
        return weekToStr(week);
    }


    public static String weekToStr(int week)
    {
        String w = "";
        switch (week)
        {
            case 7:
                w = "日";
                break;
            case 6:
                w = "六";
                break;
            case 5:
                w = "五";
                break;
            case 4:
                w = "四";
                break;
            case 3:
                w = "三";
                break;
            case 2:
                w = "二";
                break;
            case 1:
                w = "一";
                break;
            default:
                w = "";
                break;
        }
        return w;
    }


    /**
     * 获取 时 分 秒 字符窜
     */
    public static String getTimeShort(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString.equals("00:00:00") ? "12:00:00" : dateString;
    }


    /**
     * 计算时间戳的时间差
     *
     * @param start 减数
     * @param end 被减数
     *
     * @return 分钟
     */
    public static long getTimeRang(long start, long end)
    {

        long l = (end - start) / (1000 * 60);
        return l;
    }


    /**
     * 获取当前月的天数
     */
    public static int getDayOfMonth(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.getActualMaximum(Calendar.DATE);
        return day;
    }


    /**
     * 获取个月的第一天
     */
    public static Date getFirstDayDateOfMonth(final Date date)
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }


    /**
     * 获取月的最后一天
     */
    public static Date getLastDayOfMonth(final Date date)
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }


    /**
     *  获取小时
     * @param date
     * @return
     */
    public static int getHours(Date date)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 获取分钟
     * @param date
     * @return
     */
    public static int getMinute(Date date)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     * @param date
     * @return
     */
    public static int getSecend(Date date)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }
}
