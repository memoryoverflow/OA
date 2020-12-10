package com.yj.oa.common.conf;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 永健
 * 处理前端字符窜格式日期转换
 */
@Component
public class StringDateToDateConfig implements Converter<String, Date>{
    private static final List<String> formarts = new ArrayList<>(4);

    static
    {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Date convert(String source)
    {
        String value = source.trim();
        if ("".equals(value))
        {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$"))
        {
            return parseDate(source, formarts.get(0));
        }
        else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
        {
            return parseDate(source, formarts.get(1));
        }
        else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$"))
        {
            return parseDate(source, formarts.get(2));
        }
        else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$"))
        {
            return parseDate(source, formarts.get(3));
        }
        else
        {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format String 格式
     *
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format)
    {
        Date date = null;
        try
        {

            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        }
        catch (Exception e)
        {

        }
        return date;
    }

}
