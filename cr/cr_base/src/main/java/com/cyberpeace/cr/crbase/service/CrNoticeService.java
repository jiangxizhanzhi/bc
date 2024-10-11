package com.cyberpeace.cr.crbase.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;

import java.util.List;

/**
* @author CP
* @description 针对表【cr_notice】的数据库操作Service
*/
public interface CrNoticeService extends IService<CrNotice> {

    List<CrNoticeRsp> queryList(String roomId, boolean isRb);

    void notice(CrNoticeReq crNoticeReq);

    Boolean hasNewInfo(String roomID, boolean isRb);

    void remveAndNotice(String resourceId);
}
