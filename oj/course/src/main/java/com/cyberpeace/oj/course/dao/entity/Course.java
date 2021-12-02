package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseTable;

import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Data
@TableName("oj_course")
public class Course extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Boolean builtin;

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
			String startStr = ConfigUtil.getContextProperty("common.fastdfs.url.start.str");
			if (StringUtils.isNotBlank(fastdfsPrefix) && this.logo.startsWith(startStr)) {
				return fastdfsPrefix + this.logo;
			}
		}
		return this.logo;
	}

	public enum Difficulty implements GenericEnum {

		PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Difficulty(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
