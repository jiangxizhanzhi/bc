package com.cyberpeace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个泛型类 `BaseRsp<T>`，用于表示基础响应对象。以下是简要分析： 
 * 1. **类的功能**： 
 * - `BaseRsp` 是一个通用的响应类，适用于各种业务场景，通过泛型 `T` 可以承载具体的数据内容。 
 * - 它包含了响应的基本属性，如数据、状态码、消息等。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成了 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor` 分别生成全参构造函数和无参构造函数。 
 * 3. **主要字段**： 
 * - `data`：存放具体的响应数据，类型为泛型 `T`。 
 * - `code`：响应的状态码，默认值为 `"OJ-000000"`。 
 * - `message`：描述响应的消息。 
 * - `detail`：保留字段，可能用于存储额外信息。 
 * - `timestamp`：注释掉的时间戳字段，未实现。 
 * 4. **构造方法**： 
 * - 提供了带 `data` 参数的构造方法，用于快速创建响应对象。 
 * 总结：这是一个简洁且通用的基础响应类，适用于需要返回数据和状态码的场景。 
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRsp<T> {

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 代号
	 */
	private  String code = "OJ-000000";

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 保留字段
	 */
	private String detail;

	/**
	 * 时间戳
	 */
//	public long timestamp = System.currentTimeMillis();

	public BaseRsp(T data) {
		this.data = data;
	}
}

