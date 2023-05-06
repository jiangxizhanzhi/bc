package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.constant.topo.Color;
import com.cyberpeace.situation.constant.topo.LineType;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingUpdateReq;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.EventTypeMapper;
import com.cyberpeace.situation.dao.mapper.SituationDynamicEffectMappingMapper;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.service.SituationDynamicEffectMappingService;
import com.cyberpeace.situation.dao.entity.EventType;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.site.RangeContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:40
 */
@Service
public class SituationDynamicEffectMappingServiceImpl extends ServiceImpl<SituationDynamicEffectMappingMapper, DynamicEffectsMapping> implements SituationDynamicEffectMappingService {

    @Autowired
    private SituationDynamicEffectMappingMapper situationDynamicEffectMappingMapper;

    @Autowired
    private EventTypeMapper eventTypeMapper;

    @Autowired
    private ThirdPartyService thirdPartyService;
    
    private void checkEffectsMapExist(String attackType, String subRangeId, String resourceId){
        
        if (situationDynamicEffectMappingMapper.checkEffectsMapExist(attackType, subRangeId, resourceId)){
            throw new BusinessException(SituationErrorCode.DYNAMIC_EFFECT_MAPPING_EXIST);
        }
    }

    @Override
    public String setDynamicEffectsMapping(DynamicEffectMappingReq dynamicEffectMappingReq) {
        DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingReq,
                DynamicEffectsMapping.class);
        dynamicEffectsMapping.setFocus(Boolean.parseBoolean(dynamicEffectMappingReq.getFocus()));
        String userId = UserInfoContext.getUserContext().getUserId();
        dynamicEffectsMapping.setCreateUserId(userId);
        String resourceId = IDGeneratorUtil.getID();
        dynamicEffectsMapping.setResourceId(resourceId);
        RangeContext subRange = thirdPartyService.getRangeById(dynamicEffectsMapping.getCategory());

        if (null == subRange) {
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

		checkEffectsMapExist(dynamicEffectMappingReq.getAttackType(),
				dynamicEffectsMapping.getCategory(), null);

        try {
            situationDynamicEffectMappingMapper.insert(dynamicEffectsMapping);
            return resourceId;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
    }

    @Override
    public ListRsp<DynamicEffectsMapping> getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {
        Page page = dynamicEffectMappingFilterReq.toPageParam();
        ListRsp<DynamicEffectsMapping> dynamicEffectsMappingListRsp =
                PageUtil.getListResult(this.getBaseMapper().selectPage(page, dynamicEffectMappingFilterReq));
        List<DynamicEffectsMapping> list = dynamicEffectsMappingListRsp.getList();
        List<EventType> eventTypeList = eventTypeMapper.getEventTypeContextList(null);
        List<RangeContext> subRanges = thirdPartyService.getRanges(null);
        if (CollectionUtils.isNotEmpty(list)) {
            for (DynamicEffectsMapping dynamicEffectsMapping : list) {
                String colorCode = dynamicEffectsMapping.getColor();
                String lineCode = dynamicEffectsMapping.getLine();
                String attackType = dynamicEffectsMapping.getAttackType();
                String category = dynamicEffectsMapping.getCategory();
                List<LocalType> colorList = Color.getMsgList();
                List<LocalType> lineList = LineType.getMsgList();
                //设置颜色翻译
                colorList.forEach(color -> {
                    if (color.getCode().equals(colorCode)) {
                        dynamicEffectsMapping.setColor(color.getMsg());
                    }
                });
                //设置线条翻译
                lineList.forEach(line -> {
                    if (line.getCode().equals(lineCode)) {
                        dynamicEffectsMapping.setLine(line.getMsg());
                    }
                });
                //设置攻击类型翻译
                eventTypeList.forEach(eventType -> {
                    if (eventType.getCode().equals(attackType)) {
                        dynamicEffectsMapping.setAttackType(eventType.getName());
                    }
                });
                //设置靶场翻译
                subRanges.forEach(rangeContext -> {
                    if (rangeContext.getSubRangeId().equals(category)) {
                        dynamicEffectsMapping.setCategory(rangeContext.getSubRangeName());
                    }
                });
            }
            dynamicEffectsMappingListRsp.setList(list);
        }
        return dynamicEffectsMappingListRsp;
    }

    @Override
    public DynamicEffectsMapping getDynamicEffectsMappingDetail(String resourceId) {
        DynamicEffectsMapping dynamicEffectsMapping = situationDynamicEffectMappingMapper.selectById(resourceId);
        if (null == dynamicEffectsMapping) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return dynamicEffectsMapping;
    }

    @Override
    public Integer deleteDynamicEffectsMapping(BatchDeleteReq batchDeleteReq) {

        List<String> resourceIds = batchDeleteReq.getIds();

        List<DynamicEffectsMapping> dynamicEffectsMappings = situationDynamicEffectMappingMapper.selectBatchIds(resourceIds);
        if (dynamicEffectsMappings.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        situationDynamicEffectMappingMapper.deleteBatchIds(resourceIds);

        return resourceIds.size();
    }

    @Override
    public String patchDynamicEffectsMapping(DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {
        DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingUpdateReq, DynamicEffectsMapping.class);
        dynamicEffectsMapping.setFocus(Boolean.parseBoolean(dynamicEffectMappingUpdateReq.getFocus()));
        DynamicEffectsMapping mapping = situationDynamicEffectMappingMapper.selectById(dynamicEffectMappingUpdateReq.getResourceId());
        if (null == mapping) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        RangeContext subRange = thirdPartyService.getRangeById(dynamicEffectsMapping.getCategory());

        if (null == subRange) {
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

		checkEffectsMapExist(dynamicEffectMappingUpdateReq.getAttackType(),
				dynamicEffectMappingUpdateReq.getCategory(), mapping.getResourceId());

        LambdaQueryWrapper<DynamicEffectsMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicEffectsMapping::getName, dynamicEffectMappingUpdateReq.getName());
        DynamicEffectsMapping one = this.getOne(queryWrapper);
        if (null != one && !Objects.equals(one.getResourceId(), dynamicEffectMappingUpdateReq.getResourceId())) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        situationDynamicEffectMappingMapper.updateById(dynamicEffectsMapping);
        return dynamicEffectMappingUpdateReq.getResourceId();
    }
}
