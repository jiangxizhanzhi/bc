package com.cyberpeace.oj.course.service.bo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 集团信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ArchiveGroupBo
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `GroupInfoBo` 的 Java 类，该类继承自 `ArchiveGroupBo`。以下是代码的详细分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`GroupInfoBo` 
 * - **继承**：`GroupInfoBo` 继承自 `ArchiveGroupBo` 
 * ### 注解 
 * - **`@ApiModel`**：表示这个类是一个模型对象，通常用于 Swagger 文档生成。 
 * - **`@Getter` 和 `@Setter`**：来自 Lombok 库，用于自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * 1. **sceneData** 
 * - **类型**：`JSONObject` 
 * - **注释**：表示场景数据。 
 * - **Swagger 注解**：`@ApiModelProperty("场景信息")`，用于 Swagger 文档生成，描述该字段为“场景信息”。 
 * 2. **userInfos** 
 * - **类型**：`List<SimpleUserData>` 
 * - **注释**：表示用户信息。 
 * - **Swagger 注解**：`@ApiModelProperty("组员信息")`，用于 Swagger 文档生成，描述该字段为“组员信息”。 
 * ### 总结 
 * - `GroupInfoBo` 类主要用于存储集团信息，并包含两个主要属性：`sceneData`（场景数据）和 `userInfos`（用户信息）。 
 * - 通过 Swagger 注解，这些属性可以在 API 文档中得到清晰的描述。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * ### 注意点 
 * - `SimpleUserData` 类未在代码中定义，但根据注释可以推测它是一个表示简单用户数据的类。 
 * - 如果 `ArchiveGroupBo` 类有其他重要的属性或方法，建议进一步查看该类的定义以了解完整的继承关系。 
**/
@Getter
@Setter
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	/**
	 * 用户信息
	 *
	 * @see List<SimpleUserData>
	 */
	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}

