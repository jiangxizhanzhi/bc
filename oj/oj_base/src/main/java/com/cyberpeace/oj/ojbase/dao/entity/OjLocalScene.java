package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@TableName("oj_local_scene")
public class OjLocalScene extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String userId;

	private Boolean shared;

	private Boolean fromWeb;

	private String flags;

	private String businessId;

	private Integer businessType;

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
