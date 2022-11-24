package com.cyberpeace.assess.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估标签表
 * @TableName assess_file_data
 */
@TableName(value = "assess_file_data")
@Data
public class AssessFileData extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 文件解析内容
	 */
	private String content;

	/**
	 * 文件地址
	 */
	private String path;

	/**
	 * 关联id
	 */
	private String relatedId;


}