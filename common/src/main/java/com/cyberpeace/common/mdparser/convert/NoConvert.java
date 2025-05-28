/**
 * ### 代码分析 
 * #### 功能描述 
 * 该代码定义了一个枚举类 `NoConvert`，实现了接口 `MdConvertEnum<String, String>`。其主要功能是表示“不进行任何转换”的操作，无论是从某种值转换为目标值，还是反向转换。 
 * #### 主要组成部分 
 * 1. **枚举定义**： 
 * - 枚举名为 `NoConvert`。 
 * - 包含一个静态实例 `NO_CONVERT`，表示“不转换”的操作。 
 * 2. **实现接口方法**： 
 * - 实现了接口中的两个抽象方法： 
 * - `to()`：返回空字符串（`CommonConstant.BLANK_STR`），表示正向转换时不改变原始值。 
 * - `reTo()`：同样返回空字符串，表示反向转换也不改变值。 
 * 3. **注释说明**： 
 * - 提供了详细的注释，明确指出该枚举的作用是“不转换”，并引用了相关的常量类 `CommonConstant` 和其他可能相关的类或接口。 
 * #### 使用场景 
 * - 该枚举适用于需要提供一种“无操作”转换的场景，例如在某些解析器或转换器中，当不需要对输入值进行处理时，可以使用此枚举。 
 * #### 总结 
 * 这是一个简单的枚举类，用于表示“不转换”的操作，通过实现接口的方法提供了统一的行为规范，适合在需要灵活扩展转换逻辑的系统中使用。 
**/
package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.constant.CommonConstant;

/**
 * 不转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum NoConvert implements MdConvertEnum<String, String> {
    /**
     * 不转换
     *
     * @see NoConvert
     */
    NO_CONVERT();

    /**
     * 自
     *
     * @return {@code String }
     */
    @Override
    public String to() {
        return CommonConstant.BLANK_STR;
    }

    /**
     * re 更改为
     *
     * @return {@code String }
     */
    @Override
    public String reTo() {
        return CommonConstant.BLANK_STR;
    }

}
