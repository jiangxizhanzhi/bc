/**
 * 这段代码定义了一个名为 `SubmitMissionsReq` 的 Java 类，用于表示提交任务请求的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装提交任务请求所需的数据。 
 * 2. **包与导入**： 
 * - 包名：`com.cyberpeace.cr.crbase.controller.req`，表明该类位于项目中的 `req` 包下。 
 * - 导入了必要的类： 
 * - `javax.validation.constraints.NotNull`：用于字段的非空校验。 
 * - `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档时描述字段用途。 
 * - `lombok.Getter` 和 `lombok.Setter`：通过 Lombok 注解自动生成 getter 和 setter 方法。 
 * 3. **注解说明**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，描述字段的含义和是否必填。 
 * 4. **字段说明**： 
 * - `resourceId`：资源 ID，用于标识任务所属的资源。 
 * - `missionHash`：任务哈希，用于唯一标识任务。 
 * - `answer`：提交的答案。 
 * - `remark`：备注信息。 
 * 5. **设计特点**： 
 * - 使用了 Lombok 注解减少冗余代码。 
 * - 字段均使用了 `@NotNull` 注解，确保请求数据的完整性。 
 * - 结合 Swagger 注解，便于生成 API 文档。 
 * 总结：这是一个简洁且功能明确的请求体类，适合用于接收提交任务的请求数据。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 提交任务请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SubmitMissionsReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true, value = "用户id")
	@NotNull
	private String resourceId;

	/**
	 * 任务哈希
	 */
	@ApiModelProperty(required = true, value = "任务id")
	@NotNull
	private String missionHash;

	/**
	 * 答案
	 */
	@ApiModelProperty(required = true, value = "提交答案")
 	private String answer;

	/**
	 * 备注
	 */
	@ApiModelProperty(required = true, value = "备注")
	private String remark;

}
