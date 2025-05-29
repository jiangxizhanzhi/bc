package com.cyberpeace.common.baseinterface;

/**
 * 枚举实现此接口 可使用EnumUtils获取枚举相关信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `GenericEnum` 的 Java 接口。以下是简要分析： 
 * 1. **接口用途**： 
 * - 该接口旨在被枚举类实现，目的是提供通用的枚举属性获取方法。 
 * - 实现此接口的枚举可以通过 `EnumUtils` 工具类来获取相关枚举信息。 
 * 2. **方法定义**： 
 * - `getCode()`：返回一个整数值（`Integer`），通常用于表示枚举的唯一标识或编码。 
 * - `getDesc()`：返回字符串值（`String`），通常用于描述枚举的具体含义。 
 * 3. **设计意图**： 
 * - 提供了一种标准化的方式来访问枚举的代码和描述信息，便于在项目中统一处理枚举类型。 
 * - 结合 `EnumUtils` 工具类，可以方便地进行枚举的操作和查询。 
 * 4. **适用场景**： 
 * - 常用于需要动态操作枚举的场景，例如根据代码查找描述、反向解析等。 
 * 总结：这是一个简洁而通用的接口，为枚举提供了标准的代码和描述获取方法，适合在需要枚举管理的项目中使用。 
**/
public interface GenericEnum {

    /**
     * 获取代号
     *
     * @return {@code Integer }
     */
    Integer getCode();

    /**
     * 获取描述
     *
     * @return {@code String }
     */
    String getDesc();

}

