package com.minis.util;

public interface ObjectMapper {
    void setDateFormat(String dateFormat);
    void setDecimalFormat(String decimalFormat);
    /**将对象转成字符串*/
    String writeValuesAsString(Object obj);
}
