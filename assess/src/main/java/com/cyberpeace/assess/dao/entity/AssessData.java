package com.cyberpeace.assess.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估数据
 * @TableName assess_data
 */
@TableName(value = "assess_data")
@Data
public class AssessData extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建人uuid
	 */
	private String createUserId;

	/**
	 * 标签
	 */
	private String tag;

	/**
	 * 封面
	 */
	private String logo;

	/**
	 * 评估数据类型。1：输入，2：结果
	 */
	private Integer type;

	/**
	 * 输入数据集类型。1：mysql，2：excel
	 */
	private Integer inputType;

	/**
	 * 上传文件名称
	 */
	private String fileName;

	/**
	 * 数据集路径
	 */
	private String inputData;

	/**
	 * 结果路径
	 */
	private String resultData;

}