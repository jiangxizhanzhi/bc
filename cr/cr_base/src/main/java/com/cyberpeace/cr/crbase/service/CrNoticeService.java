package com.cyberpeace.cr.crbase.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;

import java.util.List;

/**
 * 靶场通知Service
 *
 * @author CP
 * @date 2024/11/13
 */
public interface CrNoticeService extends IService<CrNotice> {

    /**
     * 查询列表
     *
     * @param roomId 房间id
     * @param isRb   是否红蓝对抗
     * @return {@code List<CrNoticeRsp> }
     */
    List<CrNoticeRsp> queryList(String roomId, boolean isRb);

    /**
     * 通知
     *
     * @param crNoticeReq 靶场通知请求体
     */
    void notice(CrNoticeReq crNoticeReq);

    /**
     * 有新信息
     *
     * @param roomID 房间id
     * @param isRb   是否红蓝对抗
     * @return {@code Boolean }
     */
    Boolean hasNewInfo(String roomID, boolean isRb);

    /**
     * Remve 和 Notice
     *
     * @param resourceId 资源id
     */
    void remveAndNotice(String resourceId);
}
