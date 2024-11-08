package com.cyberpeace.common.mdparser.convert;

/**
 * md convert 枚举
 *
 * @author SN
 * @date 2024/11/08
 */
public interface MdConvertEnum<K, V> {

    /**
     * 自
     *
     * @return {@code K }
     */
    K to();

    /**
     * re 更改为
     *
     * @return {@code V }
     */
    V reTo();

}
