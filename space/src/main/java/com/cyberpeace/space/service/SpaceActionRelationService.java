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
    void createRelation(List<String> upperIds, String resourceId);

    void updateRelation(List<String> upperIds, String resourceId);

    void deleteRelationsByIds(List<String> ids);

    List<String> getUpperIdsByLowerId(String resourceId);

    List<String> getLowerIdsByUpperId(String upperId);

    List<String> getLowerIdsByUpperIds(List<String> upperId);

    List<SpaceActionRelation> listByUpperIds(List<String> upperIds);

    void deleteByUpperIds(List<String> upperIds);

    SpaceActionRelation createEntity(String upperId, String lowerId);
}
