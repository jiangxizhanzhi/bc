package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.TrainingRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 在获取训练记录接口使用
 *
 * @author 张晋宝@date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TrainingRecordMissionRsp` 的 Java 类，用于表示从获取训练记录接口返回的数据结构。以下是简要分析： 
 * ### 主要功能 
 * 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化存储。它封装了与训练任务相关的多个字段信息，包括资源 ID、标题、描述、训练记录以及训练状态。 
 * ### 关键点 
 * 1. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - Swagger 的 `@ApiModelProperty` 注解为每个字段提供了元数据说明，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - `resourceId`：任务的唯一标识符。 
 * - `title`：任务的名称。 
 * - `description`：任务的详细描述。 
 * - `trainingRecord`：嵌套对象，包含具体的训练记录信息（类型为 `TrainingRecord`）。 
 * - `traininStatus`：训练的状态值（注意拼写错误应为 `trainingStatus`）。 
 * 3. **设计目的**： 
 * - 该类是专门为获取训练记录接口设计的响应模型，确保前后端数据交互的一致性。 
 * ### 改进建议 
 * - 修改字段 `traininStatus` 的拼写错误，改为 `trainingStatus`。 
 * - 可以进一步添加必要的构造函数或重载方法来增强类的灵活性。 
 * 总结来说，这是一个简洁且实用的数据传输对象（DTO），符合现代 Java 开发的最佳实践。 
**/
@Getter
@Setter
public class TrainingRecordMissionRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 训练记录
	 */
	@ApiModelProperty("训练记录")
	private TrainingRecord trainingRecord;

	/**
	 * 培训状态
	 */
	@ApiModelProperty("训练状态")
	private Integer traininStatus;
}

