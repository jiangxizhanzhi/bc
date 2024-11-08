package com.cyberpeace.common.mdparser.convert;

/**
 * 课程类型 转换
 *
 * @author SN
 * @date 2024/11/08
 */
public enum LessonTypeConvert implements MdConvertEnum<String, Integer> {

    /** 课时类型*/
    THEORY("理论课", 0),
    OPERATION("实验课", 1);

    private String name;
    
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
