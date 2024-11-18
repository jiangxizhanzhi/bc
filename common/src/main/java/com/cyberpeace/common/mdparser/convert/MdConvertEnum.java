package com.cyberpeace.common.mdparser.convert;

/**
 * md convert 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface MdConvertEnum<K, V> {

    /**
     * 自
     *
     * @return {@code K }
     * @throws
     * @see K
     */
    K to();

    /**
     * re 更改为
     *
     * @return {@code V }
     * @throws
     * @see V
     */
    V reTo();

}
