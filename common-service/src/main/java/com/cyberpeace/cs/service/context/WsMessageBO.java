/**
 * 这段代码定义了一个泛型类 `WsMessageBO`，用于表示 WebSocket 消息的业务对象（BO）。以下是简要分析： 
 * 1. **类的功能**： 
 * - `WsMessageBO` 是一个通用的消息载体，支持携带自定义类型的数据。 
 * - 它实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 2. **字段说明**： 
 * - `code`：消息的唯一标识符，通过 Swagger 注解标明其为“消息编码”。 
 * - `data`：存储实际的消息数据，类型为泛型 `T`，通过 Swagger 注解标明其为“消息内容”。 
 * 3. **构造方法**： 
 * - 提供了一个带参构造方法，用于初始化 `code` 和 `data` 字段。 
 * 4. **注解与框架**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供文档描述。 
 * 5. **适用场景**： 
 * - 该类适用于需要通过 WebSocket 传输结构化消息的场景，尤其是需要携带不同类型的业务数据时。 
 * 总结：这是一个轻量级、可扩展的消息封装类，适合用作 WebSocket 消息的载体。 
**/
package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * WS 消息 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class WsMessageBO<T> implements Serializable {

	/**
	 * 代号
	 */
	@ApiModelProperty("消息编码")
	private String code;

	/**
	 * 数据
	 */
	@ApiModelProperty("消息内容")
	private T data;

	public WsMessageBO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
