package com.cyberpeace.cr.crbase.service.context.screen;

import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 屏幕记录用户岗位
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ScreenRecordUserRole extends UserRole {


    /**
     * 屏幕记录
     */
    private List<ScreenRecordRsp> screenRecords;
}
