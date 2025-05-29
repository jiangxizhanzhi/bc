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
/**
 * 该代码定义了一个名为 `SpaceActionFrameOrderService` 的接口，它继承自 `IService<SpaceActionFrameOrder>`。这个接口用于处理与“空间行动框架次序”相关的业务逻辑。以下是对其功能的简要分析： 
 * 1. **继承关系**：`SpaceActionFrameOrderService` 接口继承了 `IService<SpaceActionFrameOrder>`，这表明它扩展了 MyBatis-Plus 提供的基本 CRUD 功能。 
 * 2. **方法说明**： 
 * - `create(SpaceActionFrameOrderReq req)`：根据请求体创建一个新的空间行动框架次序。 
 * - `complete(String resourceId)`：标记某个资源ID对应的订单为完成状态。 
 * - `listMyOrders(SpaceActionFrameOrderListReq req)`：根据请求体获取当前用户的订单列表，并返回一个包含多个 `SpaceActionFrameOrderBo` 对象的列表。 
 * - `deleteReleased(ActionFrameArrangeBo arrangeBo, String applicationId)`：删除所有不再关联任何框架的战法指令，需要提供 `ActionFrameArrangeBo` 和行动框架的应用ID作为参数。 
 * 3. **注释与文档**：每个方法都有详细的注释说明其用途、参数以及可能抛出的异常等信息，有助于其他开发者理解和使用该接口。 
 * 总体来说，这段代码清晰地定义了一组针对特定业务场景的服务方法，便于后续的具体实现和调用。 
**/

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
    
