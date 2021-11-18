package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Type;

@Data
@TableName("oj_note")
public class Note extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer noteType;

	private Integer businessType;

	private String content;

	private String userId;

	private String businessId;

	@TableField("public")
	private Boolean isPublic;

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

		COURSE(1, "课程"), LESSON(2, "课时"), PRACTICE(3, "练习"), LESSON_PAPER(4, "课后练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NoteResourceType(Integer code, String desc) {

		}

	}

}
