package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间基础算法Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 1. **类的功能** 
 * - `SpaceBaseAlgorithmBo` 是一个基于 `SpaceBoBase` 的扩展类，主要用于封装空间基础算法相关的业务对象（BO, Business Object）。它通过注解和字段定义了算法的相关信息。 
 * #### 2. **字段说明** 
 * - **基本信息**： 
 * - `resourceId`: 资源 ID。 
 * - `name`: 算法的名称。 
 * - `description`: 算法的简述。 
 * - **类型相关**： 
 * - `type`: 算法的类型。 
 * - `codeType`: 代码语言类型。 
 * - `code`: 具体的代码内容。 
 * - **参数**： 
 * - `parameter`: 算法的参数列表，类型为 `List<SpaceAlgorithmParameter>`。 
 * - **继承自 `SpaceBoBase`**：该类可能包含一些通用的基础属性或方法。 
 * #### 3. **方法说明** 
 * - **`bo2Entity()` 方法**： 
 * - 将当前 BO 对象转换为实体对象 `SpaceBaseAlgorithm`。 
 * - 使用工具类 `BeanUtils.copy` 进行对象复制。 
 * - 将 `parameter` 列表序列化为 JSON 字符串并赋值给实体对象的 `parameter` 属性。 
 * - 返回生成的实体对象。 
 * #### 4. **技术点** 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty` 用于描述字段的含义，便于 API 文档生成。 
 * - **工具类**： 
 * - `BeanUtils.copy`：用于对象之间的属性拷贝。 
 * - `JsonUtil.arrayToString`：将 `List` 类型的参数序列化为 JSON 字符串。 
 * #### 5. **优点** 
 * - 遵循面向对象的设计原则，封装了算法相关信息。 
 * - 使用 Lombok 减少了冗余代码。 
 * - 提供了 BO 到实体对象的转换逻辑，便于数据层使用。 
 * #### 6. **潜在问题** 
 * - `parameter` 字段的序列化依赖外部工具类 `JsonUtil`，如果该工具类不可用或出错，可能导致运行时异常。 
 * - 没有对输入数据进行校验，可能会导致非法数据进入系统。 
 * #### 总结 
 * `SpaceBaseAlgorithmBo` 是一个典型的业务对象类，主要用于封装算法相关的信息，并提供 BO 到实体对象的转换功能。设计合理，但需注意工具类依赖和数据校验问题。 
**/

@Getter
@Setter
public class SpaceBaseAlgorithmBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 代号类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "代码语言类型")
	private Integer codeType;

	/**
	 * 代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "代码")
	private String code;

	/**
	 * 参数
	 *
	 * @see List<SpaceAlgorithmParameter>
	 */
	@ApiModelProperty(value = "参数")
	private List<SpaceAlgorithmParameter> parameter;

	/**
	 * BO2 实体
	 *
	 * @return SpaceBase算法
	 * @throws
	 * @see SpaceBaseAlgorithm
	 */
	public SpaceBaseAlgorithm bo2Entity() {
		SpaceBaseAlgorithm entity = BeanUtils.copy(this, SpaceBaseAlgorithm.class);
		entity.setParameter(JsonUtil.arrayToString(this.parameter));
		return entity;
	}

}
    
