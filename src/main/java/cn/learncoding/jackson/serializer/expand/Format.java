package cn.learncoding.jackson.serializer.expand;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 数据序列化时进行格式化
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = FormatJsonSerializer.class)
public @interface Format {
    /**
     * 格式化模板，参见 {@link java.util.Formatter }
     *
     * 原数据使用第一个替换符进行格式化替换
     */
    String value() default "";
}
