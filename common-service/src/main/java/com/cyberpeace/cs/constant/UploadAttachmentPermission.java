package com.cyberpeace.cs.constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

/**
 * 上传附件权限
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum UploadAttachmentPermission implements GenericEnum {

	/** 文件上传权限枚举*/
	CMS_COURSE_MD_ATTACHMENT(1, "后台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE,
			new String[] { MenuConstant.CMS_COURSES }), CMS_LESSON_ATTACHMENT(2, "后台课时附件上传",
					FileTypeContant.LESSON_ATTACHMENT,
					new String[] { MenuConstant.CMS_COURSES }), CMS_THEORY_MD_ATTACHMENT(3,
							"后台理论MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE,
							new String[] {
									MenuConstant.CMS_THEORY_PRACTICE }), CMS_OPERATION_ATTACHMENT(4,
											"后台实操习题附件上传",
											FileTypeContant.OPERATION_PRACTICE_ATTACHMENT,
											new String[] {
													MenuConstant.CMS_PRACTICE_OPERATION }), CMS_OPERATION_MD_ATTACHMENT(
															5, "后台实操习题MD编辑器上传附件",
															FileTypeContant.MD_ATTACHMENT_TYPE,
															new String[] {
																	MenuConstant.CMS_PRACTICE_OPERATION }), CMS_TOOL_ATTACHMENT(
																			6, "后台工具库附件上传",
																			FileTypeContant.TOOL_ATTACHMENT,
																			new String[] {
																					MenuConstant.CMS_TOOL }), WEB_COURSE_MD_ATTACHMENT(
																							7,
																							"前台课程MD编辑器上传附件",
																							FileTypeContant.MD_ATTACHMENT_TYPE,
																							new String[] {
																									MenuConstant.WEB_OJ_COURSE }),
	// TODO后期同步前端修改
	CMS_SITUATION_THREE_DIMENSIONAL_COMPONENT_ATTACHMENT(8, "后台态势三维组件文件上传",
			FileTypeContant.CHART_STANDARD_ATTACHMENT,
			new String[] {
					MenuConstant.CMS_SITUATION_THREE_DIMENSIONAL_COMPONENT }), CMS_SITUATION_PARK_RESOURCE_LIBRARY_ATTACHMENT(
							9, "后台态势园区资源库文件上传", FileTypeContant.OPERATION_PRACTICE_ATTACHMENT,
							new String[] { MenuConstant.CMS_SITUATION_DYNAMIC_EFFICIENCY,
									MenuConstant.CMS_SITUATION_PARK_RESOURCE_LIBRARY }), CMS_SITUATION_COMMON_COMPONENT(
											10, "后台态势模板公共组件文件上传", FileTypeContant.COMMON_COMPONENT,
											new String[] {
													MenuConstant.CMS_SITUATION_PARK_RESOURCE_LIBRARY }), CMS_SITUATION_ICON_SVG(
															11, "后台态势图标文件上传",
															FileTypeContant.ICON_SVG,
															new String[] {
																	MenuConstant.CMS_SITUATION_ICON }), CMS_SITUATION_DB_CONVERSION_TOOL_ATTACHMENT(
																			12, "后台态势数据转换工具文件上传",
																			FileTypeContant.OPERATION_PRACTICE_ATTACHMENT,
																			new String[] {
																					MenuConstant.DATA_CONVERSION_TOOL_MANAGEMENT }), CMS_SITUATION_TEXT_ATTACHMENT(
																							13,
																							"后台态势数据文本解析上传",
																							FileTypeContant.TEXT_TYPE,
																							new String[] {
																									MenuConstant.DATA_CONVERSION_TOOL_MANAGEMENT });

	@Getter
	public Integer code;

	@Getter
	private String desc;

	public String fileSuffix;

	public String[] menuIds;

	/**
	 * 上传附件权限
	 *
	 * @param type       类型
	 * @param desc       desc
	 * @param fileSuffix 文件后缀
	 * @param menuIds    菜单id
	 */
	UploadAttachmentPermission(int type, String desc, String fileSuffix, String[] menuIds) {
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
