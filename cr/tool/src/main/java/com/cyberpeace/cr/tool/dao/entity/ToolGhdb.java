package com.cyberpeace.cr.tool.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 工具 GHDB
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_tool_ghdb")
public class ToolGhdb implements Serializable {

	/**
	 * id
	 */
	@TableId
	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * GHDB 数据库id
	 */
	private Integer ghdbId;

	/**
	 * Google 搜索
	 */
	private String googleSearch;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 发表
	 */
	private String published;

	/**
	 * Google Dork 说明
	 */
	private String googleDorkDescription;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private String createTime;
}
