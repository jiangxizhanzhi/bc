package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

/**
 * 上传图片权限
 *
 * @author chenzhuo
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */

public enum UploadImagePermission implements GenericEnum {

	/**
	 * 文件上传权限枚举
	 *
	 * @see UploadImagePermission
	 */
	CMS_COURSE_LOGO(1, "后台课程", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_COURSES, MenuConstant.WEB_OJ_COURSE }),
	/**
	 * CMS 系列练习设置图标
	 *
	 * @see UploadImagePermission
	 */
	CMS_PRACTICE_SET_LOGO(2, "后台习题集", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_EXERCISES }),
	/**
	 * Web 团队形象
	 *
	 * @see UploadImagePermission
	 */
	WEB_TEAM_IMAGE(3, "前台团队训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_TEAM_TRAIN }),
	/**
	 * CMS 团队形象
	 *
	 * @see UploadImagePermission
	 */
	CMS_TEAM_IMAGE(4, "后台团队训练", FileTypeContant.PICTURE_TYPE, new String[]{ MenuConstant.CMS_CR_TEAM_TRAIN }),
	/**
	 * Web 红色蓝色图像
	 *
	 * @see UploadImagePermission
	 */
	WEB_RED_BLUE_IMAGE(5, "前台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_RED_VS_BLUE }),
	/**
	 * CMS 红蓝图像
	 *
	 * @see UploadImagePermission
	 */
	CMS_RED_BLUE_IMAGE(6, "后台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_RED_VS_BLUE }),
	/**
	 * CMS 单张图片
	 *
	 * @see UploadImagePermission
	 */
	CMS_SINGLE_IMAGE(7, "后台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_CR_SINGLE_TRAIN }),
	/**
	 * CMS 工具图像
	 *
	 * @see UploadImagePermission
	 */
	CMS_TOOL_IMAGE(8, "后台工具库", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_TOOL}),
	/**
	 * Web 单张图片
	 *
	 * @see UploadImagePermission
	 */
	WEB_SINGLE_IMAGE(9, "前台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_SINGLE_TRAIN }),
	/**
	 * Web评估图像
	 *
	 * @see UploadImagePermission
	 */
	WEB_EVALUATE_IMAGE(10, "前台靶场测评", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_EVALUATION }),
	/**
	 * Web实操图像
	 *
	 * @see UploadImagePermission
	 */
	WEB_OPERATION_IMAGE(11, "前台实操习题", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_OJ_PRACTICE_OPERATION }),
	/**
	 * Web 课程图像
	 *
	 * @see UploadImagePermission
	 */
	WEB_COURSE_IMAGE(12, "前台课程", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.WEB_OJ_COURSE}),
	/**
	 * CMS 系列理论图像
	 *
	 * @see UploadImagePermission
	 */
	CMS_THEORY_IMAGE(13, "后台理论习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_THEORY_PRACTICE}),
	/**
	 * CMS 系列实操图像
	 *
	 * @see UploadImagePermission
	 */
	CMS_OPERATION_IMAGE(14, "后台实操习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_PRACTICE_OPERATION}),
	/**
	 * WebOJ理论练习
	 *
	 * @see UploadImagePermission
	 */
	WEB_OJ_THEORY_PRACTICE(15, "理论题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.WEB_OJ_THEORY_PRACTICE});

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	public final Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private final String desc;

	/**
	 * 文件后缀
	 *
	 * @see String
	 */
	public final String fileSuffix;

	/**
	 * 菜单id列表
	 *
	 * @see String[]
	 */
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
