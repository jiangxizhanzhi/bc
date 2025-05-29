package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;

import java.util.List;

/**
 * 空间行动关系Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionRelationService` 的接口，它扩展了 `IService<SpaceActionRelation>` 接口。这个接口主要用于处理与空间行动关系（`SpaceActionRelation`）相关的业务逻辑。以下是对其功能的简要分析： 
 * 1. **继承自 MyBatis-Plus 的 IService**： 
 * - 该接口继承了 `IService<SpaceActionRelation>`，这意味着它可能使用了 MyBatis-Plus 框架来简化数据库操作。 
 * 2. **主要方法概述**： 
 * - `createRelation`: 根据上层ID列表和资源ID创建新的关系。 
 * - `updateRelation`: 更新现有的关系。 
 * - `deleteRelationsByIds`: 根据ID列表删除多个关系。 
 * - `getUpperIdsByLowerId`: 根据资源ID获取其所有上级ID列表。 
 * - `getLowerIdsByUpperId`: 根据上级ID获取其所有下级ID列表。 
 * - `getLowerIdsByUpperIds`: 根据多个上级ID获取对应的下级ID列表。 
 * - `listByUpperIds`: 根据多个上级ID查询对应的关系列表。 
 * - `deleteByUpperIds`: 根据多个上级ID删除对应的关系。 
 * - `createEntity`: 创建一个新的 `SpaceActionRelation` 实体对象。 
 * 3. **设计目的**： 
 * - 该接口的设计目的是为了提供一套完整的、面向空间行动关系的操作API，包括但不限于增删改查等基本操作。 
 * - 它不仅支持单个对象的操作，还支持批量操作，提高了系统的效率和灵活性。 
 * 4. **技术栈**： 
 * - 使用了 MyBatis-Plus 提供的功能来简化数据访问层的操作。 
 * - 方法注释清晰地描述了每个方法的目的、参数以及返回值类型，便于理解和维护。 
 * 总体来说，这个接口为管理空间行动关系提供了全面的支持，并且通过良好的命名和文档化使得代码易于理解。 
**/
public interface SpaceActionRelationService extends IService<SpaceActionRelation> {
    /**
     * 创建关系
     *
     * @param upperIds   上id列表
     * @param resourceId 资源id
     * @throws
     * @see List<String>
     * @see String
     */
    void createRelation(List<String> upperIds, String resourceId);

    /**
     * 更新关系
     *
     * @param upperIds   上id列表
     * @param resourceId 资源id
     * @throws
     * @see List<String>
     * @see String
     */
    void updateRelation(List<String> upperIds, String resourceId);

    /**
     * 删除关系由id列表
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteRelationsByIds(List<String> ids);

    /**
     * 获取上id列表由降低id
     *
     * @param resourceId 资源id
     * @return list<字符串>
     * @throws
     * @see String
     * @see List<String>
     */
    List<String> getUpperIdsByLowerId(String resourceId);

    /**
     * 获取降低id列表由上id
     *
     * @param upperId 上id
     * @return list<字符串>
     * @throws
     * @see String
     * @see List<String>
     */
    List<String> getLowerIdsByUpperId(String upperId);

    /**
     * 获取降低id列表由上id列表
     *
     * @param upperId 上id
     * @return list<字符串>
     * @throws
     * @see List<String>
     * @see List<String>
     */
    List<String> getLowerIdsByUpperIds(List<String> upperId);

    /**
     * 列表由上id列表
     *
     * @param upperIds 上id列表
     * @return list<Space Action 关系>
     * @throws
     * @see List<String>
     * @see List<SpaceActionRelation>
     */
    List<SpaceActionRelation> listByUpperIds(List<String> upperIds);

    /**
     * 删除由上id列表
     *
     * @param upperIds 上id列表
     * @throws
     * @see List<String>
     */
    void deleteByUpperIds(List<String> upperIds);

    /**
     * 创建实体
     *
     * @param upperId 上id
     * @param lowerId 降低id
     * @return 空间作关系
     * @throws
     * @see String
     * @see String
     * @see SpaceActionRelation
     */
    SpaceActionRelation createEntity(String upperId, String lowerId);
}

