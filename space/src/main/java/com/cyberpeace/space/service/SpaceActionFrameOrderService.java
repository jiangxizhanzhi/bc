
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderListReq;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderReq;

public interface SpaceActionFrameOrderService extends IService<SpaceActionFrameOrder>{
    void create(SpaceActionFrameOrderReq req);

    ListRsp<SpaceActionFrameOrderBo> pageMyOrders(SpaceActionFrameOrderListReq req);

    void complete(String resourceId);
}
    