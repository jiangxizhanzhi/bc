package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeGroupRsp;

import java.util.List;

public interface CrNoticeService {
    List<CrNoticeGroupRsp> noticeGroup(String roomId);
}
