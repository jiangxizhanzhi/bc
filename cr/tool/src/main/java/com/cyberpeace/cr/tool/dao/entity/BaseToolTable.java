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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BaseToolTable implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	private Integer id;

	/**
	 * 状态
	 *
	 * @see String
	 */
	private String status;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 教育局id
	 *
	 * @see Integer
	 */
	private Integer edbId;

	/**
	 * 作者
	 *
	 * @see String
	 */
	private String author;

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 平台
	 *
	 * @see String
	 */
	private String platform;

	/**
	 * 后缀
	 *
	 * @see String
	 */
	private String suffix;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

}
