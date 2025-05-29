package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;
import com.cyberpeace.space.dao.mapper.SpaceActionRelationMapper;
import com.cyberpeace.space.service.SpaceActionRelationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 空间行动关系Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionRelationService
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 该代码是一个服务类 `SpaceActionRelationServiceImpl`，实现了 `SpaceActionRelationService` 接口。它主要用于处理与 `SpaceActionRelation` 实体相关的业务逻辑，包括创建、更新、删除以及查询操作。 
 * #### 主要功能模块 
 * 1. **创建关系** 
 * - 方法 `createRelation`：根据给定的上层 ID 列表 (`upperIds`) 和资源 ID (`resourceId`) 创建多个关系。 
 * - 使用了工具方法 `StreamApiUtils.distinct` 和 `StreamApiUtils.map` 来去重并生成实体对象列表，最后通过批量保存接口 `saveBatch` 完成数据插入。 
 * 2. **更新关系** 
 * - 方法 `updateRelation`：先删除与指定资源 ID 相关的所有旧关系，然后调用 `createRelation` 方法重新创建新的关系。 
 * 3. **删除关系** 
 * - 方法 `deleteRelationsByIds`：根据 ID 列表删除上下层级的关系。 
 * - 方法 `deleteByUpperIds`：根据上层 ID 列表删除对应的关系。 
 * 4. **查询关系** 
 * - 方法 `getUpperIdsByLowerId`：根据下级 ID 查询其对应的上级 ID 列表。 
 * - 方法 `getLowerIdsByUpperId`：根据上级 ID 查询其对应的下级 ID 列表。 
 * - 方法 `getLowerIdsByUpperIds`：批量查询多个上级 ID 对应的下级 ID 列表。 
 * - 方法 `listByUpperIds`：根据上级 ID 列表查询所有相关的关系记录。 
 * 5. **创建实体** 
 * - 方法 `createEntity`：用于生成 `SpaceActionRelation` 实体对象，同时进行必要的参数校验。 
 * #### 技术特点 
 * - **MyBatis-Plus**：利用了 MyBatis-Plus 的 `LambdaQueryWrapper` 进行条件构造，简化了数据库操作。 
 * - **流式操作**：使用了 `StreamApiUtils` 工具类中的 `distinct` 和 `map` 方法来处理集合数据，提高了代码的可读性和简洁性。 
 * - **异常处理**：在关键方法中加入了业务异常抛出逻辑，例如当输入的 ID 为空时会抛出 `BusinessException`。 
 * #### 总结 
 * 此服务类封装了关于 `SpaceActionRelation` 的核心业务逻辑，提供了从关系的增删改查到具体实体创建的一系列功能。通过合理运用 MyBatis-Plus 和 Java 8 的流式操作，使得代码结构清晰且易于维护。 
**/
@Service
@Slf4j
public class SpaceActionRelationServiceImpl extends ServiceImpl<SpaceActionRelationMapper, SpaceActionRelation> implements SpaceActionRelationService {

    /**
     * 创建关系
     *
     * @param upperIds   上id列表
     * @param resourceId 资源id
     * @throws
     * @see List<String>
     * @see String
     */
    @Override
    public void createRelation(List<String> upperIds, String resourceId) {
        if (upperIds.isEmpty()) {
            return;
        }
        upperIds = StreamApiUtils.distinct(upperIds);
        List<SpaceActionRelation> relations = StreamApiUtils.map(upperIds, up -> this.createEntity(up, resourceId));
        this.saveBatch(relations);
    }

    /**
     * 更新关系
     *
     * @param upperIds   上id列表
     * @param resourceId 资源id
     * @throws
     * @see List<String>
     * @see String
     */
    @Override
    public void updateRelation(List<String> upperIds, String resourceId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getLowerId, resourceId);
        this.remove(wrapper);
        this.createRelation(upperIds, resourceId);
    }

    /**
     * 删除关系由id列表
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    @Override
    public void deleteRelationsByIds(List<String> ids) {
        if(ids.isEmpty()){
            return;
        }
        LambdaQueryWrapper<SpaceActionRelation> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(SpaceActionRelation::getLowerId, ids);
        this.remove(wrapper1);
        LambdaQueryWrapper<SpaceActionRelation> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.in(SpaceActionRelation::getUpperId, ids);
        this.remove(wrapper2);
    }

    /**
     * 获取上id列表由降低id
     *
     * @param resourceId 资源id
     * @return {@code List<String> }
     * @throws
     * @see String
     * @see List<String>
     */
    @Override
    public List<String> getUpperIdsByLowerId(String resourceId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getLowerId, resourceId);
        List<SpaceActionRelation> relations  = this.list(wrapper);
        return StreamApiUtils.map(relations, SpaceActionRelation::getUpperId);
    }

    /**
     * 获取降低id列表由上id
     *
     * @param upperId 上id
     * @return {@code List<String> }
     * @throws
     * @see String
     * @see List<String>
     */
    @Override
    public List<String> getLowerIdsByUpperId(String upperId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getUpperId, upperId);
        List<SpaceActionRelation> relations  = this.list(wrapper);
        return StreamApiUtils.map(relations, SpaceActionRelation::getLowerId);
    }

    /**
     * 获取降低id列表由上id列表
     *
     * @param upperIds 上id列表
     * @return {@code List<String> }
     * @throws
     * @see List<String>
     * @see List<String>
     */
    @Override
	public List<String> getLowerIdsByUpperIds(List<String> upperIds) {
		if (CollectionUtils.isEmpty(upperIds)) {
			return Lists.newArrayList();
		}
		LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SpaceActionRelation::getUpperId, upperIds);
		List<SpaceActionRelation> relations = this.list(wrapper);
		return StreamApiUtils.map(relations, SpaceActionRelation::getLowerId);
	}

    /**
     * 列表由上id列表
     *
     * @param upperIds 上id列表
     * @return {@code List<SpaceActionRelation> }
     * @throws
     * @see List<String>
     * @see List<SpaceActionRelation>
     */
    @Override
    public List<SpaceActionRelation> listByUpperIds(List<String> upperIds) {
        if(upperIds.isEmpty()){
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SpaceActionRelation::getUpperId, upperIds);
        return this.list(wrapper);
    }

    /**
     * 删除由上id列表
     *
     * @param upperIds 上id列表
     * @throws
     * @see List<String>
     */
    @Override
    public void deleteByUpperIds(List<String> upperIds) {
        if(upperIds.isEmpty()){
            return;
        }
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SpaceActionRelation::getUpperId, upperIds);
        this.remove(wrapper);
    }


    /**
     * 创建实体
     *
     * @param upperId 上id
     * @param lowerId 降低id
     * @return {@code SpaceActionRelation }
     * @throws
     * @see String
     * @see String
     * @see SpaceActionRelation
     */
    @Override
    public SpaceActionRelation createEntity(String upperId, String lowerId){
		if (StringUtils.isBlank(upperId) || StringUtils.isBlank(lowerId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
        SpaceActionRelation relation = new SpaceActionRelation();
        relation.setUpperId(upperId);
        relation.setLowerId(lowerId);
        return relation;
    }
}

