
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderListReq;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderReq;

import java.util.List;

/**
 * 空间行动框架次序Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceActionFrameOrderService extends IService<SpaceActionFrameOrder>{
    /**
     * 创建
     *
     * @param req 请求体
     * @throws
     * @see SpaceActionFrameOrderReq
     */
    void create(SpaceActionFrameOrderReq req);

    /**
     * 完成
     *
     * @param resourceId 资源id
     * @throws
     * @see String
     */
    void complete(String resourceId);

    /**
     * 列表我的订单
     *
     * @param req 请求体
     * @return 列表<空间行动框架次序博>
     * @throws
     * @see SpaceActionFrameOrderListReq
     * @see List<SpaceActionFrameOrderBo>
     */
    List<SpaceActionFrameOrderBo> listMyOrders(SpaceActionFrameOrderListReq req);

    /**
     * 删除所有已经不关联框架的战法指令
     *
     * @param arrangeBo     arrangeBo
     * @param applicationId 行动框架应用id
     * @throws
     * @see ActionFrameArrangeBo
     * @see String
     */
    void deleteReleased(ActionFrameArrangeBo arrangeBo, String applicationId);
}
    