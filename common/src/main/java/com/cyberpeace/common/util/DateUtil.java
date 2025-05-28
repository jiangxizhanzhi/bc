/**
 * 这段代码定义了一个工具类 `DateUtil`，用于将 `Date` 对象格式化为字符串。以下是代码的主要功能和结构分析： 
 * 1. **类与常量**： 
 * - 类名为 `DateUtil`，是一个公共工具类。 
 * - 定义了一个公共常量 `SHOT_PATTERN`，其值为 `"yyyy-MM-dd"`，表示短日期格式。 
 * 2. **方法 `DateToString`**： 
 * - 功能：将 `Date` 对象转换为指定格式的字符串。 
 * - 参数： 
 * - `date`：需要格式化的 `Date` 对象，若为 `null`，返回空字符串 `""`。 
 * - `pattern`：日期格式字符串，默认值为 `"yyyy-MM-dd HH:mm:ss"`。 
 * - 实现逻辑： 
 * - 如果 `pattern` 为空或空白（通过 `StringUtils.isBlank` 判断），则使用默认格式 `"yyyy-MM-dd HH:mm:ss"`。 
 * - 使用 `SimpleDateFormat` 根据指定格式格式化日期，并返回结果字符串。 
 * 3. **依赖库**： 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 工具类来判断字符串是否为空或空白。 
 * - 使用了 `java.text.SimpleDateFormat` 进行日期格式化。 
 * 4. **优点**： 
 * - 提供了灵活的日期格式化功能，支持自定义格式。 
 * - 默认格式考虑了常见的日期时间格式。 
 * 5. **改进建议**： 
 * - `SimpleDateFormat` 不是线程安全的，在多线程环境中可能需要改进，例如使用 `ThreadLocal` 或 `DateTimeFormatter`（Java 8+）。 
 * - 可以增加异常处理，例如捕获 `IllegalArgumentException` 并提供友好的错误提示。 
 * 总结：这是一个简单的日期格式化工具类，适用于基本的日期字符串转换需求。 
**/
package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String SHOT_PATTERN = "yyyy-MM-dd";

	public static String DateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
}
