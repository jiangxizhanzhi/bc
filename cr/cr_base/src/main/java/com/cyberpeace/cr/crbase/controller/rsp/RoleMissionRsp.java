/**
 * 这段代码定义了一个名为 `RoleMissionRsp` 的 Java 类，用于表示岗位任务的响应数据。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * `RoleMissionRsp` 是一个数据传输对象（DTO），主要用于封装岗位任务的相关信息，便于在前后端之间传递任务相关的数据。 
 * 2. **继承与注解**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * - 使用了 Lombok 注解： 
 * - `@Getter` 和 `@Setter` 自动生成所有字段的 getter 和 setter 方法，减少了样板代码。 
 * - `@Data` 注解包含了 `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode` 等功能，但在这里没有使用全部特性。 
 * 3. **字段说明**： 
 * - 各字段通过 `@ApiModelProperty` 注解描述了其用途，方便文档生成工具（如 Swagger）生成 API 文档。 
 * - 主要字段包括任务 ID (`resourceId`)、任务名称 (`title`)、任务类别 (`category`)、任务状态 (`missionStatus`)、技能分数 (`skillPoints`)、任务答案 (`answer`) 等。 
 * 4. **嵌套对象**： 
 * - `deliveryDetails` 是一个 `List<RbUserData>` 类型的字段，表示任务的交货详情，其中 `RbUserData` 是另一个类，可能包含用户相关的信息。 
 * 5. **设计目的**： 
 * - 该类专注于封装任务相关的数据结构，适合用于 RESTful API 的响应体中，为前端提供任务的详细信息。 
 * 总结：这是一个典型的 DTO 类，用于简化任务数据的传输和管理，具有良好的可读性和扩展性。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.RbUserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 岗位任务响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class RoleMissionRsp implements Serializable {

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
	 * 选择
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 类别
	 */
	@ApiModelProperty("任务类别")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 任务状态
	 */
	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	/**
	 * 类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 技能点
	 */
	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	/**
	 * 写入向上
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 答案
	 */
	@ApiModelProperty("任务答案")
	private String answer;

	/**
	 * 得分
	 */
	@ApiModelProperty("任务分数")
	private Integer score;

	/**
	 * 交货详情
	 */
	@ApiModelProperty("用户列表")
	private List<RbUserData> deliveryDetails;
}
