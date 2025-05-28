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
