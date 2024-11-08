package com.cyberpeace.common.util;

/**
 * 数据传输实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
public class DataTransferUtils {

	/**
	 * 判断整数对象是否为空 并进行赋值
	 *
	 * @param num
	 * @return
	 */
	public static Integer nullToZero(Integer num) {
		if (null == num) {
			num = 0;
		}
		return num;
	}

	/**
	 * 判断 浮点数对象是否为空 并进行赋值
	 *
	 * @param num
	 * @return
	 */
	public static Double nullToZero(Double num) {
		if (null == num) {
			num = new Double(0);
		}
		return num;
	}

}
