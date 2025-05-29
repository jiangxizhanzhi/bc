package com.cyberpeace.integratation.model.scene;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Getter;

/**
 * 场景开始上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneStartContext` 的类，该类继承自 `SceneOpsContext`。以下是对其主要特性和功能的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.model.scene` 
 * - **父类**：`SceneOpsContext` 
 * - **导入的类**： 
 * - `GenericEnum` 从 `com.cyberpeace.common.baseinterface` 导入。 
 * - `JsonUtil` 从 `com.cyberpeace.common.util` 导入。 
 * - `lombok.Getter` 用于自动生成 getter 方法。 
 * ### 类成员变量 
 * - `reference_meta_data`：字符串类型，表示引用元数据。 
 * - `scene_attr`：整型，表示场景属性。 
 * - `scene_ttl`：整型，表示场景存活时间。 
 * - `cscene`：对象类型，表示场景的大 JSON 对象。 
 * ### 构造方法和成员方法 
 * - `setReferenceMetaData(String reference_meta_data)`：设置引用元数据。 
 * - `setSceneAttr(Integer scene_attr)`：设置场景属性。 
 * - `setSceneTtl(Integer scene_ttl)`：设置场景存活时间。 
 * - `setCscene(Object cscene)`：设置场景大 JSON 对象。 
 * - `getReference_meta_data()`、`getScene_attr()`、`getScene_ttl()` 和 `getCscene()`：获取相应的成员变量。 
 * - `setReferenceMetaData(ReferenceMetaData referenceMetaData)`：将 `ReferenceMetaData` 对象转换为 JSON 字符串并设置引用元数据。 
 * ### 枚举类 
 * - `SceneAttrEnum` 实现了 `GenericEnum` 接口，定义了场景属性的不同枚举值及其对应的代码和描述： 
 * - `NULL`：值为 0，描述为“无隔离”。 
 * - `SINGLE`：值为 1，描述为“个人隔离”。 
 * - `TEAM`：值为 2，描述为“团队隔离”。 
 * ### 注释 
 * - 文档注释提供了关于类用途的基本信息。 
 * - 成员变量和方法也有详细的注释说明其用途和功能。 
 * ### 总结 
 * `SceneStartContext` 类主要用于存储和管理场景启动时所需的上下文信息，包括引用元数据、场景属性、场景存活时间和场景的大 JSON 对象。通过枚举类 `SceneAttrEnum` 定义了场景属性的不同状态，并且提供了相应的方法来设置和获取这些属性。 
**/
public class SceneStartContext extends SceneOpsContext {

	/**
	 * 引用元数据
	 */
	private String reference_meta_data;

	/**
	 * 场景属性
	 */
	private Integer scene_attr;

	/**
	 * 场景存活时间
	 */
	private Integer scene_ttl;

	public void setReferenceMetaData(String reference_meta_data) {
		this.reference_meta_data = reference_meta_data;
	}

	public void setSceneAttr(Integer scene_attr) {
		this.scene_attr = scene_attr;
	}

	public void setSceneTtl(Integer scene_ttl) {
		this.scene_ttl = scene_ttl;
	}

	public void setCscene(Object cscene) {
		this.cscene = cscene;
	}

	public String getReference_meta_data() {
		return reference_meta_data;
	}

	public Integer getScene_attr() {
		return scene_attr;
	}

	public Integer getScene_ttl() {
		return scene_ttl;
	}

	public Object getCscene() {
		return cscene;
	}

	/**
	 * 场景大json。
	 * 该字段传入的情况下，url中拓扑id不起作用。
	 */
	private Object cscene;

	public void setReferenceMetaData(ReferenceMetaData referenceMetaData) {
		this.reference_meta_data = JsonUtil.toJsonString(referenceMetaData);
	}

	public enum SceneAttrEnum implements GenericEnum {

		NULL(0, "无隔离"), SINGLE(1, "个人隔离"), TEAM(2, "团队隔离");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private SceneAttrEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}

