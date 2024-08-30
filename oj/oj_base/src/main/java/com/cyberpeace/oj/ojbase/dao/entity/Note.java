package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_note")
public class Note extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("笔记编号")
	private String resourceId;

	@ApiModelProperty("笔记类型")
	private Integer noteType;

	@ApiModelProperty("业务类型")
	private Integer businessType;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("业务编号")
	private String businessId;

	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("实验报告是否提交")
	private Boolean isSubmit;

	public enum NoteType implements GenericEnum {

		NOTE(1, "笔记"), REPORT(2, "实验报告"), CLASS_REPORT(3, "课堂教学报告");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NoteType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum NoteResourceType implements GenericEnum {

		COURSE(1, "课程"),
		LESSON(2, "课时"),
		OPTION(3, "实操题"),
		LESSON_PAPER(4, "课后练习"),
		THEORY(5, "理论题");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NoteResourceType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
