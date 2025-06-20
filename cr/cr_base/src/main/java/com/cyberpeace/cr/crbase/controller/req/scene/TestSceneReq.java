package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 测试场景请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TestSceneReq` 的 Java 类，用于表示测试场景请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 文档中的字段信息，标明 `businessTemplateId` 是必填字段。 
 * 3. **字段说明**： 
 * - `businessTemplateId`：表示业务模板 ID，是一个字符串类型，且根据注解是必填项。 
 * 4. **作者与日期**： 
 * - 代码由作者 SN 编写，并标注了编写日期为 2024 年 11 月 8 日。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，主要用于封装测试场景请求的数据，支持序列化并带有 API 文档注解。 
**/
@Getter
@Setter
public class TestSceneReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 业务模板id
	 */
	@ApiModelProperty(value = "业务模板id", required = true)
	private String businessTemplateId;
}

