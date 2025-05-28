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
