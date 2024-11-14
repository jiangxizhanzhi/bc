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
 * @date 2024/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_local_scene")
public class OjLocalScene extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 共享
	 */
	private Boolean shared;

	/**
	 * 从Web
	 */
	private Boolean fromWeb;

	/**
	 * 标志
	 */
	private String flags;

	/**
	 * 业务id
	 */
	private String businessId;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 当地场景id
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
