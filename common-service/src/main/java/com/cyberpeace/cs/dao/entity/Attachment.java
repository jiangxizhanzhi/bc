package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	private String fileName;

	/**
	 * 文件路径
	 *
	 * @see String
	 */
	private String filePath;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("public")
	private Boolean isPublic;

}
