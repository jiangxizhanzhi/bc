package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.constant.ModelConstant;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@TableName("cr_tool_tool")
public class Tool extends BaseTable {

	private Integer status = ModelConstant.NOT_DELETE;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String cover;

	@Deprecated
	private String homepage;

	private String category;

	@Deprecated
	private String manual;

	private String imageResourceId;

	private Boolean visible;
	// 0 离线， 1在线
	private Integer toolType;

	private String platforms;

	private String language;

	private String introduction;

	@Deprecated
	@TableField("public")
	private Integer toolPublic;

	private Boolean cracked;

	@Deprecated
	private String tag;

	private Date publishTime;

	private String scene;

	private String version;

	private String files;

	@TableField(exist = false)
	private String coverUrl;

	public String getCoverUrl() {
		if (StringUtils.isBlank(cover)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + cover;
	}

	private static final long serialVersionUID = 1L;
}