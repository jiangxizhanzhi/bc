package com.cyberpeace.cr.crbase.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import java.util.List;

/**
 * 靶场通知Service
 *
 * @author CP
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface CrNoticeService extends IService<CrNotice> {

    /**
     * 查询列表
     *
     * @param roomId 房间id
     * @param isRb   是 RB
     * @return {@code List<CrNoticeRsp> }
     * @throws
     * @see String
     * @see boolean
     * @see List<CrNoticeRsp>
     */
    List<CrNoticeRsp> queryList(String roomId, boolean isRb);

    /**
     * 通知
     *
     * @param crNoticeReq 靶场通知请求体
     * @throws
     * @see CrNoticeReq
     */
    void notice(CrNoticeReq crNoticeReq);

    /**
     * 有新信息
     *
     * @param roomID 房间id
     * @param isRb   是 RB
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see boolean
     * @see Boolean
     */
    Boolean hasNewInfo(String roomID, boolean isRb);

    /**
     * Remve 和 Notice
     *
     * @param resourceId 资源id
     * @throws
     * @see String
     */
    void remveAndNotice(String resourceId);
}
