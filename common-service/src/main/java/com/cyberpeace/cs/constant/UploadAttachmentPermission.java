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
public enum UploadAttachmentPermission implements GenericEnum {

    /** 文件上传权限枚举*/
    CMS_COURSE_MD_ATTACHMENT(1, "后台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[] { MenuConstant.CMS_COURSES}),
    CMS_LESSON_ATTACHMENT(2, "后台课时附件上传", FileTypeContant.LESSON_ATTACHMENT, new String[]{MenuConstant.CMS_COURSES}),
    CMS_THEORY_MD_ATTACHMENT(3, "后台理论MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_THEORY_PRACTICE }),
    CMS_OPERATION_ATTACHMENT(4, "后台实操习题附件上传", FileTypeContant.OPERATION_PRACTICE_ATTACHMENT, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    CMS_OPERATION_MD_ATTACHMENT(5, "后台实操习题MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    CMS_TOOL_ATTACHMENT(6, "后台工具库附件上传", FileTypeContant.TOOL_ATTACHMENT, new String[]{ MenuConstant.CMS_TOOL }),
    WEB_COURSE_MD_ATTACHMENT(7, "前台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.WEB_OJ_COURSE , MenuConstant.WEB_OJ_ALL_COURSE }),
    CMS_MISSION_MD_ATTACHMENT(8,"后台任务编辑MD附件上传", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_CR_MISSION_SCHEME}),
    CMS_LESSON_HANDOUT_ATTACHMENT(9,"后台课时讲义附件上传", FileTypeContant.HANDOUT_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_COURSES}),
    CMS_LESSON_VIDEO_ATTACHMENT(10,"后台课时视频附件上传", FileTypeContant.VIDEO_ATTACHMENT_TYPE, new String[]{MenuConstant.CMS_COURSES}),
	WEB_OJ_THEORY_PRACTICE(11, "前台理论题MD编辑器附件上传", FileTypeContant.MD_ATTACHMENT_TYPE, new String[] { MenuConstant.WEB_OJ_THEORY_PRACTICE }),
    WEB_MISSION_MD_ATTACHMENT(12, "前台学员答题编辑MD附件上传", FileTypeContant.MD_ATTACHMENT_TYPE,
					new String[] { MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN,
							MenuConstant.WEB_CR_SINGLE_TRAIN });

    @Getter
    public final Integer code;

    @Getter
    private final String desc;

    public final String fileSuffix;

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
