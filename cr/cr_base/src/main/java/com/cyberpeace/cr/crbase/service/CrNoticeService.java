package com.cyberpeace.cr.crbase.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import java.util.List;

/**
* @author CP
* @description 针对表【cr_notice】的数据库操作Service
* @createDate 2023-05-24 10:33:37
*/
public interface CrNoticeService extends IService<CrNotice> {

    List<CrNoticeRsp> queryList(String roomId, boolean isRb);

    void notice(String roomId);
}
