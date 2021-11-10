package com.cyberpeace.oj.ojbase.dao.entity;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
public class ResourceCollection extends BaseTable {

	private String userId;

	private String businessId;

	private Integer businessType;

	public enum BusinessType implements GenericEnum {

		THEORY(0, "理论基础"),
		@Deprecated
		REAL_VUL(1, "真实漏洞"), OPERATION(2, "实践操作"),
		COURSE(3, "课程"), LESSON(4, "课时");

		@Getter
		private Integer code;
		@Getter
		private String desc;

		BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
