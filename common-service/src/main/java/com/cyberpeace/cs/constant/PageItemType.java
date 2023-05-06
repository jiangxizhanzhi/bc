package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;

/**
 * 页面项目类型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum PageItemType implements GenericEnum {

	/** 页面配置项类型*/
	COURSE_DIFFICULTY(0, "课程难度", MenuConstant.WEB_OJ_COURSE, MenuConstant.CMS_COURSES),
	/**
	 * 理论难度
	 */
	THEORY_DIFFICULTY(1, "理论习题难度", MenuConstant.WEB_OJ_THEORY_PRACTICE,
			MenuConstant.CMS_THEORY_PRACTICE),
	/**
	 * 操作难度
	 */
	OPERATION_DIFFICULTY(2, "实操习题难度", MenuConstant.WEB_OJ_PRACTICE_OPERATION,
			MenuConstant.CMS_PRACTICE_OPERATION),
	/**
	 * 理论状态
	 */
	THEORY_STATUS(3, "理论习题状态", MenuConstant.WEB_OJ_THEORY_PRACTICE),
	/**
	 * 操作状态
	 */
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

	/**
	 * 页面项目类型
	 *
	 * @param type    类型
	 * @param desc    desc
	 * @param menuIds 菜单id
	 */
	PageItemType(int type, String desc, String... menuIds) {
		this.code = type;
		this.desc = desc;
		this.menuIds = menuIds;
	}

	/**
	 * 验证权限
	 *
	 * @return boolean
	 */
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