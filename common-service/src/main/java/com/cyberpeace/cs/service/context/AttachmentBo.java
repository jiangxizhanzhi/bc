package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件 bo
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `AttachmentBo` 的 Java 类，用于表示附件的相关信息。以下是代码的主要特点和功能分析： 
 * ### 主要特性： 
 * 1. **注解说明**： 
 * - 使用了 Lombok 提供的注解简化代码： 
 * - `@Data`：自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成全参构造函数和无参构造函数。 
 * - `@Builder`：提供基于 Builder 模式的对象创建方式。 
 * - 使用了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty` 来描述类和字段的用途，便于 API 文档生成。 
 * 2. **字段说明**： 
 * - `resourceId`：资源编号，用于唯一标识附件对应的资源。 
 * - `fileName`：附件的文件名称。 
 * - `filePath`：附件的存储路径。 
 * - `isPublic`：布尔值，表示附件是否公开（`true` 表示公开，`false` 表示隐藏）。 
 * 3. **接口实现**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于支持对象在网络中传输或持久化存储。 
 * ### 总结： 
 * - 这是一个简单的数据传输对象（DTO），主要用于封装附件的相关信息。 
 * - 通过 Lombok 注解减少了样板代码，提高了开发效率。 
 * - 使用 Swagger 注解增强了代码的可读性和文档生成能力。 
**/
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 文件路径
	 */
	@ApiModelProperty("文件路径")
	private String filePath;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("附件公开与隐藏状态 true=公开 false=隐藏")
	private Boolean isPublic;

}

