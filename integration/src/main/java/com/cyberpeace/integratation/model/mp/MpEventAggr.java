/**
 * 这段代码定义了一个名为 `MpEventAggr` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示某个“中台事件”的聚合数据。以下是详细的分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.integratation.model.mp; 
 * import java.io.Serializable; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `package` 声明指定了类所在的包。 
 * - 导入了必要的类：`Serializable`、`ApiModelProperty` 和 Lombok 提供的 `Getter` 和 `Setter` 注解。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MpEventAggr implements Serializable { 
 * ``` 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动为类中的所有字段生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，使得该对象可以序列化，便于在网络上传输或保存到文件。 
 * ### 字段声明 
 * ```java 
 * /** 
 * * 文件百分比 
 *
 * @ApiModelProperty("文件百分比") 
 * private Double file; 
 * /** 
 * * 网络连接百分比 
 *
 * @ApiModelProperty("网络连接百分比") 
 * private Double network; 
 * /** 
 * * 端口百分比 
 *
 * @ApiModelProperty("端口百分比") 
 * private Double port; 
 * /** 
 * * 进程百分比 
 *
 * @ApiModelProperty("进程百分比") 
 * private Double process; 
 * /** 
 * * 用户百分比 
 *
 * @ApiModelProperty("用户百分比") 
 * private Double user; 
 * ``` 
 * - 每个字段都是 `Double` 类型，表示某种指标的百分比。 
 * - 使用了 `@ApiModelProperty` 注解来描述每个字段的意义，这有助于生成 API 文档。 
 * ### 总结 
 * - **功能**：`MpEventAggr` 类用于存储与某个“中台事件”相关的各种百分比数据。 
 * - **特性**： 
 * - 通过 Lombok 的注解自动生成 getter 和 setter 方法。 
 * - 支持序列化，便于数据传输和持久化。 
 * - 使用 Swagger 注解来描述字段，方便文档生成。 
 * - **用途**：通常在需要处理和传输这种类型的数据时使用，例如在监控系统中表示不同资源的使用情况。 
 * 这个类的设计简洁且功能明确，适用于需要快速开发 RESTful API 的场景。 
**/
package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台事件aggr
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpEventAggr implements Serializable {

	/**
	 * 文件百分比
	 */
	@ApiModelProperty("文件百分比")
	private Double file;

	/**
	 * 网络连接百分比
	 */
	@ApiModelProperty("网络连接百分比")
	private Double network;

	/**
	 * 端口百分比
	 */
	@ApiModelProperty("端口百分比")
	private Double port;

	/**
	 * 进程百分比
	 */
	@ApiModelProperty("进程百分比")
	private Double process;

	/**
	 * 用户百分比
	 */
	@ApiModelProperty("用户百分比")
	private Double user;

}
