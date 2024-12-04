package com.cyberpeace.common.mdparser.annotations;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.mdparser.parser.StringParser;

import java.lang.annotation.*;

/**
 * md 头线
 *
 * @author chenzhuo@date 2024/11/08@see Annotation
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MdHeadLine {

    /**
     * 标题等级 1=# 2=##
     *
     * @return
     */
    int level() default -1;

    /**
     * 要匹配的标题名称
     *
     * @return
     */
    String name() default "";

    /**
     * 使用哪种格式化
     *
     * @return
     */
    Class<? extends MdContentParser> parser() default StringParser.class;

    /**
     * 转换器 将获取到的值 通过枚举进行转换
     *
     * @return
     */
    Class<? extends MdConvertEnum> convert() default NoConvert.class;

}
