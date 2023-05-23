package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@TableName("system_property")
public class SystemProperty implements Serializable {

	private String name;

	private String desc;

	private String content;

	public enum NameEnum implements GenericEnum {

		EXPORT_BUILTIN(1, "export_builtin"),
		IS_SHOW_SITUATION(2, "isShowSituation");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		NameEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
