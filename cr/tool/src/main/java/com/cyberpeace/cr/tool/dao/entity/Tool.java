package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.constant.ModelConstant;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_tool_tool")
public class Tool extends BaseTable {

	enum Type{
		OFFLINE(0), // 离线
		ONLINE(1); // 在线
		private final Integer value;
		Type(Integer value) {
			this.value = value;
		}
	}
	private Integer status = ModelConstant.NOT_DELETE;

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

	private String scene;

	private String version;

	private String files;

	private static final long serialVersionUID = 1L;
}