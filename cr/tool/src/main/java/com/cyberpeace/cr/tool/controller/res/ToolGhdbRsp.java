package com.cyberpeace.cr.tool.controller.res;

import java.io.Serializable;

import lombok.Data;

/**
 * 工具GHDB 数据库响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ToolGhdbRsp implements Serializable {

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 作者
	 *
	 * @see String
	 */
	private String author;
}
