package net.leeautumn.common.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2015/5/13 0013.
 */
public class DateJsonValueProcessorUtil implements JsonValueProcessor {
    private String format;
    public DateJsonValueProcessorUtil(String format){
        this.format = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig)
    {
        return null;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
    {
        if(value == null)
        {
            return "";
        }
        if(value instanceof java.sql.Timestamp)
        {
            String str = new SimpleDateFormat(format).format((java.sql.Timestamp)value);
            return str;
        }
        if (value instanceof java.util.Date)
        {
            String str = new SimpleDateFormat(format).format((java.util.Date) value);
            return str;
        }

        return value.toString();
    }
}
