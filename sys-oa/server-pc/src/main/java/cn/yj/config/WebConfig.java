package cn.yj.config;

import cn.yj.commons.enums.LPATTERM;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer
{
    private static final List<String> formarts = new ArrayList<>(4);


    @Autowired
    RequestMappingHandlerAdapter handlerAdapter;


    @PostConstruct
    public void initEditableAvlidation()
    {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null)
        {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new GlobalDateConvert());
        }
    }


    static
    {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * <br>
     * 配置日期转化器
     */
    class GlobalDateConvert implements Converter<String, Date>
    {


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
                return parseDate(source, WebConfig.formarts.get(0));
            }
            else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
            {
                return parseDate(source, WebConfig.formarts.get(1));
            }
            else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$"))
            {
                return parseDate(source, WebConfig.formarts.get(2));
            }
            else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$"))
            {
                return parseDate(source, WebConfig.formarts.get(3));
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
         * @param format  String 格式
         * @return Date 日期
         */
        public Date parseDate(String dateStr, String format)
        {
            Date date = null;
            try
            {

                DateFormat dateFormat = new SimpleDateFormat(format);
                date = dateFormat.parse(dateStr);
            } catch (Exception e)
            {

            }
            return date;
        }
    }


    /**
     * 注入Bean : HttpMessageConverters，以支持fastjson
     * 返回前端时，替换掉属性为null的值
     *
     *
     * QuoteFieldNames 输出key时是否使用双引号,默认为true
     * UseSingleQuotes 使用单引号而不是双引号,默认为false
     * WriteMapNullValue   是否输出值为null的字段,默认为false
     * WriteEnumUsingToString  Enum输出name()或者original,默认为false
     * UseISO8601DateFormat    Date使用ISO8601格式输出，默认为false
     * WriteNullListAsEmpty    List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty  字符类型字段如果为null,输出为”“,而非null
     * WriteNullNumberAsZero   数值字段如果为null,输出为0,而非null
     * WriteNullBooleanAsFalse Boolean字段如果为null,输出为false,而非null
     * SkipTransientField  如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
     * SortField   按字段名称排序后输出。默认为false
     * WriteTabAsSpecial   把\t做转义输出，默认为false   不推荐
     * PrettyFormat    结果是否格式化,默认为false
     * WriteClassName  序列化时写入类型信息，默认为false。反序列化是需用到
     * DisableCircularReferenceDetect  消除对同一对象循环引用的问题，默认为false
     * WriteSlashAsSpecial 对斜杠’/’进行转义
     * BrowserCompatible   将中文都会序列化为XXXX格式，字节数会多一些，但是能兼容IE 6，默认为false
     * WriteDateUseDateFormat  全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
     * DisableCheckSpecialChar 一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false
     * NotWriteRootClassName   含义
     * BeanToArray 将对象转为array输出
     *
     */
    //@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCheckSpecialChar);
        fastJsonConfig.setDateFormat(LPATTERM.YYMMDDMMSS.getExpression());
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConvert.setSupportedMediaTypes(fastMediaTypes);
        fastConvert.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters((HttpMessageConverter<?>) fastConvert);
    }
}
