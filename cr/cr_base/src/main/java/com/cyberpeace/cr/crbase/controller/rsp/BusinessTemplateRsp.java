/**
 * 该代码定义了一个名为 `BusinessTemplateRsp` 的 Java 类，用于表示靶场模板的公共信息。以下是对其的主要分析： 
 * ### 类的功能与作用 
 * - **类名**：`BusinessTemplateRsp` 
 * - **功能**：作为靶场模板的信息载体，封装了模板的相关属性及其元数据。 
 * - **继承关系**：实现了 `Serializable` 接口，表明它可以被序列化。 
 * ### 主要属性 
 * - `id`：冗余字段，对应资源 ID。 
 * - `resourceId`：模板的唯一标识符。 
 * - `sceneManagerId`：场景经理 ID，用于前端兼容。 
 * - `name`：模板名称。 
 * - `description`：模板描述。 
 * - `type`：训练类型。 
 * - `difficult`：训练难度。 
 * - `createTime`：模板创建时间。 
 * - `createUser`：创建人姓名。 
 * - `share`：授权操作类型。 
 * - `shareTeachers`：授权操作的用户列表。 
 * - `sceneConfigInfo`：场景配置信息对象，类型为 `SceneConfigRsp`。 
 * ### 注解说明 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了详细的描述信息，便于 API 文档生成。 
 * ### 总结 
 * 此代码是一个典型的 DTO（Data Transfer Object）类，用于在不同层之间传递数据。它结构清晰，注解丰富，便于后续开发和维护。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class BusinessTemplateRsp implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "冗余字段，resource_id")
	private String id;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "唯一标识符")
	private String resourceId;

	/**
	 * 场景经理id
	 */
	@ApiModelProperty(value = "拓扑id，前端兼容")
	private String sceneManagerId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "模板名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "模板描述")
	private String description;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer type;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "训练难度")
	private Integer difficult;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建人名字")
	private String createUser;

	/**
	 * 共享
	 */
	@ApiModelProperty(value = "授权操作类型")
	private Integer share;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权操作用户列表")
	private List<String> shareTeachers;

	/**
	 * 场景配置信息
	 */
	@ApiModelProperty(value = "场景编排")
	private SceneConfigRsp sceneConfigInfo;

}
