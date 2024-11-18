package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * 系统属性
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("system_property")
public class SystemProperty implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@TableField("`desc`")
	private String desc;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 名称枚举
	 *
	 * @author SN
	 * @date 2024/11/07
	 */
	public enum NameEnum implements GenericEnum {

		/**
		 * 导出内置
		 */
		EXPORT_BUILTIN(1, "export_builtin"),
		/**
		 * 是展会情况
		 */
		IS_SHOW_SITUATION(2, "isShowSituation");

		/**
		 * 代号
		 */
		@Getter
		private final Integer code;

		/**
		 * 描述
		 */
		@Getter
		private final String desc;

		NameEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
