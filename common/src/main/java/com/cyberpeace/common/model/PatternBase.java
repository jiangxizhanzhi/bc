package com.cyberpeace.common.model;

import com.cyberpeace.common.util.LocalStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 图案基底
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `PatternBase` 的 Java 类，用于表示图案基底。以下是代码的主要分析和总结： 
 * ### 主要功能： 
 * 1. **类的功能**： 
 * - 该类是一个数据模型类，主要用于存储和操作与图案相关的模式信息。 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * 2. **成员变量**： 
 * - `pattern`：表示图案的模式，通过 `@ApiModelProperty` 注解说明这是一个模糊查询的筛选参数。 
 * 3. **方法**： 
 * - `setPattern(String pattern)`：自定义的 setter 方法，用于设置 `pattern` 字段。 
 * - 如果传入的 `pattern` 不为空或空白字符串，则调用 `LocalStringUtil.transfer()` 方法对其进行处理（可能是格式化或清理操作）。 
 * - 最终将处理后的值赋给 `this.pattern`。 
 * 4. **依赖项**： 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 工具类来判断字符串是否为空或空白。 
 * - 使用了 `LocalStringUtil` 工具类来对字符串进行自定义处理。 
 * ### 总结： 
 * - 该类的核心功能是管理图案的模式字段，并提供一个自定义的 setter 方法来确保字段在设置时经过必要的验证和处理。 
 * - 使用了 Lombok 和 Swagger 注解，提高了代码的简洁性和可读性。 
**/
@ApiModel
@Data
public class PatternBase implements Serializable {

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "模糊查询筛选参数")
	private String pattern;

	public void setPattern(String pattern) {
		String newPattern = pattern;

		if (!StringUtils.isBlank(pattern)) {

			newPattern = LocalStringUtil.transfer(pattern);

		}

		this.pattern = newPattern;
	}
}

