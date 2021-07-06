package cn.learncoding.jackson.serializer.expand;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 脱敏类型处理
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DataMaskingJsonSerializer.class)
public @interface Masking {
    /**
     * 常见脱敏类型
     */
    MaskingType type() default MaskingType.CUSTOMER;

    /**
     * 定制化处理
     */
    Class<? extends DataMaskingSerializer> customer() default DataMaskingSerializer.class;

    /**
     * 开始下标（包含）
     */
    int beginIndex() default -1;

    /**
     * 截止下标（不包含）
     *
     * 0 ：表示结尾，即全部替换；
     * 负数：表示从结尾倒数；
     */
    int endIndex() default 0;

    /**
     * 替换字符
     */
    char replaceChar() default '*';

}
