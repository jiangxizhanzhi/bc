package com.cyberpeace.common.mdparser.convert;

/**
 * 课程类型 转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum LessonTypeConvert implements MdConvertEnum<String, Integer> {

    /**
     * 课时类型
     *
     * @see LessonTypeConvert
     */
    THEORY("理论课", 0),
    /**
     * 实操
     *
     * @see LessonTypeConvert
     */
    OPERATION("实验课", 1);

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 自
     *
     * @return {@code String }
     */
    @Override
    public String to() {
        return name;
    }

    /**
     * re 更改为
     *
     * @return {@code Integer }
     */
    @Override
    public Integer reTo() {
        return type;
    }

    /**
     * 课程类型 转换
     *
     * @param name 名称
     * @param type 类型
     */
    LessonTypeConvert(String name, Integer type) {
        this.name = name;
        this.type = type;
    }
}
