package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

/**
 * 上传图片许可
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum UploadImagePermission implements GenericEnum {

	/** 文件上传权限枚举*/
	CMS_COURSE_LOGO(1, "后台课程", FileTypeContant.PICTURE_TYPE, new String[] {
			MenuConstant.CMS_COURSES, MenuConstant.WEB_OJ_COURSE }), CMS_PRACTICE_SET_LOGO(2,
					"后台习题集", FileTypeContant.PICTURE_TYPE,
					new String[] { MenuConstant.CMS_EXERCISES }), WEB_TEAM_IMAGE(3, "前台团队训练",
							FileTypeContant.PICTURE_TYPE,
							new String[] { MenuConstant.WEB_CR_TEAM_TRAIN }), CMS_TEAM_IMAGE(4,
									"后台团队训练", FileTypeContant.PICTURE_TYPE,
									new String[] {
											MenuConstant.CMS_CR_TEAM_TRAIN }), WEB_RED_BLUE_IMAGE(5,
													"前台红蓝对抗", FileTypeContant.PICTURE_TYPE,
													new String[] {
															MenuConstant.WEB_CR_RED_VS_BLUE }), CMS_RED_BLUE_IMAGE(
																	6, "后台红蓝对抗",
																	FileTypeContant.PICTURE_TYPE,
																	new String[] {
																			MenuConstant.CMS_RED_VS_BLUE }), CMS_SINGLE_IMAGE(
																					7, "后台单兵训练",
																					FileTypeContant.PICTURE_TYPE,
																					new String[] {
																							MenuConstant.CMS_CR_SINGLE_TRAIN }), CMS_TOOL_IMAGE(
																									8,
																									"后台工具库",
																									FileTypeContant.PICTURE_TYPE,
																									new String[] {
																											MenuConstant.CMS_TOOL }), WEB_SINGLE_IMAGE(
																													9,
																													"前台单兵训练",
																													FileTypeContant.PICTURE_TYPE,
																													new String[] {
																															MenuConstant.WEB_CR_SINGLE_TRAIN }), WEB_EVALUATE_IMAGE(
																																	10,
																																	"前台靶场测评",
																																	FileTypeContant.PICTURE_TYPE,
																																	new String[] {
																																			MenuConstant.WEB_CR_EVALUATION }), WEB_OPERATION_IMAGE(
																																					11,
																																					"前台实操习题",
																																					FileTypeContant.PICTURE_TYPE,
																																					new String[] {
																																							MenuConstant.WEB_OJ_PRACTICE_OPERATION }), WEB_COURSE_IMAGE(
																																									12,
																																									"前台课程",
																																									FileTypeContant.PICTURE_TYPE,
																																									new String[] {
																																											MenuConstant.WEB_OJ_COURSE }), CMS_THEORY_IMAGE(
																																													13,
																																													"后台理论习题",
																																													FileTypeContant.PICTURE_TYPE,
																																													new String[] {
																																															MenuConstant.CMS_THEORY_PRACTICE }), CMS_OPERATION_IMAGE(
																																																	14,
																																																	"后台实操习题",
																																																	FileTypeContant.PICTURE_TYPE,
																																																	new String[] {
																																																			MenuConstant.CMS_PRACTICE_OPERATION }), CMS_SITUATION_PLOT(
																																																					15,
																																																					"态势绘标图元",
																																																					FileTypeContant.PLOT_PNG,
																																																					new String[] {
																																																							MenuConstant.CMS_SITUATION_PLOT_PIXEL }), CMS_SITUATION_COMPONENTS(
																																																									16,
																																																									"后台图形管理",
																																																									FileTypeContant.DRAWING,
																																																									new String[] {
																																																											MenuConstant.CMS_SITUATION_DRAWING_RESOURCE });

	@Getter
	public Integer code;

	@Getter
	private String desc;

	public String fileSuffix;

	public String[] menuIds;

	/**
	 * 上传图片许可
	 *
	 * @param type       类型
	 * @param desc       desc
	 * @param fileSuffix 文件后缀
	 * @param menuIds    菜单id
	 */
	UploadImagePermission(int type, String desc, String fileSuffix, String[] menuIds) {
		this.code = type;
		this.desc = desc;
		this.fileSuffix = fileSuffix;
		this.menuIds = menuIds;
	}

	/**
	 * 验证文件后缀
	 *
	 * @param fileName 文件名称
	 */
	public void validateFileSuffix(String fileName) {
		FileUtil.validateFileSuffix(fileName, fileSuffix);
	}

	/**
	 * 验证权限
	 *
	 * @param all 所有
	 */
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
