package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;

public enum PageItemType implements GenericEnum {

	/** 页面配置项类型*/
	COURSE_DIFFICULTY(0, "课程难度", MenuConstant.WEB_OJ_COURSE, MenuConstant.CMS_COURSES),
	THEORY_DIFFICULTY(1, "理论习题难度", MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.CMS_THEORY_PRACTICE),
	OPERATION_DIFFICULTY(2, "实操习题难度", MenuConstant.WEB_OJ_PRACTICE_OPERATION, MenuConstant.CMS_PRACTICE_OPERATION),
	THEORY_STATUS(3, "理论习题状态", MenuConstant.WEB_OJ_THEORY_PRACTICE),
	OPERATION_STATUS(4, "实操习题状态", MenuConstant.WEB_OJ_THEORY_PRACTICE);

	private int code;

	private String desc;

	private String[] menuIds;

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	PageItemType(int type, String desc, String... menuIds) {
		this.code = type;
		this.desc = desc;
		this.menuIds = menuIds;
	}

	public boolean validatePermission() {

		Boolean access = false;
		for (String menuId : menuIds) {
			if (access = SecurityUtils.getSubject().isPermitted(menuId)) {
				break;
			}
		}

		return access;
	}
}