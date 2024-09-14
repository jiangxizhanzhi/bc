package com.cyberpeace.cr.crbase.service.context.screen;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.integratation.service.bo.ScreenRecordRsp;

import lombok.Data;

@Data
public class ScreenRecordUserRole extends UserRole {


    private List<ScreenRecordRsp> screenRecords;
}
