package com.cyberpeace.space.service.context.strategy;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 空间策略新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategyAddReq` 的 Java 类，属于 `com.cyberpeace.space.service.context.strategy` 包。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类是一个用于接收空间策略新增请求的实体类，主要用于封装新增策略所需的各种字段信息。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成类中所有字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `name`: 策略名称。 
 * - `content`: 策略内容。 
 * - `step`: 操作步骤。 
 * - `example`: 配置举例。 
 * - `script`: 执行脚本。 
 * - `scriptType`: 脚本类型（常量值为 44）。 
 * - `frameId`: 框架 ID。 
 * - `method`: 策略执行成功判定方法。 
 * - `suggest`: 处置建议。 
 * - `gauge`: 策略依据。 
 * 4. **用途**： 
 * 这个类通常会被用作后端服务中的请求参数对象，通过接收前端传来的 JSON 数据来新增或更新空间策略。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装新增空间策略的相关信息，方便后续业务逻辑处理。 
**/
@Getter
@Setter
public class SpaceStrategyAddReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "")
	private String name;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "策略内容")
	private String content;

	/**
	 * 步骤
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "操作步骤")
	private String step;

	/**
	 * 例
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "配置举例")
	private String example;

	/**
	 * 脚本
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "执行脚本")
	private String script;

	/**
	 * 脚本类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "脚本类型, 常量type=44")
	private Integer scriptType;

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String frameId;

	/**
	 * 方法
	 *
	 * @see String
	 */
	@ApiModelProperty("策略执行成功判定方法")
	private String method;

	/**
	 * 建议
	 *
	 * @see String
	 */
	@ApiModelProperty("处置建议")
	private String suggest;

	/**
	 * 轨距
	 *
	 * @see String
	 */
	@ApiModelProperty("策略依据")
	private String gauge;

	public void validate() {
		if (!StringUtils.isEmpty(this.name)){
			CrCommonValidator.validateNameLen(this.name,  CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
	}
}

