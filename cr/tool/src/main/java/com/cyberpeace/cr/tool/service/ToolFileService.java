/**
 * 这段代码定义了一个名为 `ToolFileService` 的接口，该接口继承自 `IService<ToolFile>`。`IService` 是 MyBatis-Plus 框架中提供的一个通用 Service 接口，用于简化 CRUD 操作。 
 * ### 主要功能 
 * 1. **继承关系**: 
 * - `ToolFileService` 继承了 `IService<ToolFile>`，这表示它提供了基本的增删改查操作，并且这些操作是针对 `ToolFile` 类型的数据进行的。 
 * 2. **方法定义**: 
 * - `saveFile(String filePath, boolean transferName)`: 该方法用于保存工具关联的附件，输入参数包括文件路径 (`filePath`) 和一个布尔值 (`transferName`) 表示是否替换文件名。返回值是一个 `ToolFile` 对象。 
 * - `saveFile(MultipartFile multipartFile)`: 该方法用于保存工具关联的附件，输入参数是一个 `MultipartFile` 对象，通常是从 HTTP 请求中上传的文件。返回值也是一个 `ToolFile` 对象。 
 * ### 总结 
 * - `ToolFileService` 接口定义了两个保存文件的方法：一个用于处理文件路径，另一个用于处理上传的文件对象。 
 * - 这些方法的返回类型都是 `ToolFile`，这意味着它们会返回与文件关联的数据对象。 
 * - 通过继承 `IService<ToolFile>`，该接口还自动获得了 MyBatis-Plus 提供的基本 CRUD 操作的支持。 
 * 这个接口的设计目的是为了提供一种方便的方式来处理工具相关的文件保存操作，并且可以灵活地处理不同的文件来源（文件路径或上传的文件）。 
**/
package com.cyberpeace.cr.tool.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;

/**
 * 工具文件Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface ToolFileService extends IService<ToolFile> {

	/**
	 * 保存工具关联附件（文件链接型）
	 *
	 * @param filePath     文件链接
	 * @param transferName 是否替换文件名
	 * @return 工具附件数据对象
	 */
	ToolFile saveFile(String filePath, boolean transferName);

	/**
	 * 保存工具关联附件（文件数据流型）
	 *
	 * @param multipartFile 文件数据对象
	 * @return 工具附件数据对象
	 */
	ToolFile saveFile(MultipartFile multipartFile);
}
