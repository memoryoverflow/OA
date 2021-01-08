package cn.yj.common;

import cn.yj.commons.enums.LPATTERM;
import cn.yj.commons.utils.DateTimeUtils;
import cn.yj.commons.utils.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Date;

/**
 * <br>
 *
 * @RequestBody 日期参数绑定转换
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
public class StringToDateDeSerializer extends JsonDeserializer<Date>
{

    @SneakyThrows
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        if(StringUtils.isBlank(p.getText())){
            return null;
        }
        return DateTimeUtils.format(p.getText(), LPATTERM.YYMMDDMMSS);
    }
}
