package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;

/**
 * OJ当地场景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_local_scene")
public class OjLocalScene extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 共享
	 *
	 * @see Boolean
	 */
	private Boolean shared;

	/**
	 * 从 Web
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

	/**
	 * 标志
	 *
	 * @see String
	 */
	private String flags;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		LESSON(1, "课时"), OPERATION(2, "实操题"), CLASSROOM(3, "课堂");

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
