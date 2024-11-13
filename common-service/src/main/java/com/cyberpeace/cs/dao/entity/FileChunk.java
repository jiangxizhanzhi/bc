package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

/**
 * 文件块
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("file_chunk")
public class FileChunk {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * md5
	 */
	private String md5;

	/**
	 * 文件名
	 */
	private String filename;

	/**
	 * 文件大小
	 */
	private Long filesize;

	/**
	 * 总
	 */
	private Integer total;

	/**
	 * 指数
	 */
	@TableField(value = "`index`")
	private Integer index;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
