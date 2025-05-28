/**
 * 该代码定义了一个 `AttachmentMapper` 接口，用于操作数据库中的 `Attachment` 表。以下是代码的主要分析： 
 * 1. **继承关系**： 
 * - `AttachmentMapper` 继承自 `BaseMapper<Attachment>`，这是 MyBatis-Plus 提供的一个基础接口，提供了通用的 CRUD 操作。 
 * 2. **方法说明**： 
 * - `selectBatchIds`：根据附件 ID 列表和是否公开的条件查询多个附件对象。 
 * - `selectByPath`：通过文件路径查询单个附件对象。 
 * - `batchInsert`：批量插入附件数据。 
 * 3. **注解**： 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - 使用了 `@Param` 注解来指定方法参数的名称，便于在 XML 或注解方式中引用。 
 * 4. **功能描述**： 
 * - 该接口主要用于管理附件相关的数据库操作，包括查询、插入等常见操作。 
 * 总体而言，这段代码是一个典型的 MyBatis-Plus Mapper 接口，封装了与附件相关的数据库操作逻辑。 
**/
package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.Attachment;

/**
 * 附件Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	/**
	 * 查询批量id列表
	 *
	 * @param attachmentIds 附件id列表
	 * @param isPublic      是否公开
	 * @return {@code List<Attachment> }
	 */
	List<Attachment> selectBatchIds(@Param("attachmentIds") List<String> attachmentIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询由路径
	 *
	 * @param filePath 文件路径
	 * @return {@code Attachment }
	 */
	Attachment selectByPath(@Param("filePath") String filePath);

	/**
	 * 批量插入
	 *
	 * @param attachments 附件
	 */
	void batchInsert(@Param("attachments") List<Attachment> attachments);
}
