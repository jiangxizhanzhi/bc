package com.cyberpeace.space.service.context.strategy;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 空间策略框架新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategyFrameAddReq` 的类，用于表示空间策略框架新增请求体。以下是代码的简要分析： 
 * 1. **类的用途**： 
 * - 该类是用于空间策略框架中新增操作的请求体，包含与新增相关的字段信息。 
 * 2. **类的注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述字段的元数据（尽管当前注解的 `value` 属性为空）。 
 * 3. **字段说明**： 
 * - `name`：表示名称，类型为 `String`，通过注释说明其用途。 
 * - `parent`：表示父节点或父母，类型为 `String`，通过注释说明其用途。 
 * 4. **设计特点**： 
 * - 类的设计简单明了，仅包含两个字段，适合用于传输新增请求的基本信息。 
 * - 使用了 Lombok 和 Swagger 注解，提高了代码的可读性和开发效率。 
 * 总结：这是一个轻量级的请求体类，适用于空间策略框架中的新增操作，具有良好的可扩展性和注解支持。 
**/
@Getter
@Setter
public class SpaceStrategyFrameAddReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "")
	private String name;

	/**
	 * 父母
	 *
	 * @see String
	 */
	private String parent;


	public void validate() {
		if (!StringUtils.isEmpty(this.name)){
			CrCommonValidator.validateLength(this.name, 4, 40,
					CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
	}
}

