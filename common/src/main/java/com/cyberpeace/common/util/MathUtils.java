package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * math utils
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 分析与总结 
 * #### 模块功能 
 * 该类 `MathUtils` 是一个数学工具类，提供了一系列常用数学运算方法，包括随机字符串生成、数值计算（加、减、乘、除等）、取整操作、以及四舍五入等。代码结构清晰，功能模块化，适合复用。 
 * --- 
 * #### 主要功能模块 
 * 1. **随机字符串生成** 
 * - 方法：`randomStr(int len)` 
 * - 功能：生成指定长度的随机字符串，包含数字和大写字母。 
 * - 实现细节： 
 * - 使用 `char[]` 数组存储生成的字符。 
 * - 利用 `Random` 类生成随机索引填充数组。 
 * - 对生成的数组进行随机排序以增加随机性。 
 * 2. **数值计算** 
 * - **加法**：`add(Double value1, Double value2)` 
 * - **减法**：`sub(Double value1, Double value2)` 
 * - **乘法**：`mul(Double value1, Double value2)` 
 * - **除法**： 
 * - `divide(Double dividend, Double divisor)`：默认精度为 2 位。 
 * - `divide(Double dividend, Double divisor, Integer scale)`：支持自定义精度。 
 * - **向上取整除法**：`ceilDivide(Double dividend, Double divisor)` 
 * - **正整数四舍五入除法**：`roundDivide(Integer dividend, Integer divisor)` 
 * 实现细节： 
 * - 所有数值计算均使用 `BigDecimal` 进行高精度运算，避免浮点数精度问题。 
 * - 支持四舍五入、向上取整等操作，满足不同场景需求。 
 * 3. **取整操作** 
 * - 方法：`ceil(Double num)` 
 * - 功能：对输入数值进行向上取整。 
 * - 实现细节：调用 `Math.ceil()` 方法并转换为整型。 
 * 4. **随机数生成** 
 * - 方法：`randomInt(Integer max)` 和 `randomRange(int max)` 
 * - 功能： 
 * - `randomInt(Integer max)`：生成 `[0, max)` 区间内的随机整数。 
 * - `randomRange(int max)`：生成 `[0, max)` 区间的随机整数。 
 * - 实现细节：利用 `Math.random()` 或 `Random` 类生成随机数。 
 * 5. **四舍五入** 
 * - 方法：`round(double value, int scale)` 
 * - 功能：对数值按指定的小数位进行四舍五入。 
 * - 实现细节：使用 `BigDecimal` 的 `divide()` 方法并指定舍入模式。 
 * --- 
 * #### 优点 
 * 1. **高精度计算**：通过 `BigDecimal` 避免了浮点数精度问题。 
 * 2. **模块化设计**：每个功能独立封装为静态方法，便于调用和维护。 
 * 3. **扩展性强**：如除法支持自定义精度，可以灵活适应多种业务场景。 
 * 4. **鲁棒性**：对空值或非法输入（如除数为零）进行了合理处理。 
 * --- 
 * #### 可改进点 
 * 1. **异常处理**：部分方法未详细记录异常信息，如 `IllegalArgumentException` 的具体含义可进一步说明。 
 * 2. **注释完善**：部分方法注释不够详尽，例如 `randomStr(int len)` 中的排序逻辑未充分解释。 
 * 3. **线程安全性**：`Random` 类是线程不安全的，若在多线程环境中使用需改为线程安全的实现（如 `ThreadLocalRandom`）。 
 * --- 
 * #### 总结 
 * 该工具类覆盖了常见的数学运算需求，代码逻辑清晰，功能丰富且实用性强。通过结合 `BigDecimal` 和 `Random`，实现了高精度计算和随机数生成，适合作为项目中的通用数学工具类。 
**/
public class MathUtils {

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = -3345205828566485102L;

	/**
	 * def div scale （定义 div 缩放）
	 */// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;

	/**
	 * 代码
	 */
	private static char[] codes = { '0','1','2','3','4','5','6','7','8','9',
			'A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N','O','P','Q','R','S','T',
			'U','V','W','X','Y','Z'};

	private static final Random random = new Random();

	/**
	 * 返回随机字符串，同时包含数字、大写字母
	 *
	 * @param len 字符串长度
	 * @return String 随机字符串
	 */
	public static String randomStr(int len){
		if(len == CommonConstant.ZERO){
			return "";
		}
		//数组，用于存放随机字符
		char[] chArr = new char[len];

		for(int i = 0; i < len; i++){
			chArr[i] = codes[random.nextInt(codes.length)];
		}

		//将数组chArr随机排序
		for(int i = 0; i < len; i++){
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
	 *
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
	 *
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
	 *
	 * @param max 最大
	 * @return
	 */
	public static int randomInt(Integer max) {
		return (int) (max * Math.random());
	}

	/**
	 * 获取0-max之间的随机数
	 *
	 * @param max
	 * @return
	 */
	public static int randomRange(int max) {
		return random.nextInt(max);
	}

	/**
	 * double向上取整
	 *
	 * @param num
	 * @return
	 */
	public static int ceil(Double num) {
		double ceil = Math.ceil(num);
		return Double.valueOf(ceil).intValue();
	}

	/**
	 * 正整数， 四舍五入除法
	 *
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

