package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础刀具表
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BaseToolTable implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 教育局id
	 */
	private Integer edbId;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 后缀
	 */
	private String suffix;

	/**
	 * 内容
	 */
	private String content;

}
