package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("bs_user_behavior")
public class UserBehavior extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("id")
	private String resourceId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("简介")
	private String description;

	@ApiModelProperty("用户类型")
	private Integer userType;

	@ApiModelProperty("活跃度")
	private Integer activity;

	@ApiModelProperty("访问带宽")
	private Integer bandwidth;

	@ApiModelProperty("访问习惯")
	private Integer accessOs;

	@ApiModelProperty("访问配置")
	private Integer accessConfig;

	@ApiModelProperty("访问行为")
	private String behaviorList;

	public enum UserType implements GenericEnum {

		CODER(1, "程序员"), TEACHER(2, "教师"), STUDENT(3, "学生");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private UserType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum Activity implements GenericEnum {

		CODER(1, "程序员"), TEACHER(2, "教师"), STUDENT(3, "学生");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Activity(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum AccessOs implements GenericEnum {

		CODER(1, "程序员"), TEACHER(2, "教师"), STUDENT(3, "学生");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AccessOs(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum AccessConfig implements GenericEnum {

		CODER(1, "程序员"), TEACHER(2, "教师"), STUDENT(3, "学生");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AccessConfig(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
