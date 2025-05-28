/**
 * 这段代码定义了一个名为 `SystemUseStatusRes` 的 Java 类，用于表示系统使用状态的结果。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * - 它包含了系统在不同维度上的使用状态数据，如 CPU、磁盘和内存。 
 * 2. **字段说明**： 
 * - `xAxis`：表示时间轴，用于描述数据的时间点。 
 * - `cpudata`：存储 CPU 使用率的数据。 
 * - `diskdata`：存储磁盘使用率的数据。 
 * - `ramdata`：存储内存使用率的数据。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述信息，便于 API 文档生成。 
 * 4. **用途**： 
 * - 该类通常用于 API 响应中，返回系统的实时使用状态数据。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装系统使用状态的数据，支持序列化并带有详细的注解说明。 
**/
package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用状态结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SystemUseStatusRes implements Serializable {

	/**
	 * XAXIS 公司
	 */
	@ApiModelProperty("x轴，时间")
	private List<String> xaxis;

	/**
	 * 中央数据
	 */
	@ApiModelProperty("cpu")
	private List<String> cpudata;

	/**
	 * 磁盘数据
	 */
	@ApiModelProperty("disk")
	private List<String> diskdata;

	/**
	 * RAMDATA
	 */
	@ApiModelProperty("memory")
	private List<String> ramdata;

}
