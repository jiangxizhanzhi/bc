/**
 * 这段代码定义了一个名为 `AttackAddReq` 的 Java 类，用于表示攻击新增请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类主要用于封装攻击新增操作所需的数据结构，包含攻击相关的各种属性。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * 3. **属性说明**： 
 * - `name`：表示攻击名称。 
 * - `categoryId`：表示攻击所属的类别 ID。 
 * - `source`：表示攻击的来源。 
 * - `systemId`：表示防御技战术体系的 ID。 
 * - `action`：表示攻击涉及的一系列行动，类型为 `List<TacticAction>`。 
 * - `tool`：表示攻击使用的工具列表。 
 * - `strategy`：表示与攻击相关的策略列表，类型为 `List<SpaceActionStrategy>`。 
 * 4. **设计目的**： 
 * 该类作为请求体的一部分，通常在接口中用于接收客户端提交的攻击新增信息。 
 * 总结：这是一个简洁且功能明确的数据传输对象（DTO），适用于处理攻击新增相关的业务逻辑。 
**/
package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 攻击新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Getter
@Setter
public class AttackAddReq {

	/**
	 * 名称
	 */
	private String name;


	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别")
	private String categoryId;

	/**
	 * 源
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "来源")
	private String source;

	@ApiModelProperty(value = "防御技战术体系id")
	private String systemId;

	/**
	 * 行动
	 */
	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<String> tool;

	/**
	 * 策略
	 *
	 * @see List<SpaceActionStrategy>
	 */
	@ApiModelProperty(value = "相关策略")
	private List<SpaceActionStrategy> strategy;
}
