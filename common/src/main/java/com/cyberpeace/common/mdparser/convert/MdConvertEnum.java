package com.cyberpeace.common.mdparser.convert;

/**
 * md枚举转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface MdConvertEnum<K, V> {

	K to();

	V reTo();

}
