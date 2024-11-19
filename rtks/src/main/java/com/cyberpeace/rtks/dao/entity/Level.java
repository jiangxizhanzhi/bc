package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 水平
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("rtks_level")
public class Level extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * Frame是Level的一种特殊形式，表示顶级的层级 其parent=null frame=null
	 *
	 * @see String
	 */

	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 当前层级的父级resourceId 如果是顶级(Frame)则为空
	 *
	 * @see String
	 */
	private String parent;

	/**
	 * 当前层级所属的框架(Frame)的resourceId
	 *
	 * @see String
	 */
	private String frame;

	public enum DefaultName {

		/***/
		ENGLISH(0, "Network Security"),
		CHINESE(1, "网络安全框架");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		DefaultName(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
