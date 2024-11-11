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
 * @date 2024/11/11
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

	/**
	 * 业务类型
	 *
	 * @author SN
	 * @date 2024/11/11
	 */
	public enum BusinessType implements GenericEnum {

		/**
		 * 理论
		 */
		THEORY(0, "理论基础"),
		/**
		 * 真正漏洞
		 */@Deprecated
		REAL_VUL(1, "真实漏洞"),
		/**
		 * 实操
		 */
		OPERATION(2, "实践操作"),
		/**
		 * 课程
		 */
		COURSE(3, "课程"),
		/**
		 * 课时
		 */
		LESSON(4, "课时");

		/**
		 * 代号
		 */
		@Getter
		private Integer code;
		/**
		 * 描述
		 */
		@Getter
		private String desc;

		BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
