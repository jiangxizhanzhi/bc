package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 保存群信息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SaveGroupInfoReq` 的 Java 类，该类用于处理保存群信息的请求。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **导入语句**： 
 * - 导入了 Lombok 注解库（`@Getter`, `@Setter`）。 
 * - 导入了 Swagger 注解（`@ApiModel`, `@ApiModelProperty`）。 
 * - 导入了 Java 标准库中的 `Serializable` 接口和其他一些常用的注解。 
 * ### 类属性 
 * - **classroomId**：表示课堂ID，使用 `String` 类型，且允许为空。 
 * - **name**：表示群组的名称，使用 `String` 类型，并且不能为空（使用 `@NotBlank` 注解）。 
 * - **data**：表示群组的信息列表，使用 `List<GroupInfoBo>` 类型，并且不能为空（使用 `@NotEmpty` 注解）。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：将该类标记为一个模型对象。 
 * - `@ApiModelProperty`：为每个字段提供描述性信息。 
 * ### 实现接口 
 * - `Serializable`：使得该类可以序列化，便于在网络上传输或持久化存储。 
 * ### 总结 
 * - **用途**：这个类主要用于接收客户端请求，包含保存群组信息所需的参数。 
 * - **验证**：通过 `@NotBlank` 和 `@NotEmpty` 注解确保 `name` 和 `data` 字段在请求时必须提供有效值。 
 * - **数据传输**：实现了 `Serializable` 接口，方便在不同系统组件之间传递数据。 
 * 这个类的设计简洁明了，符合 RESTful API 的设计原则，同时也考虑到了数据验证的需求。 
**/
@Getter
@Setter
@ApiModel
public class SaveGroupInfoReq implements Serializable {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	private String classroomId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分组模板名称")
	@NotBlank
	private String name;

	/**
	 * 数据
	 *
	 * @see List<GroupInfoBo>
	 */
	@ApiModelProperty("分组信息")
	@NotEmpty
	private List<GroupInfoBo> data;

}

