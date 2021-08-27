package com.cyberpeace.cr.tool.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_tool_ghdb")
public class ToolGhdb implements Serializable {

	@TableId
	private Integer id;

	private String title;

	private Integer ghdbId;

	private String googleSearch;

	private String author;

	private String published;

	private String googleDorkDescription;

	private String content;

	private Integer status;

	private String createTime;
}
