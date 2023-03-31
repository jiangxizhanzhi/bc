package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeCreateReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeUpdateReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeDetailRsp;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeListRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;

import java.util.List;

public interface CrNoticeService extends IService<CrNotice> {
    List<CrLabelGroup> noticeGroup(String roomId);

    ListRsp<CrNoticeListRsp> pageAdmin(String roomId, Page page);

    List<CrNoticeListRsp> page(String roomId);

    CrNoticeDetailRsp get(String resourceId);

    CrNoticeDetailRsp create(CrNoticeCreateReq crNoticeReq);

    CrNoticeDetailRsp update(CrNoticeUpdateReq crNoticeReq);

    Boolean updateStatus(List<String> ids, Integer status);

}
