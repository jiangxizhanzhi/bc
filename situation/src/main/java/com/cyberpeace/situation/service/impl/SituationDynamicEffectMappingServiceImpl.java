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
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingUpdateReq;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibraryCategory;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsLibraryCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDynamicEffectMappingMapper;
import com.cyberpeace.situation.service.SituationDynamicEffectMappingService;
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
    private DynamicEffectsLibraryCategoryMapper dynamicEffectsLibraryCategoryMapper;

    @Override
    public String setDynamicEffectsMapping(DynamicEffectMappingReq dynamicEffectMappingReq) {
        DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingReq,
                DynamicEffectsMapping.class);
        String userId = UserInfoContext.getUserContext().getUserId();
        dynamicEffectsMapping.setCreateUserId(userId);
        String resourceId = IDGeneratorUtil.getID();
        dynamicEffectsMapping.setResourceId(resourceId);
        DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectsMapping.getCategory());

        if (null == category) {
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

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
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, dynamicEffectMappingFilterReq));
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
        DynamicEffectsMapping mapping = situationDynamicEffectMappingMapper.selectById(dynamicEffectMappingUpdateReq.getResourceId());
        if (null == mapping) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectMappingUpdateReq.getCategory());
        if (null == category) {
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

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
