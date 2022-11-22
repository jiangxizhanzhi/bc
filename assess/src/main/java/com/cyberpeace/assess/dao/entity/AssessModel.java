package com.cyberpeace.assess.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估数据
 * @TableName assess_model
 */
@TableName(value = "assess_model")
@Data
public class AssessModel extends BaseTable {

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
	 * 版本号
	 */
	private String visionId;

	/**
	 * 标签
	 */
	private String tag;

	/**
	 * 封面
	 */
	private String logo;

	/**
	 * 输入数据集类型。1：超链接，2：数据库，3：可执行文件，4：其他类型
	 */
	private Integer interfaceType;

	/**
	 * 数据集路径
	 */
	private String inputData;

	/**
	 * 附件地址
	 */
	private String attachmentUrl;

	/**
	 * 超链接域名
	 */
	private String urlDomain;

	/**
	 * 数据库内容
	 */
	private String excelData;

	/**
	 * 参数列表
	 */
	private String params;

}