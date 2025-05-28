/**
 * 该代码定义了一个名为 `SpaceStrategyService` 的接口，属于服务层的规范。以下是对其功能的简要分析： 
 * 1. **继承与扩展**： 
 * - 接口继承了 `IService<SpaceStrategy>`，表明它基于 MyBatis-Plus 提供的基础 CRUD 功能，并进一步扩展了自定义业务逻辑。 
 * 2. **主要功能模块**： 
 * - **框架管理**：包括获取框架 (`getFrame`)、新增框架 (`addFrame`)、修改框架 (`modify`) 和删除框架 (`deleteFrame`)。 
 * - **策略管理**：提供策略的增删改查操作（如 `addStrategy`, `updateStrategy`, `deleteStrategy`, `getDetail`）以及按框架查询策略列表 (`listStrategiesByFrame`) 和分页查询 (`pageStrategiesByFrame`)。 
 * - **转换与导出**：支持将 ID 列表转换为 `SpaceActionStrategy` 对象列表 (`convert2ActionStrategy`)，以及导出策略路径 (`dumpStrategyPath`)。 
 * - **统计与图表**：提供获取统计学对象 (`getStatistics`) 和策略图表 (`getStrategyChart`) 的方法。 
 * - **异常处理**：定义了获取资源或抛出异常的方法 (`getOrException`)。 
 * 3. **设计特点**： 
 * - 方法命名清晰，遵循驼峰命名法，便于理解和维护。 
 * - 使用了泛型和注释来增强代码的可读性和可维护性。 
 * - 接口设计面向业务场景，体现了良好的模块化和职责分离原则。 
 * 总体而言，该接口为管理空间策略及其相关框架提供了全面的服务支持，适用于复杂业务场景下的数据管理和操作。 
**/
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.index.IndexStrategy;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.strategy.*;

import java.util.List;

/**
 * 空间策略Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/13
 * @see IService
 * @since 1.0.0
 */
public interface SpaceStrategyService extends IService<SpaceStrategy> {
    /**
     * 获取框架
     *
     * @return {@code SpaceStrategyFrameBo }
     * @throws
     * @see SpaceStrategyFrameBo
     */
    SpaceStrategyFrameBo getFrame();

    /**
     * 新增框架
     *
     * @param req 请求体
     */
    void addFrame(SpaceStrategyFrameAddReq req);

    /**
     * 修改
     *
     * @param req 请求体
     */
    void modify(SpaceStrategyFrameModifyReq req);

    /**
     * 删除框架
     *
     * @param resourceId 资源id
     */
    void deleteFrame(String resourceId);

    /**
     * 列表策略由框架
     *
     * @param frameId 框架id
     * @param name    名称
     * @return {@code List<SpaceStrategySimple> }
     */
    List<SpaceStrategySimple> listStrategiesByFrame(String frameId, String name);

    /**
     * 新增策略
     *
     * @param req 请求体
     */
    void addStrategy(SpaceStrategyAddReq req);

    /**
     * 更新策略
     *
     * @param req 请求体
     */
    void updateStrategy(SpaceStrategyModifyReq req);

    /**
     * 删除策略
     *
     * @param ids 资源id
     */
    void deleteStrategy(List<String> ids);

    /**
     * 获取详情
     *
     * @param resourceId 资源id
     * @return {@code SpaceStrategyBo }
     */
    SpaceStrategyBo getDetail(String resourceId);

    /**
     * 获取框架路线由id
     *
     * @param frameId 框架id
     * @return {@code String }
     */
    String getFrameRouteById(String frameId);

    /**
     * 获取统计学
     *
     * @return {@code IndexStrategy }
     */
    IndexStrategy getStatistics();

    ListRsp<SpaceStrategyBo> pageStrategiesByFrame(PageReqBase req, String frameId, String name);
    /**
     * 转换
     *
     * @param ids id列表
     * @return {@code List<SpaceActionStrategy> }
     */
    List<SpaceActionStrategy> convert2ActionStrategy(List<String> ids);

    /**
     * 获取策略图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getStrategyChart();

    /**
     * 导出策略路径
     *
     * @param strategyIds 策略id列表
     * @return 字符串
     * @throws
     * @see List<String>
     * @see String
     */
    String dumpStrategyPath(List<String> strategyIds);

    /**
     * 获取或例外
     *
     * @param resourceId 资源id
     * @return 空间策略
     * @throws
     * @see String
     * @see SpaceStrategy
     */
    SpaceStrategy getOrException(String resourceId);
}
