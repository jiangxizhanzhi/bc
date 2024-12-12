package com.cyberpeace.cr.tool.controller.res;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 工具GHDB 数据库响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ToolGhdbRsp implements Serializable {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 作者
	 */
	private String author;
}
