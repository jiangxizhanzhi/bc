/**
 * 这段代码定义了一个名为 `SignInfoBo` 的 Java 类，用于表示标牌信息。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModel`: 用于标记这个类是一个 Swagger API 模型。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于标记类中的属性，描述这些属性在 Swagger 文档中的说明。 
 * - `lombok.Getter`, `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 类名：`SignInfoBo` 
 * - 作者：`SN` 
 * - 版本：`1.0.0` 
 * - 创建日期：`2024/11/08` 
 * ### 类实现 
 * - 实现了 `Serializable` 接口，表示这个类的对象可以序列化。 
 * ### 类成员变量 
 * - `finishCount`: 完成人数，默认值为 `0`。 
 * - `isSign`: 签到人数据，默认值为 `0`。 
 * - `notSign`: 未签到人数，默认值为 `0`。 
 * - `organizationInfo`: 组织信息，类型为 `String`。 
 * ### 成员变量注释 
 * - `finishCount`: 描述为“完成人数”。 
 * - `isSign`: 描述为“签到人数据”。 
 * - `notSign`: 描述为“未签到人数”。 
 * - `organizationInfo`: 描述为“组织信息”。 
 * ### Getter 和 Setter 方法 
 * - Lombok 的 `@Getter` 和 `@Setter` 注解会自动生成所有成员变量的 getter 和 setter 方法。 
 * ### 总结 
 * `SignInfoBo` 类主要用于存储和传递与标牌相关的签到信息。它包含了签到、未签到的人数以及组织信息。通过使用 Swagger 注解，这个类可以在 API 文档中进行详细的描述。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 标牌信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class SignInfoBo implements Serializable {

	/**
	 * 完成计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成人数")
	private Integer finishCount = 0;

	/**
	 * 是符号
	 *
	 * @see Integer
	 */
	@ApiModelProperty("签到人数据")
	private Integer isSign = 0;

	/**
	 * 未签名
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未签到人数")
	private Integer notSign = 0;

	/**
	 * 组织信息
	 *
	 * @see String
	 */
	@ApiModelProperty("组织信息")
	private String organizationInfo;

}
