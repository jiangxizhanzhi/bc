/**
 * 这段代码定义了一个名为 `IndexActionFrameChart` 的 Java 类，位于包 `com.cyberpeace.space.service.context.index` 中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类用于表示“编号行动框架图表”，主要用于存储与时间序列相关的数据。 
 * - 它包含了三个主要属性：`xDates`（日期列表）、`process`（过程列表）和 `target`（目标列表）。 
 * 2. **注解和功能**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，减少了冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 `process` 字段添加了描述信息，便于 API 文档生成。 
 * 3. **数据结构**： 
 * - `xDates`：存储日期的字符串列表，初始化时使用了 Apache Commons Compress 提供的 `Lists.newArrayList()` 方法。 
 * - `process` 和 `target`：分别存储整数列表，用于表示“面向攻击流程”和“面向攻击目标”。 
 * 4. **依赖库**： 
 * - 使用了 Apache Commons Compress 库中的 `Lists.newArrayList()` 方法来初始化列表。 
 * - 使用了 Lombok 和 Swagger 注解库。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装与时间序列相关的数据，方便在系统中传递和展示。 
**/
package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 编号行动框架图表
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexActionFrameChart {

	/**
	 * x 日期
	 *
	 * @see List<String>
	 */
	private List<String> xDates = Lists.newArrayList();

	/**
	 * 过程
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "面向攻击流程")
	private List<Integer> process = Lists.newArrayList();

	/**
	 * 目标
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "面向攻击目标")
	private List<Integer> target = Lists.newArrayList();

}
