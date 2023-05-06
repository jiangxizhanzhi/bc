package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.Attachment;

/**
 * 附件映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	/**
	 * 查找id列表
	 *
	 * @param attachmentIds 附件标识
	 * @param isPublic      是公共
	 * @return {@link List}<{@link Attachment}>
	 */
	List<Attachment> selectBatchIds(@Param("attachmentIds") List<String> attachmentIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 选择路径
	 *
	 * @param filePath 文件路径
	 * @return {@link Attachment}
	 */
	Attachment selectByPath(@Param("filePath") String filePath);

	/**
	 * 批量插入
	 *
	 * @param attachments 附件
	 */
	void batchInsert(@Param("attachments") List<Attachment> attachments);
}
