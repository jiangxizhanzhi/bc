package com.cyberpeace.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import com.cyberpeace.common.constant.CommonConstant;

/**
 * 数学工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class MathUtils {

	private static final long serialVersionUID = -3345205828566485102L;

	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;

	private static char[] codes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 返回随机字符串，同时包含数字、大写字母
	 * @param len 字符串长度
	 * @return String 随机字符串
	 */
	public static String randomStr(int len) {
		if (len == CommonConstant.ZERO) {
			return "";
		}
		// 数组，用于存放随机字符
		char[] chArr = new char[len];
		Random random = new Random();

		for (int i = 0; i < len; i++) {
			chArr[i] = codes[random.nextInt(codes.length)];
		}

		// 将数组chArr随机排序
		for (int i = 0; i < len; i++) {
			int r = i + random.nextInt(len - i);
			char temp = chArr[i];
			chArr[i] = chArr[r];
			chArr[r] = temp;
		}

		return new String(chArr);
	}

	/**
	 * 提供精确的加法运算。
	 *
	 * @param value1 被加数
	 * @param value2 加数
	 * @return 两个参数的和
	 */
	public static Double add(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param value1 被减数
	 * @param value2 减数
	 * @return 两个参数的差
	 */
	public static double sub(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param value1 被乘数
	 * @param value2 乘数
	 * @return 两个参数的积
	 */
	public static Double mul(Double value1, Double value2) {

		BigDecimal b1 = new BigDecimal(Double.toString(DataTransferUtils.nullToZero(value1)));
		BigDecimal b2 = new BigDecimal(Double.toString(DataTransferUtils.nullToZero(value2)));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 *
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor) {
		return divide(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 *
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @param scale    表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (null == divisor || divisor.doubleValue() == new Double(0).doubleValue()) {
			return new Double(0);
		}
		BigDecimal b1 = new BigDecimal(Double.toString(DataTransferUtils.nullToZero(dividend)));
		BigDecimal b2 = new BigDecimal(Double.toString(DataTransferUtils.nullToZero(divisor)));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * double向上取整除法
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static Integer ceilDivide(Double dividend, Double divisor) {
		if (null == divisor || divisor.doubleValue() == new Double(0).doubleValue()) {
			return 0;
		}
		double result = divide(dividend, divisor, DEF_DIV_SCALE);
		return MathUtils.ceil(result);
	}

	/**
	 * 向上取整除法
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static Integer divide(Integer dividend, Integer divisor) {
		if (null == divisor || divisor.intValue() == 0) {
			return 0;
		}
		int ret = dividend / divisor;
		int mod = dividend % divisor;

		if (mod != 0) {
			ret++;
		}

		if (ret > 100) {
			ret = 100;
		}

		if (ret < 0) {
			ret = 0;
		}

		return ret;
	}

	/**
	 * 提供指定数值的（精确）小数位四舍五入处理。
	 *
	 * @param value 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 生成随机int值
	 * @param max 最大
	 * @return
	 */
	public static int randomInt(Integer max) {
		return (int) (max * Math.random());
	}

	/**
	 * 获取0-max之间的随机数
	 * @param max
	 * @return
	 */
	public static int randomRange(int max) {
		return new Random().nextInt(max);
	}

	/**
	 * double向上取整
	 * @param num
	 * @return
	 */
	public static int ceil(Double num) {
		double ceil = Math.ceil(num);
		return Double.valueOf(ceil).intValue();
	}

	/**
	 * 正整数， 四舍五入除法
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static Integer roundDivide(Integer dividend, Integer divisor) {
		if (null == divisor || divisor == 0) {
			return 0;
		}
		int ret = dividend / divisor;
		int mod = dividend % divisor;

		if (mod * 2 >= divisor) {
			ret++;
		}

		return ret;
	}

}
