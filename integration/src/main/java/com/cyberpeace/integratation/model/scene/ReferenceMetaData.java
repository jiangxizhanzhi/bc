/**
 * 这段代码定义了一个名为 `ReferenceMetaData` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示引用元数据，并包含了几个字段以及对应的 getter 和 setter 方法。以下是详细的分析： 
 * ### 类定义 
 * - **类名**：`ReferenceMetaData` 
 * - **包名**：`com.cyberpeace.integratation.model.scene` 
 * - **实现接口**：`Serializable` 
 * ### 字段 
 * 1. **business_id**（String 类型）：业务ID。 
 * 2. **business_name**（String 类型）：业务名称。 
 * 3. **business_code**（Integer 类型）：业务代码，注释说明了其用途（1xx 表示实训，2xx 表示靶场）。 
 * 4. **is_web**（Boolean 类型）：是否为 OJ 专用，当前未使用。 
 * 5. **create_user_id**（String 类型）：创建用户的 ID。 
 * ### 方法 
 * 每个字段都有对应的 getter 和 setter 方法： 
 * - `getBusiness_id()` 和 `setBusinessId(String business_id)` 
 * - `getBusiness_name()` 和 `setBusinessName(String business_name)` 
 * - `getBusiness_code()` 和 `setBusinessCode(Integer business_code)` 
 * - `getIs_web()` 和 `setFromWeb(Boolean from_web)` (注意 setter 方法名与字段名不一致) 
 * - `getCreate_user_id()` 和 `setCreateUserId(String create_user_id)` (注意 setter 方法名与字段名不一致) 
 * ### 总结 
 * - **优点**： 
 * - 类的设计清晰，字段和方法命名合理。 
 * - 实现了 `Serializable` 接口，方便对象的序列化操作。 
 * - **缺点**： 
 * - 部分 setter 方法名与字段名不一致，例如 `setFromWeb` 对应 `is_web` 字段，这可能会导致混淆。 
 * - 注释信息详细，有助于理解字段的作用。 
 * ### 改进建议 
 * - 统一 setter 方法名与字段名，以保持一致性。 
 * - 如果某些字段目前未使用，可以考虑将其标记为 `@Deprecated` 或删除以保持代码简洁。 
 * 总体来说，这是一个设计良好的类，但可以通过一些小的改进使其更加完善。 
**/
package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

/**
 * 引用元数据
 *
 * @author SN
 * @date 2024/11/08
 */
public class ReferenceMetaData implements Serializable {

	/**
	 * 业务id
	 */
	private String business_id;

	/**
	 * 业务名称
	 */
	private String business_name;

	/**
	 * 1xx=实训；2xx=靶场
	 */
	private Integer business_code;

	/**
	 * oj专用，暂时不用
	 */
	private Boolean is_web;

	/**
	 * 创造用户id
	 */
	private String create_user_id;

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusinessId(String business_id) {
		this.business_id = business_id;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusinessName(String business_name) {
		this.business_name = business_name;
	}

	public Integer getBusiness_code() {
		return business_code;
	}

	public void setBusinessCode(Integer business_code) {
		this.business_code = business_code;
	}

	public Boolean getIs_web() {
		return is_web;
	}

	public void setFromWeb(Boolean from_web) {
		this.is_web = from_web;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreateUserId(String create_user_id) {
		this.create_user_id = create_user_id;
	}
}
