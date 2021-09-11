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
@TableName("course_course")
public class Course extends BaseTable {

	/** 初级*/
	public static final int PRIMARY = 0;

	/** 中级*/
	public static final int MIDDLE = 1;

	/** 高级*/
	public static final int SENIOR = 2;

	/** 公开*/
	public static final int IS_PUBLIC = 1;

	/** 共享所有*/
	public static final int ALL_SHARE_MODE = 1;

	/** 自定义共享*/
	public static final int CUSTOM_SHARE_MODE = 2;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Boolean builtin;

	private Integer auth;

	private String authOrganization;

	private Integer share;

	private String shareTeachers;

	private String name;

	private String description;

	private String direction;

	private String logo;

	private String courseWriteup;

	private Integer difficulty;

	private String createUser;

	private String lastEditUser;

	@TableField("public")
	private Boolean isPublic;

	private Integer eBook;

	private Integer status;

	private Integer heat;

	private String lecturer;

	private Integer score;

	@TableField(exist = false)
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
