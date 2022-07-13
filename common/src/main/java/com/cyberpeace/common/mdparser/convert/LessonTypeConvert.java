package com.cyberpeace.common.mdparser.convert;

public enum LessonTypeConvert implements MdConvertEnum<String, Integer> {

    /** 课时类型*/
    THEORY("理论课", 0),
    OPERATION("实验课", 1);

    private String name;
    
    private Integer type;
    
    @Override
    public String to() {
        return name;
    }

    @Override
    public Integer reTo() {
        return type;
    }
    
    LessonTypeConvert(String name, Integer type) {
        this.name = name;
        this.type = type;
    }
}
