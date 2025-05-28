/**
 * 这段代码定义了一个名为 `LocalSceneMeta` 的 Java 类，位于 `com.cyberpeace.cs.dao.entity` 包中。以下是代码的主要分析和总结： 
 * ### 功能概述： 
 * - `LocalSceneMeta` 是一个实体类，用于表示“当地场景元”（Local Scene Metadata）的数据模型。 
 * - 它主要用于存储与场景相关的元信息，例如应用资源 ID、拓扑数据以及是否为 Web 场景。 
 * ### 主要特性： 
 * 1. **注解说明**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * - `@Builder`：提供构建器模式，方便对象实例化。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成包含所有字段和无参构造函数。 
 * - 这些注解减少了样板代码，提升了代码简洁性。 
 * 2. **字段说明**： 
 * - `appResourceId`：字符串类型，表示 JSON 格式的元数据，目前保存了 `appResourceId` 和 `topo_data`。 
 * - `appResourceId` 用于前端页面场景小球的路由。 
 * - `topo_data` 用于拓扑结构的描述，便于进行标志注入（Flag Injection）。 
 * - `topoData`：`JSONObject` 类型，表示拓扑数据，用于存储复杂的结构化数据。 
 * - `isWeb`：布尔值，标记当前场景是否为 Web 场景。 
 * 3. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化存储。 
 * ### 总结： 
 * - 该类是一个典型的领域模型（Domain Model），用于封装与“当地场景元”相关的信息。 
 * - 通过 Lombok 注解简化了代码，提高了可读性和开发效率。 
 * - 字段设计清晰，能够满足当前场景的需求，同时保留了扩展性。 
**/
package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

/**
 * 当地场景元
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSceneMeta implements Serializable {

	/**
	 * json格式。
	 * 目前保存appResourceId
	 * {
	 * "app_resource_id":"abcd",
	 * "topo_data":"拓扑结构 用于flag注入"
	 * }
	 * app_resource_id用于前端页面场景小球的路由。对于靶场而言=业务实例id；
	 * 实训习题=习题编号
	 * 实训课时=course_id/lesson_id
	 * 实训课后联系=course_id/lesson_id/paper_id
	 */
	private String appResourceId;

	/**
	 * 拓扑数据
	 */
	private JSONObject topoData;

	/**
	 * 是 Web
	 */
	private Boolean isWeb;
}
