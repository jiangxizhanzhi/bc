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
 * @author chenzhuo@date 2024/11/08
 */

/**
 * ### 代码分析 
 * #### 概述 
 * 该代码定义了一个枚举类 `UploadImagePermission`，用于管理上传图片的权限。每个枚举值表示一种特定的图片上传权限，并包含了相关的描述、文件后缀、菜单ID等信息。 
 * #### 主要功能 
 * 1. **枚举值定义**： 
 * - 每个枚举值代表一种上传图片的权限，例如 `CMS_COURSE_LOGO` 表示后台课程的图片上传权限。 
 * - 每个枚举值包含以下属性： 
 * - `code`：权限的唯一标识符（整数）。 
 * - `desc`：权限的描述（字符串）。 
 * - `fileSuffix`：允许上传的文件后缀（字符串）。 
 * - `menuIds`：与该权限相关的菜单ID数组。 
 * 2. **方法实现**： 
 * - `validateFileSuffix(String fileName)`：验证上传文件的后缀是否符合要求。 
 * - `validatePermission(boolean all)`：验证当前用户是否具有该权限。如果 `all` 参数为 `true`，则要求用户必须拥有所有相关的菜单权限；否则只需拥有任意一个菜单权限即可。 
 * 3. **异常处理**： 
 * - 如果用户没有足够的权限，会抛出 `UnauthorizedException` 异常。 
 * #### 特点 
 * - 使用了 Lombok 的 `@Getter` 注解来自动生成字段的 getter 方法。 
 * - 利用了 Apache Shiro 的 `SecurityUtils` 和 `Subject` 对象来检查用户的权限。 
 * - 提供了灵活的权限验证机制，支持逐项检查或全部检查两种模式。 
 * #### 总结 
 * 这是一个典型的权限控制枚举类，适用于需要对不同类型的图片上传进行精细化权限管理的场景。通过结合枚举类型和权限校验逻辑，可以有效提升代码的可读性和维护性。 
**/
public enum UploadImagePermission implements GenericEnum {

	/**
	 * 文件上传权限枚举
	 */
	CMS_COURSE_LOGO(1, "后台课程", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_COURSES, MenuConstant.WEB_OJ_COURSE }),
	/**
	 * CMS 系列实践设置图标
	 */
	CMS_PRACTICE_SET_LOGO(2, "后台习题集", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_EXERCISES }),
	/**
	 * Web 团队形象
	 */
	WEB_TEAM_IMAGE(3, "前台团队训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_TEAM_TRAIN }),
	/**
	 * CMS 团队形象
	 */
	CMS_TEAM_IMAGE(4, "后台团队训练", FileTypeContant.PICTURE_TYPE, new String[]{ MenuConstant.CMS_CR_TEAM_TRAIN }),
	/**
	 * Web 红色蓝色图像
	 */
	WEB_RED_BLUE_IMAGE(5, "前台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_RED_VS_BLUE }),
	/**
	 * CMS 红蓝图像
	 */
	CMS_RED_BLUE_IMAGE(6, "后台红蓝对抗", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_RED_VS_BLUE }),
	/**
	 * CMS 单张图片
	 */
	CMS_SINGLE_IMAGE(7, "后台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.CMS_CR_SINGLE_TRAIN }),
	/**
	 * CMS 工具图像
	 */
	CMS_TOOL_IMAGE(8, "后台工具库", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_TOOL}),
	/**
	 * Web 单张图片
	 */
	WEB_SINGLE_IMAGE(9, "前台单兵训练", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_SINGLE_TRAIN }),
	/**
	 * Web评估图像
	 */
	WEB_EVALUATE_IMAGE(10, "前台靶场测评", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_CR_EVALUATION }),
	/**
	 * Web实操图像
	 */
	WEB_OPERATION_IMAGE(11, "前台实操习题", FileTypeContant.PICTURE_TYPE, new String[] { MenuConstant.WEB_OJ_PRACTICE_OPERATION }),
	/**
	 * Web 课程图像
	 */
	WEB_COURSE_IMAGE(12, "前台课程", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.WEB_OJ_COURSE}),
	/**
	 * CMS 系列理论图像
	 */
	CMS_THEORY_IMAGE(13, "后台理论习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_THEORY_PRACTICE}),
	/**
	 * CMS 系列实操图像
	 */
	CMS_OPERATION_IMAGE(14, "后台实操习题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.CMS_PRACTICE_OPERATION}),
	/**
	 * WebOJ理论实践
	 */
	WEB_OJ_THEORY_PRACTICE(15, "理论题", FileTypeContant.PICTURE_TYPE, new String[] {MenuConstant.WEB_OJ_THEORY_PRACTICE});

	/**
	 * 代号
	 */
	@Getter
	public final Integer code;

	/**
	 * 描述
	 */
	@Getter
	private final String desc;

	/**
	 * 文件后缀
	 */
	public final String fileSuffix;

	/**
	 * 菜单id列表
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

