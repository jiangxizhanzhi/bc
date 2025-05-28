/**
 * 这段代码定义了一个名为 `EvalTemplateRsp` 的 Java 类，该类用于表示评估模板的响应信息。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **实现接口**：`Serializable`，表明该类的对象可以被序列化。 
 * ### 类属性 
 * - **enableRecording**：布尔值，表示是否启用录屏功能。 
 * - **sceneConfigData**：`JSONObject` 类型，表示场景配置数据。 
 * - **stepConfig**：`List<EvalTaskStepConfig>` 类型，表示步骤配置信息。 
 * - **currentTask**：字符串类型，表示当前的测评任务。 
 * ### 类方法 
 * - **构造函数**：未显示，但默认存在。 
 * - **Getter 和 Setter 方法**：使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成。 
 * - **setStepConfig**：自定义方法，将传入的 JSON 字符串转换为 `EvalTaskStepConfig` 对象列表，并赋值给 `stepConfig` 属性。 
 * ### 注解 
 * - 使用了 Swagger 注解 `@ApiModelProperty` 来描述每个字段的含义和用途。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 以简化 getter 和 setter 方法的编写。 
 * ### 其他引用 
 * - `EvalTemplateListRsp`：继承自另一个响应类，可能用于包含一些公共的响应信息。 
 * - `JsonUtil`：工具类，用于处理 JSON 数据的转换。 
 * ### 总结 
 * 该类主要用于封装评估模板的响应数据，包括录屏设置、场景配置、步骤配置以及当前任务等信息。通过使用 Lombok 注解和 Swagger 注解，使得代码更加简洁且具有良好的文档说明。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateRsp extends EvalTemplateListRsp implements Serializable {

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "是否录屏")
	private Boolean enableRecording;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "拓扑配置")
	private JSONObject sceneConfigData;

	/**
	 * 步骤配置
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig;

	/**
	 * 当前任务
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTask;

	public void setStepConfig(String stepConfig) {
		this.stepConfig = JsonUtil.toJsonObjectArray(stepConfig, EvalTaskStepConfig.class);
	}

}
