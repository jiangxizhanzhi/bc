package com.cyberpeace.cs.service.context;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * WS场景报告数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `WsSceneReportData` 的 Java 类，用于表示某种 WS（WebSocket）场景的报告数据。以下是代码的简要分析： 
 * 1. **类名与功能**： 
 * - 类名为 `WsSceneReportData`，表明它是一个用于存储 WS 场景报告数据的实体类。 
 * - 注释说明该类由 `SN` 创建，并于 2024 年 11 月 8 日编写。 
 * 2. **包结构**： 
 * - 类位于 `com.cyberpeace.cs.service.context` 包中，表明其属于一个服务上下文相关的模块。 
 * 3. **依赖库**： 
 * - 使用了 `lombok` 提供的注解 `@Getter` 和 `@Setter`，简化了属性的 getter 和 setter 方法生成。 
 * - 使用了 `fastjson` 的 `JSONObject`，用于存储灵活的数据结构。 
 * 4. **属性**： 
 * - `type`：字符串类型，表示数据的类别或类型。 
 * - `data`：`JSONObject` 类型，用于存储动态、结构化的数据。 
 * 5. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，可能用于网络传输或持久化。 
 * 6. **用途**： 
 * - 该类主要用于封装 WS 场景的报告数据，支持灵活的数据结构存储。 
 * 总结：这是一个简单的数据传输对象（DTO），用于存储和传递 WS 场景的报告数据，具有良好的可扩展性和易用性。 
**/
@Getter
@Setter
public class WsSceneReportData implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 数据
	 */
	private JSONObject data;
}

