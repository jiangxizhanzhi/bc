package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("cr_tool_tool")
public class Tool implements Serializable {

	private Integer id;

	private Integer status;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String cover;

	private String homepage;

	private String category;

	private Integer attribute;

	private String manual;

	private String imageResourceId;

	private Boolean visible;

	private Integer toolType;

	private String platforms;

	private String language;

	private String introduction;

	@TableField("public")
	private Integer toolPublic;

	private Boolean cracked;

	private String tag;

	private Date publishTime;

	private Date createTime;

	private Date updateTime;

	private String scene;

	private String version;

	private String files;

	private static final long serialVersionUID = 1L;
}