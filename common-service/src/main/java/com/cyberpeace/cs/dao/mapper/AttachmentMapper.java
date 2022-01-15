package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.Attachment;

@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	List<Attachment> selectBatchIds(@Param("attachmentIds") List<String> attachmentIds,
			@Param("isPublic") Boolean isPublic);

	Attachment selectByPath(@Param("filePath") String filePath);

	void batchInsert(@Param("attachments") List<Attachment> attachments);
}
