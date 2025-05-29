package com.cyberpeace.common.util;

/**
 * 数据传输实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个工具类 `DataTransferUtils`，用于处理数据传输中的一些常见问题，比如判断对象是否为 `null` 并赋默认值。以下是代码的简要分析： 
 * ### 主要功能： 
 * 1. **`nullToZero(Integer num)` 方法**： 
 * - 判断传入的整数对象是否为 `null`。 
 * - 如果是 `null`，将其赋值为 `0`。 
 * - 返回处理后的值。 
 * 2. **`nullToZero(Double num)` 方法**： 
 * - 判断传入的浮点数对象是否为 `null`。 
 * - 如果是 `null`，创建一个新的 `Double` 对象并赋值为 `0`。 
 * - 返回处理后的值。 
 * ### 优点： 
 * - 提供了针对基本类型的包装类（`Integer` 和 `Double`）的空值处理逻辑。 
 * - 使用静态方法，便于直接调用，无需实例化类。 
 * ### 改进建议： 
 * 1. **避免显式创建 `Double` 对象**： 
 * - Java 中推荐使用 `Double.valueOf(0)` 或直接返回 `0.0`，而不是 `new Double(0)`，因为后者已被标记为过时。 
 * 2. **方法签名一致性**： 
 * - 当前两个方法的返回类型不同（`Integer` 和 `Double`），虽然符合需求，但可能会导致调用方混淆。如果需要统一接口，可以考虑泛型实现。 
 * 3. **异常处理**： 
 * - 如果输入参数可能不符合预期（如非法值），可以增加额外的检查或抛出自定义异常。 
 * ### 总结： 
 * 该工具类提供了简单的空值处理逻辑，适合在数据传输场景中快速处理常见的 `null` 值问题。通过改进方法签名和返回值的处理方式，可以进一步提升代码的可读性和安全性。 
**/
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

