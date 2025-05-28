/**
 * 该代码定义了一个名为 `WsSceneReportBO` 的 Java 类，用于表示某种 WS 场景报告对象。以下是代码的主要分析和总结： 
 * ### 分析： 
 * 1. **类的基本信息**： 
 * - 类名：`WsSceneReportBO` 
 * - 包名：`com.cyberpeace.cs.service.context` 
 * - 实现了 `Serializable` 接口，说明该类的对象可以被序列化。 
 * 2. **注解和特性**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * - `@NoArgsConstructor`：生成无参构造函数。 
 * 3. **字段**： 
 * - `code`：字符串类型，表示报告的代号。 
 * - `data`：`WsSceneReportData` 类型，包含具体的数据内容。 
 * 4. **构造函数**： 
 * - 定义了一个带参数的构造函数，接收 `code`、`type` 和 `JSONObject` 类型的 `data` 参数。 
 * - 在构造函数中，创建了一个 `WsSceneReportData` 对象，并设置其 `data` 和 `type` 属性，然后将该对象赋值给当前类的 `data` 字段。 
 * 5. **依赖类**： 
 * - `WsSceneReportData`：这是一个内部使用的类（未在代码中显示），用于封装具体的报告数据。 
 * ### 总结： 
 * - 该类主要用于封装 WS 场景报告的相关信息。 
 * - 通过构造函数可以快速初始化对象，提供了一种方便的方式来构建带有特定数据的报告对象。 
 * - 使用了 Lombok 注解简化了代码量，提高了可读性和开发效率。 
**/
package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * WS场景报告博
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@NoArgsConstructor
public class WsSceneReportBO implements Serializable {

	/**
	 * 代号
	 */
	private String code;

	/**
	 * 数据
	 */
	private WsSceneReportData data;

	public WsSceneReportBO(String code, String type, JSONObject data) {
		WsSceneReportData reportData = new WsSceneReportData();
		reportData.setData(data);
		reportData.setType(type);

		this.setCode(code);
		this.setData(reportData);
	}
}