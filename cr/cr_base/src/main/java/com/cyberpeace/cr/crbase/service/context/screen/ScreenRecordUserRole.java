package com.cyberpeace.cr.crbase.service.context.screen;

import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import lombok.Data;

import java.util.List;

/**
 * 屏幕录制用户角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see UserRole
 * @since 1.0.0
 */
@Data
public class ScreenRecordUserRole extends UserRole {


    /**
     * 屏幕记录
     *
     * @see List<ScreenRecordRsp>
     */
    private List<ScreenRecordRsp> screenRecords;
}
