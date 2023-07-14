package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

/**
 * 
 * @TableName system_property
 */
@TableName(value = "system_property")
@Data
public class SystemProperty implements Serializable {

	/**
	 * 
	 */
	@TableId
	private Integer id;

	/**
	 * 配置名称
	 */
	private String name;

	/**
	 * 配置描述
	 */
	@TableField("`desc`")
	private String desc;

	/**
	 * 配置值
	 */
	private String content;

	private Integer type;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	private enum SystemPropertyType implements GenericEnum {

		URL(1, "外网路径");

		@Getter
		Integer type;

		@Getter
		String desc;

		SystemPropertyType(Integer type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		@Override
		public Integer getCode() {
			return getType();
		}
	}

}