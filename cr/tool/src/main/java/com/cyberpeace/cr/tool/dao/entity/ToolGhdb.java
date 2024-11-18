package com.cyberpeace.cr.tool.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 工具 GHDB
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("cr_tool_ghdb")
public class ToolGhdb implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId
	private Integer id;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * GHDB 数据库id
	 *
	 * @see Integer
	 */
	private Integer ghdbId;

	/**
	 * Google 搜索
	 *
	 * @see String
	 */
	private String googleSearch;

	/**
	 * 作者
	 *
	 * @see String
	 */
	private String author;

	/**
	 * 发表
	 *
	 * @see String
	 */
	private String published;

	/**
	 * Google Dork 说明
	 *
	 * @see String
	 */
	private String googleDorkDescription;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	private Integer status;

	/**
	 * 创建时间
	 *
	 * @see String
	 */
	private String createTime;
}
