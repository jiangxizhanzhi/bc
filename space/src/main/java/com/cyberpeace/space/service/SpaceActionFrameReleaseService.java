package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseReq;

import java.util.List;

/**
 * 空间行动框架下发Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个接口 `SpaceActionFrameReleaseService`，它继承自 `IService<SpaceActionFrameRelease>`，并扩展了三个自定义方法。以下是代码的简要分析： 
 * 1. **接口继承**： 
 * - `SpaceActionFrameReleaseService` 继承了 `IService<SpaceActionFrameRelease>`，表明该接口是基于 MyBatis-Plus 的服务层接口，通常用于数据库操作。 
 * 2. **方法说明**： 
 * - `create(SpaceActionFrameReleaseReq req)`：用于创建新的 `SpaceActionFrameRelease` 实例，参数是一个请求体对象。 
 * - `getApplicationBo(String resourceId)`：根据资源ID获取行为依赖的业务对象（`SpaceActionFrameApplicationBo`）。 
 * - `getTactics(String applicationId)`：根据行为依赖ID获取策略列表，返回的是 `SpaceActionFrameOrderBo` 类型的列表。 
 * 3. **注释**： 
 * - 每个方法都有详细的注释，描述了方法的功能、参数和返回值类型，便于理解和使用。 
 * 4. **依赖类**： 
 * - 接口依赖多个类和BO（业务对象），包括 `SpaceActionFrameRelease`、`SpaceActionFrameReleaseReq`、`SpaceActionFrameApplicationBo` 和 `SpaceActionFrameOrderBo`。 
 * 总体而言，这段代码定义了一个服务接口，用于管理与 `SpaceActionFrameRelease` 相关的操作，具有清晰的职责划分和良好的注释支持。 
**/
public interface SpaceActionFrameReleaseService extends IService<SpaceActionFrameRelease> {
    /**
     * 创建
     *
     * @param req 请求体
     * @throws
     * @see SpaceActionFrameReleaseReq
     */
    void create(SpaceActionFrameReleaseReq req);

    /**
     * 获取行为依赖Bo
     *
     * @param resourceId 资源id
     * @return 空间行动框架行为依赖Bo
     * @throws
     * @see String
     * @see SpaceActionFrameApplicationBo
     */
    SpaceActionFrameApplicationBo getApplicationBo(String resourceId);

    /**
     * 获取策略
     *
     * @param applicationId 行为依赖id
     * @return 列表<空间行动框架次序博>
     * @throws
     * @see String
     * @see List<SpaceActionFrameOrderBo>
     */
    List<SpaceActionFrameOrderBo> getTactics(String applicationId, String name);
}

