package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@TableName("oj_course")
public class Course extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Boolean builtin;

	private Integer operationAuthType;

	private String operationAuthIds;

	private Integer readAuthType;

	private String readAuthIds;

	private String name;

	private String description;

	private String categoryId;

	private String logo;

	private String outline;

	private Integer difficulty;

	private String createUserId;

	private String modifyUserId;

	@TableField("public")
	private Boolean isPublic;

	private Integer courseType;

	private Integer heat;

	private String lecturer;

	private Integer score;

	private String courseNum;

	public String getLogo() {
		if (StringUtils.isNotBlank(this.logo)) {
			String fastdfsPrefix = ConfigUtil.getContextProperty("common.fastdfs.url.prefix");
			if (StringUtils.isNotBlank(fastdfsPrefix)) {
				return fastdfsPrefix + this.logo;
			}
			return this.logo;
		}
		return this.logo;
	}
}
