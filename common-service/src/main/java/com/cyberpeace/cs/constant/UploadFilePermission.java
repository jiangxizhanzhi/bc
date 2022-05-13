package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

public enum UploadFilePermission implements GenericEnum {

	/** 文件上传权限枚举*/
	CMS_COURSE_LOGO(1, "后台课程", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_COURSES, MenuConstant.WEB_OJ_COURSE }),
	CMS_PRACTICE_SET_LOGO(2, "后台习题集", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_EXERCISES }),
	TEAM_LOGO(3, "前台团队训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_TEAM_TRAIN }),
	CMS_TEAM_IMAGE(4, "后台团队训练", FileTypeContant.PICTURE_TYPE, new String[]{ MenuConstant.CMS_CR_TEAM_TRAIN }),
	RED_BLUE_LOGO(5, "前台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_RED_VS_BLUE }),
	CMS_RED_BLUE_IMAGE(6, "后台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_RED_VS_BLUE }),
	SINGLE_LOGO(7, "后台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_CR_SINGLE_TRAIN });

	@Getter
	public Integer code;

	@Getter
	private String desc;

	public String fileSuffix;

	public String[] menuIds;

	UploadFilePermission(int type, String desc, String fileSuffix, String[] menuIds) {
		this.code = type;
		this.desc = desc;
		this.fileSuffix = fileSuffix;
		this.menuIds = menuIds;
	}

	public void validateFileSuffix(String fileName) {
		FileUtil.validateFileSuffix(fileName, fileSuffix);
	}

	public void validatePermission(boolean all) {

		Boolean access = false;

		if (!all) {
			for (String menuId : menuIds) {
				if (access = SecurityUtils.getSubject().isPermitted(menuId)) {
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
