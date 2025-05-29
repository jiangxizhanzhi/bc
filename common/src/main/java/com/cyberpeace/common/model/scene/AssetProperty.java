package com.cyberpeace.common.model.scene;

/**
 * 资产属性
 *
 * @author SN
 * @date 2023/05/30
 */
/**
 * 这段代码定义了一个名为 `AssetProperty` 的接口，用于描述资产相关的属性常量。以下是简要分析： 
 * 1. **功能**： 
 * 该接口主要用于存储与资产相关的固定字符串常量，避免硬编码，提高代码的可维护性。 
 * 2. **常量定义**： 
 * - `SYSTEM_TYPE`：表示系统的类型。 
 * - `SYSTEM_TYPE_NAME`：表示系统类型的名称。 
 * - `ID`：表示资产的唯一标识符。 
 * 3. **设计意图**： 
 * - 使用接口来存储常量是一种常见的 Java 编程实践，便于集中管理。 
 * - 常量均为 `public static final` 类型，符合 Java 的命名规范。 
 * 4. **适用场景**： 
 * 此接口适用于需要统一管理资产相关属性的场景，例如在配置文件、数据库查询或系统模块间传递这些固定值时。 
 * 总结：这是一个简单的常量接口，提供了资产属性的标准化定义，有助于提升代码的清晰度和一致性。 
**/
public interface AssetProperty {

	/**
	 * 系统类型
	 */
	String SYSTEM_TYPE = "system_type";

	/**
	 * 系统类型名称
	 */
	String SYSTEM_TYPE_NAME = "system_type_name";

	/**
	 * id
	 */
	String ID = "id";
}

