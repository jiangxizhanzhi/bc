/**
 * 这段代码定义了一个名为 `IndexTool` 的 Java 类，位于包 `com.cyberpeace.space.service.context.index` 下。以下是对其功能和结构的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储与编号工具相关的各种统计数据。 
 * - 包含多个字段，分别表示不同类型的工具数量（如探针、攻击、控制、防御、其他）以及总数和平台统计信息。 
 * 2. **字段说明**： 
 * - `probe`, `attack`, `control`, `defense`, `other`：分别表示探针、攻击、控制、防御和其他类型工具的数量，初始值为 0。 
 * - `sum`：表示所有工具类型的总数量，初始值为 0。 
 * - `platform`：一个 `Map<String, Integer>` 类型的字段，用于存储不同平台上的工具数量统计。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于 API 文档生成。 
 * 4. **设计特点**： 
 * - 该类是一个简单的数据传输对象（DTO），主要用于封装和传递相关数据。 
 * - 字段命名清晰，注释详细，便于理解其用途。 
 * 总结：`IndexTool` 是一个用于存储和管理编号工具相关统计数据的数据模型类，适合在需要统计不同类型工具数量的场景中使用。 
**/
package com.cyberpeace.space.service.context.index;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTool {

	/**
	 * 探针
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "探测类工具")
	private Long probe = 0L;

	/**
	 * 攻击
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "攻击类工具")
	private Long attack = 0L;

	/**
	 * 控制
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "控制类工具")
	private Long control = 0L;

	/**
	 * 防御
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "防御类工具")
	private Long defense = 0L;

	/**
	 * 其他
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "其他类工具")
	private Long other = 0L;


	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 平台
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty(value = "平台统计")
	private Map<String, Integer> platform;
}
