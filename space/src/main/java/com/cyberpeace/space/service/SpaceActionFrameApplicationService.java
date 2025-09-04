package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.service.context.frame.ApplicationUser;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;

import java.util.List;
import java.util.Map;

/**
 * 空间行动框架行为依赖Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameApplicationService` 的接口，它继承自 `IService<SpaceActionFrameApplication>`。以下是对其功能的简要分析： 
 * 1. **继承与泛型**： 
 * - 接口继承了 `IService`，这是一个通用的服务接口，通常用于数据操作层（如增删改查）。 
 * - 泛型参数 `<SpaceActionFrameApplication>` 指定了该服务主要操作的对象类型。 
 * 2. **方法定义**： 
 * - `create(SpaceActionFrameApplicationReq req)`：用于创建一个新的 `SpaceActionFrameApplication` 实例，接收一个请求体作为参数。 
 * - `getUsers(String rvbId, Integer userGroup)`：根据指定的 `rvbId` 和 `userGroup` 获取相关的用户列表。 
 * - `getUserMap(String rvbId, Integer userGroup)`：返回一个字符串到字符串的映射表，基于给定的 `rvbId` 和 `userGroup`。 
 * - `deleteByIds(List<String> ids)`：通过一组 ID 删除对应的记录。 
 * 3. **注释说明**： 
 * - 每个方法都有详细的注释，描述了其功能、参数以及可能的返回值类型，有助于其他开发者理解接口的设计意图。 
 * 4. **依赖类**： 
 * - 接口依赖于几个外部类或接口，包括 `SpaceActionFrameApplication`（实体类）、`SpaceActionFrameApplicationReq`（请求类）、`ApplicationUser`（用户类）等。 
 * 总体而言，这个接口提供了针对特定业务场景的操作方法集合，适用于处理与空间行动框架相关的应用逻辑。 
**/

public interface SpaceActionFrameApplicationService extends IService<SpaceActionFrameApplication>{
    /**
     * 创建
     *
     * @param req 请求体
     * @throws
     * @see SpaceActionFrameApplicationReq
     */
    void create(SpaceActionFrameApplicationReq req);

    /**
     * 获取用户
     *
     * @param rvbId     RVBid
     * @param userGroup 用户组
     * @return 列表<行为依赖用户>
     * @throws
     * @see String
     * @see Integer
     * @see List<ApplicationUser>
     */
    List<ApplicationUser> getUsers(String rvbId, Integer userGroup);

    /**
     * 获取用户映射
     *
     * @param rvbId     RVBid
     * @param userGroup 用户组
     * @return map<string 、 string>
     * @throws
     * @see String
     * @see Integer
     * @see Map<String, String>
     */
    Map<String, String> getUserMap(String rvbId, Integer userGroup);

    /**
     * 删除由id列表
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteByIds(List<String> ids);

    List<IdAndName> applicationList(String id);

    void deleteRBApplication(String rvbId, String frameId);
}
    
