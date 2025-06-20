package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Office 文件信息 bo
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `OfficeFileInfoBo` 的 Java 类，该类用于表示与 Office 文件相关的业务对象（BO）。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.gateway.service.bo` 
 * - **类名**：`OfficeFileInfoBo` 
 * - **实现接口**：`Serializable`，表示该类的对象可以被序列化，以便在网络上传输或持久化存储。 
 * ### 注解 
 * - `@ApiModel("office文件信息")`：使用 Swagger 注解来描述这个 BO 对象的用途。它表示这是一个描述 Office 文件信息的模型。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成所有字段的 getter 和 setter 方法。 
 * ### 字段 
 * - `BaseFileName`：基础文件名称。 
 * - `OwnerId`：文件的所有者 ID。 
 * - `Size`：文件大小。 
 * - `SHA256`：文件的 SHA256 哈希值。 
 * - `Version`：文件版本。 
 * ### 总结 
 * - 这个类主要用于存储和传输有关 Office 文件的信息。 
 * - 它包含五个属性：文件的基础名称、所有者 ID、大小、SHA256 哈希值和版本。 
 * - 通过 Lombok 注解自动提供了 getter 和 setter 方法，使得代码更简洁。 
 * - 使用 Swagger 注解帮助文档生成工具理解这个类的用途。 
 * 这个类的设计简单明了，适合在业务层中作为数据传输对象（DTO）使用。 
**/
@Getter
@Setter
@ApiModel("office文件信息")
public class OfficeFileInfoBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 基础文件名称
	 */
	private String BaseFileName;

	/**
	 * 所有者id
	 */
	private String OwnerId;

	/**
	 * 大小
	 */
	private String Size;

	/**
	 * SHA256
	 */
	private String SHA256;

	/**
	 * 版本
	 */
	private String Version;

}

