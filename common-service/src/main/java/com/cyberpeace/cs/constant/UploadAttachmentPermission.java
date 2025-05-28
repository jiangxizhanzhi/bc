/**
 * ### 代码分析 
 * #### 概述 
 * 该代码定义了一个枚举类 `UploadAttachmentPermission`，用于管理文件上传权限。它实现了接口 `GenericEnum`，并提供了文件上传相关的验证逻辑。 
 * #### 主要功能 
 * 1. **枚举常量**： 
 * - 定义了多种文件上传权限，每个常量包含以下信息： 
 * - `code`：权限的唯一标识。 
 * - `desc`：权限的描述。 
 * - `fileSuffix`：允许上传的文件后缀。 
 * - `menuIds`：与该权限关联的菜单ID数组。 
 * 2. **方法实现**： 
 * - `validateFileSuffix(String fileName)`：校验文件名是否符合指定的文件后缀规则。 
 * - `validatePermission(boolean all)`：根据菜单ID验证用户是否有对应的权限： 
 * - 如果 `all` 为 `false`，检查用户是否至少有一个菜单ID具有权限。 
 * - 如果 `all` 为 `true`，检查用户是否对所有菜单ID都有权限。 
 * - 如果权限不足，抛出 `UnauthorizedException` 异常。 
 * 3. **依赖外部工具和框架**： 
 * - 使用了 `FileUtil` 验证文件后缀。 
 * - 使用了 Apache Shiro 的 `SecurityUtils` 和 `Subject` 进行权限校验。 
 * #### 关键点 
 * - **设计模式**：使用了枚举模式，将权限相关的常量集中管理，便于扩展和维护。 
 * - **权限校验**：通过 `SecurityUtils` 提供的权限接口，确保只有授权用户才能进行特定操作。 
 * - **灵活性**：通过参数 `all` 控制权限校验的方式（部分权限或全部权限），提高了方法的适用性。 
 * #### 总结 
 * 该代码提供了一种结构化的权限管理方式，适合用于文件上传场景中对用户权限的控制。通过枚举和方法封装，代码清晰且易于扩展，但需要确保 `FileUtil` 和 Shiro 的配置正确以避免运行时异常。 
**/
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
 * @author chenzhuo@date 2024/11/08
 */
public enum UploadAttachmentPermission implements GenericEnum {

    /**
     * 文件上传权限枚举
     */
    CMS_COURSE_MD_ATTACHMENT(1, "后台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[] { MenuConstant.CMS_COURSES}),
    /**
     * CMS 课程附件
     */
    CMS_LESSON_ATTACHMENT(2, "后台课时附件上传", FileTypeContant.LESSON_ATTACHMENT, new String[]{MenuConstant.CMS_COURSES}),
    /**
     * CMS 系列理论Markdown附件
     */
    CMS_THEORY_MD_ATTACHMENT(3, "后台理论MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_THEORY_PRACTICE }),
    /**
     * CMS 系列实操附件
     */
    CMS_OPERATION_ATTACHMENT(4, "后台实操习题附件上传", FileTypeContant.OPERATION_PRACTICE_ATTACHMENT, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    /**
     * CMS 系列实操Markdown附件
     */
    CMS_OPERATION_MD_ATTACHMENT(5, "后台实操习题MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    /**
     * CMS 工具附件
     */
    CMS_TOOL_ATTACHMENT(6, "后台工具库附件上传", FileTypeContant.TOOL_ATTACHMENT, new String[]{ MenuConstant.CMS_TOOL }),
    /**
     * Web 课程 MD 附件
     */
    WEB_COURSE_MD_ATTACHMENT(7, "前台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.WEB_OJ_COURSE }),
    /**
     * CMS Mission MD 附件
     */
    CMS_MISSION_MD_ATTACHMENT(8,"后台任务编辑MD附件上传", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_CR_MISSION_SCHEME}),
    /**
     * CMS 课程讲义附件
     */
    CMS_LESSON_HANDOUT_ATTACHMENT(9,"后台课时讲义附件上传", FileTypeContant.HANDOUT_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_COURSES}),
    /**
     * CMS 课程视频附件
     */
    CMS_LESSON_VIDEO_ATTACHMENT(10,"后台课时视频附件上传", FileTypeContant.VIDEO_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_COURSES}),
    /**
     * WebOJ理论实践
     */
    WEB_OJ_THEORY_PRACTICE(11, "前台理论题MD编辑器附件上传", FileTypeContant.MD_ATTACHMENT_TYPE, new String[] { MenuConstant.WEB_OJ_THEORY_PRACTICE }),
    /**
     * Web Mission MD 附件
     */
    WEB_MISSION_MD_ATTACHMENT(12, "前台学员答题编辑MD附件上传", FileTypeContant.MD_ATTACHMENT_TYPE,
					new String[] { MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN,
							MenuConstant.WEB_CR_SINGLE_TRAIN });

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

    UploadAttachmentPermission(int type, String desc, String fileSuffix, String[] menuIds) {
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
