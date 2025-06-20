package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台拓扑统计
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpTopoStatistic` 的 Java 类，用于表示中台拓扑统计信息。以下是该类的详细分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.integratation.model.mp; 
 * import java.io.Serializable; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `package com.cyberpeace.integratation.model.mp;`：声明了这个类所在的包。 
 * - 导入了 `Serializable` 接口，使得这个类可以序列化。 
 * - 导入了 `ApiModelProperty` 注解，用于 Swagger 文档生成。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MpTopoStatistic implements Serializable { 
 * ``` 
 * - 类 `MpTopoStatistic` 实现了 `Serializable` 接口，这意味着它可以被序列化。 
 * - 使用 Lombok 的注解 `@Getter` 和 `@Setter` 自动生成所有属性的 getter 和 setter 方法。 
 * ### 属性 
 * ```java 
 * private String csceneId; 
 * private Integer firewallCount; 
 * private Integer routerCount; 
 * private Integer switchCount; 
 * private Integer terminalCount; 
 * private Integer npcCount; 
 * private Integer resourceOccupiedNums; 
 * ``` 
 * - `csceneId`：场景ID，类型为 `String`。 
 * - `firewallCount`：防火墙数量，类型为 `Integer`。 
 * - `routerCount`：路由器数量，类型为 `Integer`。 
 * - `switchCount`：交换机数量，类型为 `Integer`。 
 * - `terminalCount`：终端设备数量，类型为 `Integer`。 
 * - `npcCount`：全国人大数量，类型为 `Integer`（可能是笔误，建议改为更合理的描述）。 
 * - `resourceOccupiedNums`：资源占用数量，类型为 `Integer`。 
 * ### 方法 
 * ```java 
 * public Integer getNodesCount() { 
 * int count = 0; 
 * if (this.firewallCount != null) { 
 * count += this.firewallCount; 
 * } 
 * if (this.routerCount != null) { 
 * count += this.routerCount; 
 * } 
 * if (this.switchCount != null) { 
 * count += this.switchCount; 
 * } 
 * if (this.terminalCount != null) { 
 * count += this.terminalCount; 
 * } 
 * if (this.npcCount != null) { 
 * count += this.npcCount; 
 * } 
 * return count; 
 * } 
 * ``` 
 * - `getNodesCount()` 方法计算并返回所有节点（包括防火墙、路由器、交换机、终端设备和全国人大）的数量总和。 
 * - 注意到 `npcCount` 可能是一个笔误，建议修改为更合理的字段名。 
 * ### 总结 
 * - 这个类主要用于存储和操作与中台拓扑相关的统计信息。 
 * - 使用了 Lombok 来简化代码。 
 * - 提供了一个方法来计算所有节点的总数。 
 * - 建议检查并修正 `npcCount` 字段的命名，使其更符合实际业务需求。 
**/
@Getter
@Setter
public class MpTopoStatistic implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * CSenseid
	 */
	private String csceneId;

	/**
	 * 防火墙计数
	 */
	private Integer firewallCount;

	/**
	 * 路由器计数
	 */
	private Integer routerCount;

	/**
	 * 开关计数
	 */
	private Integer switchCount;

	/**
	 * 终端计数
	 */
	private Integer terminalCount;

	/**
	 * 全国 人大计数
	 */
	private Integer npcCount;

	/**
	 * 资源占用数
	 */
	private Integer resourceOccupiedNums;

	public Integer getNodesCount() {
		int count = 0;
		if (this.firewallCount != null) {
			count += this.firewallCount;
		}
		if (this.routerCount != null) {
			count += this.routerCount;
		}
		if (this.switchCount != null) {
			count += this.switchCount;
		}
		if (this.terminalCount != null) {
			count += this.terminalCount;
		}
		if (this.npcCount != null) {
			count += this.npcCount;
		}
		return count;
	}
}

