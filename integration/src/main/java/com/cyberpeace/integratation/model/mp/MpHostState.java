package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台主机州
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpHostState` 的 Java 类，用于表示中台主机的状态信息。以下是对其主要特性和功能的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 表示该类位于 `com.cyberpeace.integratation.model.mp` 包内。 
 * - **导入语句**：导入了必要的类和注解，包括 `Serializable` 接口、`Date` 类以及 Lombok 注解 `@Getter` 和 `@Setter`，还有 Swagger 注解 `@ApiModelProperty`。 
 * ### 类声明 
 * - **类名**：`MpHostState` 
 * - **实现接口**：实现了 `Serializable` 接口，表示该类的对象可以序列化为字节流，便于存储或传输。 
 * ### 类属性 
 * - `cpuCount`：整型变量，表示 CPU 核数。 
 * - `cpuPercent`：双精度浮点型变量，表示 CPU 使用率。 
 * - `diskPercent`：双精度浮点型变量，表示磁盘使用率。 
 * - `diskSpace`：整型变量，表示磁盘空间（单位：GB）。 
 * - `elementId`：字符串变量，表示节点 ID。 
 * - `memoryFree`：整型变量，表示空闲内存（单位：MB）。 
 * - `memoryPercent`：双精度浮点型变量，表示内存使用率。 
 * - `memoryTotal`：整型变量，表示总内存（单位：MB）。 
 * - `sceneId`：字符串变量，表示场景 ID。 
 * - `time`：日期类型变量，表示时间。 
 * ### 注解 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：Swagger 注解，用于生成 API 文档，描述每个字段的含义。 
 * ### 总结 
 * `MpHostState` 类是一个数据模型类，用于封装和表示中台主机的性能状态信息。它包含了主机的 CPU、内存、磁盘等关键指标，并且可以通过 Swagger 自动生成相应的 API 文档。通过实现 `Serializable` 接口，该类的对象可以方便地进行序列化操作。 
**/
@Getter
@Setter
public class MpHostState implements Serializable {

	/**
	 * CPU核数
	 */
	@ApiModelProperty("CPU核数")
	private Integer cpuCount;

	/**
	 * CPU使用率
	 */
	@ApiModelProperty("CPU使用率")
	private Double cpuPercent;

	/**
	 * 磁盘使用率
	 */
	@ApiModelProperty("磁盘使用率")
	private Double diskPercent;

	/**
	 * 磁盘空间(GB)
	 */
	@ApiModelProperty("磁盘空间(GB)")
	private Integer diskSpace;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	/**
	 * 空闲内存(MB)
	 */
	@ApiModelProperty("空闲内存(MB)")
	private Integer memoryFree;

	/**
	 * 内存使用率
	 */
	@ApiModelProperty("内存使用率")
	private Double memoryPercent;

	/**
	 * 总内存(MB)
	 */
	@ApiModelProperty("总内存(MB)")
	private Integer memoryTotal;

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private Date time;

}

