package com.cyberpeace.cr.crbase.service.context.screen;

import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class ScreenRecordUserRole extends UserRole {


    private List<ScreenRecordRsp> screenRecords;
}
