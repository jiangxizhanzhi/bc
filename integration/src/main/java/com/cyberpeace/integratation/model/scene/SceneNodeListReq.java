package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 场景节点列表请求体
 *
 * @author SN
 * @date 2023/05/30
 */
/**
 * 这段代码定义了一个名为 `SceneNodeListReq` 的类，该类继承自 `TopoNodeListReq` 并实现了 `Serializable` 接口。这个类用于表示场景节点列表请求体，并包含一些属性和方法。 
 * ### 主要内容 
 * 1. **类定义**： 
 * - 类名：`SceneNodeListReq` 
 * - 继承：`TopoNodeListReq` 
 * - 实现接口：`Serializable` 
 * 2. **属性**： 
 * - `private Boolean realTimeHostState;`：实时主机状态。 
 * - `private List<String> elementIds;`：筛选节点ID列表。 
 * - `private String systemTypeName;`：系统类型名称。 
 * 3. **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `@Slf4j`：由 Lombok 自动生成日志对象 `log`。 
 * 4. **方法**： 
 * - `protected List<String> getQuery()`：覆盖父类的 `getQuery` 方法。返回一个查询条件列表，根据类的属性动态生成查询条件。 
 * ### 详细分析 
 * - **继承关系**：`SceneNodeListReq` 继承了 `TopoNodeListReq`，这意味着它可能继承了一些通用的属性或方法。 
 * - **实现接口**：实现了 `Serializable` 接口，说明这个类的对象可以被序列化，以便在网络上传输或持久化存储。 
 * - **属性**： 
 * - `realTimeHostState`：布尔值，表示是否需要实时主机状态。 
 * - `elementIds`：字符串列表，表示筛选节点的ID。 
 * - `systemTypeName`：字符串，表示系统类型名称。 
 * - **方法**： 
 * - `getQuery` 方法是关键部分，它通过检查类的属性来构建查询条件。如果 `realTimeHostState` 为 `true`，则添加 `realTimeHostState=true` 到查询条件中；否则添加 `realTimeHostState=false`。同样地，如果 `systemTypeName` 不为空，则将其添加到查询条件中。最后，如果 `elementIds` 列表不为空，则将所有元素用逗号连接后添加到查询条件中。 
 * ### 总结 
 * 这个类的主要功能是提供一种结构化的方式来封装场景节点列表请求所需的各种参数（如实时主机状态、节点ID、系统类型名称等）。通过重写 `getQuery` 方法，它可以灵活地根据不同的参数组合生成查询条件。这种设计使得在处理复杂的查询逻辑时更加清晰和易于维护。 
**/
@Getter
@Setter
@Slf4j
public class SceneNodeListReq extends TopoNodeListReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 实时主机状态
	 */
	private Boolean realTimeHostState;

	/**
	 * 筛选节点id
	 */
	private List<String> elementIds;

	/**
	 * 系统类型名称
	 */
	private String systemTypeName;

	@Override
	protected List<String> getQuery() {
		List<String> queries = super.getQuery();
		if (Boolean.TRUE.equals(this.realTimeHostState)) {
			queries.add("realTimeHostState=true");
		} else {
			queries.add("realTimeHostState=false");
		}
		if (StringUtils.isNotBlank(this.systemTypeName)) {
			queries.add("systemTypeName=" + this.systemTypeName);
		}
		if (CollectionUtils.isNotEmpty(elementIds)) {
			queries.add("elementIds=" + String.join(",", this.elementIds));
		}
		return queries;
	}
}

