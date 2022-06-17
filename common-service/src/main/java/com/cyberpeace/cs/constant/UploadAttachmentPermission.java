package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;

import lombok.Getter;

public enum UploadAttachmentPermission implements GenericEnum {

    /** 文件上传权限枚举*/
    CMS_COURSE_MD_ATTACHMENT(1, "后台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[] { MenuConstant.CMS_COURSES}),
    CMS_LESSON_ATTACHMENT(2, "后台课时附件上传", FileTypeContant.LESSON_ATTACHMENT, new String[]{MenuConstant.CMS_COURSES}),
    CMS_THEORY_MD_ATTACHMENT(3, "后台理论MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_THEORY_PRACTICE }),
    CMS_OPERATION_ATTACHMENT(4, "后台实操习题附件上传", FileTypeContant.OPERATION_PRACTICE_ATTACHMENT, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    CMS_OPERATION_MD_ATTACHMENT(5, "后台实操习题MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.CMS_PRACTICE_OPERATION }),
    CMS_TOOL_ATTACHMENT(6, "后台工具库附件上传", FileTypeContant.TOOL_ATTACHMENT, new String[]{ MenuConstant.CMS_TOOL }),
    WEB_COURSE_MD_ATTACHMENT(7, "前台课程MD编辑器上传附件", FileTypeContant.MD_ATTACHMENT_TYPE, new String[]{ MenuConstant.WEB_OJ_COURSE });

    @Getter
    public Integer code;

    @Getter
    private String desc;

    public String fileSuffix;

    public String[] menuIds;

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
