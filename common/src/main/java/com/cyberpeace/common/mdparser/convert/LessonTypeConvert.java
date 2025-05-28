/**
 * ### 代码分析 
 * #### 概述 
 * 该代码定义了一个枚举类 `LessonTypeConvert`，用于表示课程类型的转换。它实现了接口 `MdConvertEnum<String, Integer>`，并提供了课程名称和类型的映射关系。 
 * #### 主要功能 
 * 1. **枚举常量**： 
 * - 定义了两种课程类型：`THEORY`（理论课）和 `OPERATION`（实验课）。 
 * - 每个枚举常量包含一个名称（`String`）和一个类型值（`Integer`）。 
 * 2. **接口实现**： 
 * - 实现了 `MdConvertEnum<String, Integer>` 接口，定义了两个方法： 
 * - `to()`：返回课程名称（`String`）。 
 * - `reTo()`：返回课程类型值（`Integer`）。 
 * 3. **构造函数**： 
 * - 枚举的构造函数接受名称和类型值作为参数，并初始化对应的字段。 
 * 4. **字段说明**： 
 * - `name`：课程的名称。 
 * - `type`：课程的类型值。 
 * #### 使用场景 
 * - 该枚举类可以用于课程类型的转换，例如在系统中将课程名称转换为类型值，或者将类型值转换为名称。 
 * #### 总结 
 * 该代码通过枚举类实现了课程类型的规范化管理，提供了清晰的名称和类型映射，便于在系统中进行课程类型的转换操作。 
**/
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
