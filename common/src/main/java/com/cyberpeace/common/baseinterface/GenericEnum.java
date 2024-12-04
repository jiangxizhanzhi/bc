package com.cyberpeace.common.baseinterface;

/**
 * 枚举实现此接口 可使用EnumUtils获取枚举相关信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
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
