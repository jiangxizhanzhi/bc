package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

/**
 * @author chenzhuo
 */

public enum UploadImagePermission implements GenericEnum {

	/** 文件上传权限枚举*/
	CMS_COURSE_LOGO(1, "后台课程", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_COURSES, MenuConstant.WEB_OJ_COURSE }),
	CMS_PRACTICE_SET_LOGO(2, "后台习题集", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_EXERCISES }),
	WEB_TEAM_IMAGE(3, "前台团队训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_RED_TRAIN,MenuConstant.WEB_CR_BLUE_TRAIN,MenuConstant.WEB_CR_DAILY_TRAIN }),
	CMS_TEAM_IMAGE(4, "后台团队训练", FileTypeContant.PICTURE_TYPE, new String[]{ MenuConstant.CMS_CR_RED_TRAIN, MenuConstant.CMS_CR_BLUE_TRAIN, MenuConstant.CMS_DAILY_MODE }),
	WEB_RED_BLUE_IMAGE(5, "前台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_RED_VS_BLUE }),
	CMS_RED_BLUE_IMAGE(6, "后台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_RED_VS_BLUE }),
	CMS_SINGLE_IMAGE(7, "后台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_CR_SINGLE_TRAIN }),
	CMS_TOOL_IMAGE(8, "后台工具库", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}),
	WEB_SINGLE_IMAGE(9, "前台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_SINGLE_TRAIN }),
	WEB_EVALUATE_IMAGE(10, "前台靶场测评", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_EVALUATION }),
	WEB_OPERATION_IMAGE(11, "前台实操习题", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_OJ_PRACTICE_OPERATION }),
	WEB_COURSE_IMAGE(12, "前台课程", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.WEB_OJ_COURSE}),
	CMS_THEORY_IMAGE(13, "后台理论习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_THEORY_PRACTICE}),
	CMS_OPERATION_IMAGE(14, "后台实操习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_PRACTICE_OPERATION});

	@Getter
	public final Integer code;

	@Getter
	private final String desc;

	public final String fileSuffix;

	public final String[] menuIds;

	UploadImagePermission(int type, String desc, String fileSuffix, String[] menuIds) {
		this.code = type;
		this.desc = desc;
		this.fileSuffix = fileSuffix;
		this.menuIds = menuIds;
	}

	public void validateFileSuffix(String fileName) {
		FileUtil.validateFileSuffix(fileName, fileSuffix);
	}

	public void validatePermission(boolean all) {

		boolean access = false;

		if (!all) {
			for (String menuId : menuIds) {
				access = SecurityUtils.getSubject().isPermitted(menuId);
				if (access) {
					break;
				}
			}
		} else {
			access = SecurityUtils.getSubject().isPermittedAll(menuIds);
		}

		if (!access) {
			throw new UnauthorizedException();
		}
	}

}
