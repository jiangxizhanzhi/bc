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

/**
 * 注意
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_note")
public class Note extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("笔记编号")
	private String resourceId;

	/**
	 * 注释类型
	 */
	@ApiModelProperty("笔记类型")
	private Integer noteType;

	/**
	 * 业务类型
	 */
	@ApiModelProperty("业务类型")
	private Integer businessType;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 业务id
	 */
	@ApiModelProperty("业务编号")
	private String businessId;

	/**
	 * 是公共
	 */
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 是提交
	 */
	@ApiModelProperty("实验报告是否提交")
	private Boolean isSubmit;

	public enum NoteType implements GenericEnum {

		NOTE(1, "笔记"), REPORT(2, "实验报告");

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
