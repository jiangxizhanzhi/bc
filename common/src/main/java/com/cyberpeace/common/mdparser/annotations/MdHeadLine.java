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
/**
 * 这段代码定义了一个自定义注解 `MdHeadLine`，主要用于标记需要解析的 Markdown 标题字段。以下是代码的简要分析： 
 * 1. **注解的基本信息**： 
 * - 注解的目标是类的字段（`@Target(ElementType.FIELD)`）。 
 * - 注解在运行时可用（`@Retention(RetentionPolicy.RUNTIME)`）。 
 * - 支持继承（`@Inherited`）。 
 * 2. **注解的属性**： 
 * - `level()`：表示标题的等级，如 `#` 或 `##`，默认值为 `-1`。 
 * - `name()`：指定要匹配的标题名称，默认为空字符串。 
 * - `parser()`：指定用于解析内容的解析器类，默认为 `StringParser`。 
 * - `convert()`：指定用于转换值的转换器类，默认为 `NoConvert`。 
 * 3. **用途**： 
 * - 这个注解可以用于标识类中的字段，以便在运行时根据注解的属性对 Markdown 内容进行解析和处理。 
 * 总结：这是一个用于解析 Markdown 标题的自定义注解，提供了标题等级、名称、解析器和转换器等配置选项。 
**/
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

