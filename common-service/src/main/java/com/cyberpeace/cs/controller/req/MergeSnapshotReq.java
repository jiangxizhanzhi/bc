package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合并快照请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MergeSnapshotReq` 的 Java 类，用于表示合并快照请求的数据模型。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体对象，用于接收合并快照操作所需的信息。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModel` 和 `@ApiModelProperty` 注解，用于生成 API 文档，描述模型及其字段。 
 * 3. **字段说明**： 
 * - `imageType`：镜像类型，必填。 
 * - `newName`：合并后的名称，非必填。 
 * - `snapshotName`：最后一个快照的名称，必填。 
 * - `uuid`：镜像 ID，必填。 
 * - `sceneId`：场景编号，必填。 
 * 4. **验证约束**： 
 * - 字段 `imageType`、`snapshotName` 和 `uuid` 使用了 `@NotBlank` 注解，确保这些字段不能为空或仅包含空白字符。 
 * 总结：这是一个简洁且功能明确的请求体类，适用于合并快照操作的参数传递，并通过注解增强了代码的可读性和文档生成能力。 
**/
@Getter
@Setter
@ApiModel
public class MergeSnapshotReq implements Serializable {

	/**
	 * 图像类型
	 */
	@ApiModelProperty(value = "镜像类型", required = true)
	@NotBlank
	private String imageType;

	/**
	 * 新增功能名称
	 */
	@ApiModelProperty(value = "合并后的名称")
	private String newName;

	/**
	 * 快照名称
	 */
	@ApiModelProperty(value = "最后一个快照的名称", required = true)
	@NotBlank
	private String snapshotName;

	/**
	 * uuid
	 */
	@ApiModelProperty(value = "镜像id", required = true)
	@NotBlank
	private String uuid;

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景编号", required = true)
	private String sceneId;
}

