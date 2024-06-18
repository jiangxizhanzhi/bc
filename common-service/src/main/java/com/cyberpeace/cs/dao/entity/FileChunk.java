package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

@Data
@TableName("file_chunk")
public class FileChunk {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String md5;

	private String filename;

	private Long filesize;

	private Integer total;

	@TableField(value = "`index`")
	private Integer index;

	private Integer status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
