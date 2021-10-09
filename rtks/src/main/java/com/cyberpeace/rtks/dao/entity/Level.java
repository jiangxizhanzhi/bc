package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_level")
public class Level {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * Frame是Level的一种特殊形式，表示顶级的层级 其parent=null frame=null
	 */

	private String name;

	private String description;

	/**
	 * 当前层级的父级resourceId 如果是顶级(Frame)则为空
	 */
	private String parent;

	/**
	 * 当前层级所属的框架(Frame)的resourceId
	 */
	private String frame;

}
