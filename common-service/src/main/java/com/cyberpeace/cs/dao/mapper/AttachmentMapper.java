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
 * @date 2024/11/07
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	/**
	 * 查询批量id列表
	 *
	 * @param attachmentIds 附件id列表
	 * @param isPublic      是公共
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
