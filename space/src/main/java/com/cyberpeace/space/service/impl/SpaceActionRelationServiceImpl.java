package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;
import com.cyberpeace.space.dao.mapper.SpaceActionRelationMapper;
import com.cyberpeace.space.service.SpaceActionRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpaceActionRelationServiceImpl extends ServiceImpl<SpaceActionRelationMapper, SpaceActionRelation> implements SpaceActionRelationService {

    @Override
    public void createRelation(List<String> upperIds, String resourceId) {
        if(upperIds.isEmpty()){
            return;
        }
        List<SpaceActionRelation> relations = StreamApiUtils.map(upperIds, up -> this.createEntity(up, resourceId));
        this.saveBatch(relations);
    }

    @Override
    public void updateRelation(List<String> upperIds, String resourceId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getLowerId, resourceId);
        this.remove(wrapper);
        this.createRelation(upperIds, resourceId);
    }

    @Override
    public void deleteRelationsByIds(List<String> ids) {
        if(ids.isEmpty()){
            return;
        }
        LambdaQueryWrapper<SpaceActionRelation> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(SpaceActionRelation::getLowerId, ids);
        this.remove(wrapper1);
        LambdaQueryWrapper<SpaceActionRelation> wrapper2 = new LambdaQueryWrapper<>();
        wrapper1.in(SpaceActionRelation::getUpperId, ids);
        this.remove(wrapper2);
    }

    @Override
    public List<String> getUpperIdsByLowerId(String resourceId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getLowerId, resourceId);
        List<SpaceActionRelation> relations  = this.list(wrapper);
        return StreamApiUtils.map(relations, SpaceActionRelation::getUpperId);
    }

    @Override
    public List<String> getLowerIdsByUpperId(String upperId) {
        LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceActionRelation::getUpperId, upperId);
        List<SpaceActionRelation> relations  = this.list(wrapper);
        return StreamApiUtils.map(relations, SpaceActionRelation::getLowerId);
    }


    private SpaceActionRelation createEntity(String upperId, String lowerId){
        SpaceActionRelation relation = new SpaceActionRelation();
        relation.setUpperId(upperId);
        relation.setLowerId(lowerId);
        return relation;
    }
}
