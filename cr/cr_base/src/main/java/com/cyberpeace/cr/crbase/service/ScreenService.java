package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;

import java.util.List;

public interface ScreenService {


    List<ScreenRecordUserRole>  getReplyUserTimeInfo(String roomId,String nodeId);
}
