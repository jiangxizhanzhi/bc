package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务完成数
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MissionCompletion` 的 Java 类，用于表示任务完成的相关信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储任务的状态和完成的数量。 
 * - 提供了方法来增加完成数量。 
 * 2. **属性**： 
 * - `missionStatus`：表示任务的状态，类型为 `Integer`。 
 * - `completeNum`：表示任务的完成数量，类型为 `Integer`。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于生成 API 文档时描述字段的含义。 
 * 4. **构造函数**： 
 * - 提供了一个构造函数，用于初始化 `missionStatus` 和 `completeNum`。 
 * 5. **方法**： 
 * - `addCompletionNum()`：用于增加完成数量，每次调用时将 `completeNum` 增加 1。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装任务完成的信息，并提供了基本的操作功能。 
**/
@Getter
@Setter
public class MissionCompletion {

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private Integer missionStatus;

	/**
	 * 完成数量
	 */
	@ApiModelProperty(value = "任务完成数")
	private Integer completeNum;

	public void addCompletionNum() {
		completeNum += 1;
	}

	public MissionCompletion(Integer missionStatus, Integer completeNum) {
		this.missionStatus = missionStatus;
		this.completeNum = completeNum;
	}

}

