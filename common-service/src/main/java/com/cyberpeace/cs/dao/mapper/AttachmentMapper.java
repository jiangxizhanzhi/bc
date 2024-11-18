package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.Attachment;

/**
 * 附件映射器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	/**
	 * 查询批量id列表
	 *
	 * @param attachmentIds 附件id列表
	 * @param isPublic      是否公开
	 * @return {@code List<Attachment> }
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 * @see List<Attachment>
	 */
	List<Attachment> selectBatchIds(@Param("attachmentIds") List<String> attachmentIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询由路径
	 *
	 * @param filePath 文件路径
	 * @return {@code Attachment }
	 * @throws
	 * @see String
	 * @see Attachment
	 */
	Attachment selectByPath(@Param("filePath") String filePath);

	/**
	 * 批量插入
	 *
	 * @param attachments 附件
	 * @throws
	 * @see List<Attachment>
	 */
	void batchInsert(@Param("attachments") List<Attachment> attachments);
}
