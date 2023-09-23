package com.cyberpeace.common.util;

public class DataTransferUtils {

	/**
	 * 判断整数对象是否为空 并进行赋值
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
	 * @param num
	 * @return
	 */
	public static Double nullToZero(Double num) {
		if (null == num) {
			num = (double) 0;
		}
		return num;
	}

	private DataTransferUtils() {}

}
