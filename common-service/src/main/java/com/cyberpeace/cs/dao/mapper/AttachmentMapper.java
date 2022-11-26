package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

	List<Attachment> selectBatchIds(@Param("attachmentIds") List<String> attachmentIds,
			@Param("isPublic") Boolean isPublic);

	Attachment selectByPath(@Param("filePath") String filePath);

	void batchInsert(@Param("attachments") List<Attachment> attachments);

	int insertAttachment(@Param("resourceId") String resourceId,@Param("fileName") String fileName,@Param("filePath") String filePath,@Param("isPublic") Boolean isPublic);
}
