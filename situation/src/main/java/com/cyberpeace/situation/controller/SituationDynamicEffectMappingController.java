package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibraryCategory;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsLibraryCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDynamicEffectMappingMapper;
import com.cyberpeace.situation.service.SituationDynamicEffectMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午3:36
 */
@Api(tags = "组件库-动效映射")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/dynamic/effect/mapping")
public class SituationDynamicEffectMappingController {

    @Autowired
    private SituationDynamicEffectMappingMapper situationDynamicEffectMappingMapper;

    @Autowired
    private DynamicEffectsLibraryCategoryMapper dynamicEffectsLibraryCategoryMapper;

    @Autowired
    private SituationDynamicEffectMappingService situationDynamicEffectMappingService;

    @ApiOperation("新增动效库")
    @PostMapping("")
    public BaseRsp<DynamicEffectsMapping>
    setDynamicEffectsMapping(@RequestBody @Validated(Create.class) DynamicEffectMappingReq dynamicEffectMappingReq) {

        DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingReq,
                DynamicEffectsMapping.class);
        String userId = UserInfoContext.getUserContext().getUserId();
        dynamicEffectsMapping.setCreateUserId(userId);

        DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectsMapping.getCategory());
        if(null == category){
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

        try{
            situationDynamicEffectMappingMapper.insert(dynamicEffectsMapping);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        return new BaseRsp<>(dynamicEffectsMapping);
    }

    @ApiOperation("查询动效库")
    @GetMapping("/list")
    public BaseRsp<List<DynamicEffectsMapping>>
    getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {
        LambdaQueryWrapper<DynamicEffectsMapping> queryWrapper = new LambdaQueryWrapper<>();
        String name = dynamicEffectMappingFilterReq.getName();
        if (null != name){
            queryWrapper.like(DynamicEffectsMapping::getName, name);

        }
        String type = dynamicEffectMappingFilterReq.getCategory();
        if (null != type) {
            DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(type);
            if (null == category) {
                throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
            }
        }

        if (null != type){
            queryWrapper.eq(DynamicEffectsMapping::getCategory, type);
        }
        queryWrapper.orderByDesc(DynamicEffectsMapping::getCreateTime);
        List<DynamicEffectsMapping> res = situationDynamicEffectMappingMapper.selectList(queryWrapper);
        return new BaseRsp<>(res);
    }

    @ApiOperation("查询动效库详情")
    @GetMapping("/")
    public BaseRsp<DynamicEffectsMapping>
    getDynamicEffectsMappingDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

        DynamicEffectsMapping dynamicEffectsMapping = situationDynamicEffectMappingMapper.selectById(resourceId);
        if(null == dynamicEffectsMapping){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return new BaseRsp<>(dynamicEffectsMapping);
    }

    @ApiOperation("删除动效库")
    @DeleteMapping("")
    public BaseRsp deleteDynamicEffectsMapping(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

        List<String> resourceIds = batchDeleteReq.getIds();

        List<DynamicEffectsMapping> dynamicEffectsMappings = situationDynamicEffectMappingMapper.selectBatchIds(resourceIds);
        if (dynamicEffectsMappings.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        situationDynamicEffectMappingMapper.deleteBatchIds(resourceIds);
        return new BaseRsp<>();
    }

    @ApiOperation("修改动效库")
    @PatchMapping("")
    public BaseRsp patchDynamicEffectsMapping(@Validated(Update.class) DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {
        DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingUpdateReq, DynamicEffectsMapping.class);
        DynamicEffectsMapping mapping = situationDynamicEffectMappingMapper.selectById(dynamicEffectMappingUpdateReq.getResourceId());
        if(null == mapping){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectMappingUpdateReq.getCategory());
        if(null == category){
            throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
        }

        LambdaQueryWrapper<DynamicEffectsMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicEffectsMapping::getName, dynamicEffectMappingUpdateReq.getName());
        DynamicEffectsMapping one = situationDynamicEffectMappingService.getOne(queryWrapper);
        if (null != one && !Objects.equals(one.getResourceId(), dynamicEffectMappingUpdateReq.getResourceId())) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }

        situationDynamicEffectMappingMapper.updateById(dynamicEffectsMapping);
        return new BaseRsp<>();
    }

}
