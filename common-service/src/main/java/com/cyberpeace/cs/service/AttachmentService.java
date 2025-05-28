/**
 * 这段代码定义了一个名为 `AttachmentService` 的接口，继承自 `IService<Attachment>`，用于处理与附件相关的业务逻辑。以下是代码的主要功能和特点的简要分析： 
 * 1. **继承关系**： 
 * - `AttachmentService` 继承了 `IService<Attachment>`，表明它是一个基于 MyBatis-Plus 的服务层接口，通常用于数据操作。 
 * 2. **核心方法**： 
 * - `selectByIds`：根据附件编号（逗号分隔）查询附件信息，并支持是否为 Web 场景。 
 * - `batchGetByIds`：批量查询附件信息。 
 * - `create`：创建新的附件对象。 
 * - `uploadFile`：根据类型上传文件到指定文件夹，支持插入数据库及文件重命名。 
 * - `uploadDest`：将文件上传到指定目标文件夹，当前未解决中文乱码问题，文件名会重命名。 
 * - `copyFile`：从临时目录拷贝文件到实际存储目录，支持插入数据库和文件重命名。 
 * - `batchInsert`：批量插入文件路径到数据库。 
 * - `loadImage`：导入场景模板时，将图片存入统一文件夹并插入或更新数据库。 
 * - `loadZiguanImages`：资管平台导入时，批量插入图片附件。 
 * - `isExist`：判断某个附件是否存在。 
 * 3. **设计特点**： 
 * - 方法覆盖了附件的全生命周期管理，包括上传、查询、插入、更新和删除等操作。 
 * - 支持多种上传策略，例如按类型存储、重命名文件等。 
 * - 提供了批量操作的支持，如批量插入和批量查询。 
 * - 注解和注释清晰，便于理解方法的功能和用途。 
 * 总结来说，该接口提供了完整的附件管理功能，适用于需要高效处理文件上传、存储和查询的场景。 
**/
package com.cyberpeace.cs.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.context.AttachmentBo;

/**
 * 附件Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface AttachmentService extends IService<Attachment> {

	/**
	 * 根据附件编号查询附件信息 以,分割
	 *
	 * @param attachmentIds
	 * @param isWeb
	 * @return
	 */
	List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb);

	/**
	 * 批量查询
	 *
	 * @param attachmentIds
	 * @return
	 */
	List<Attachment> batchGetByIds(List<String> attachmentIds);

	/**
	 * 创造
	 *
	 * @param filename
	 * @param filePath
	 * @return
	 */
	AttachmentBo create(String filename, String filePath);

	/**
	 * 根据类型上传到对应的文件夹
	 *
	 * @param type
	 * @param insert
	 * @param file
	 * @param renameFile
	 * @return
	 */
	AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file, Boolean renameFile);

	/**
	 * 文件上传到目的文件夹 文件中文文件乱码 目前没时间解决 暂时上传的全部文件重命名
	 *
	 * @param destDir 目的文件夹
	 * @param insert  是否插入数据库
	 * @param file    上传的源文件
	 * @return
	 */
	AttachmentBo uploadDest(String destDir, boolean insert, MultipartFile file);

	/**
	 * 从临时目录将文件拷贝到实际存储目录
	 *
	 * @param originPath 临时目录
	 * @param type       文件存储地址类型，参考AttachmentPath
	 * @param insert     true表示记录写入数据库
	 * @param renameFile true表示会对文件重命名
	 * @return
	 */
	AttachmentBo copyFile(String originPath, Integer type, Boolean insert, Boolean renameFile);

	/**
	 * 文件目录插入数据库
	 *
	 * @param attachmentPaths
	 * @return
	 */
	List<String> batchInsert(List<String> attachmentPaths);

	/**
	 * 导入场景模板时，将图片存入统一文件夹，插入或更新数据库条目
	 *
	 * @param imageId  图像id
	 * @param fileName 文件名称
	 * @param imageDir 图像目录
	 * @return {@code Boolean }
	 */
	Boolean loadImage(String imageId, String fileName, String imageDir);

	/**
	 * 资管平台导入时，图片附件插入数据库
	 *
	 * @param images 图像
	 */
	void loadZiguanImages(Map<String, String> images);

	/**
	 * 判断文件是否存在
	 *
	 * @param attachmentId 附件id
	 * @return {@code Boolean }
	 */
	Boolean isExist(String attachmentId);

}
