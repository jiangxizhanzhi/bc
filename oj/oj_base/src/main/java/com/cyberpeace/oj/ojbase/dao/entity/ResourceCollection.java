package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

/**
 * 资源收集
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_resource_collection")
public class ResourceCollection extends BaseTable {

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
	 * 业务id
	 */
	private String businessId;

	/**
	 * 业务类型
	 */
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
