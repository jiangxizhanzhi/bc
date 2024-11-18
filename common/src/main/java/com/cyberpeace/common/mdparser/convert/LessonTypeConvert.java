package com.cyberpeace.common.mdparser.convert;

/**
 * 课程类型 转换
 *
 * @author SN
 * @version 1.0.0
 * @see Enum
 * @see Enum
 * @since 1.0.0
 * @date 2024/11/07
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
     * @throws
     * @see String
     */
    @Override
    public String to() {
        return name;
    }

    /**
     * re 更改为
     *
     * @return {@code Integer }
     * @throws
     * @see Integer
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
     * @throws
     * @see String
     * @see Integer
     */
    LessonTypeConvert(String name, Integer type) {
        this.name = name;
        this.type = type;
    }
}
