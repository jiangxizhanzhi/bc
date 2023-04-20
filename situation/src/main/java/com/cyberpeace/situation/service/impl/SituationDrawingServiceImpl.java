package com.cyberpeace.situation.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.controller.req.SituationDrawingPageReq;
import com.cyberpeace.situation.controller.req.SituationDrawingReq;
import com.cyberpeace.situation.controller.res.SituationDrawingPageRes;
import com.cyberpeace.situation.dao.entity.SituationDrawing;
import com.cyberpeace.situation.dao.entity.SituationDrawingCategory;
import com.cyberpeace.situation.dao.mapper.SituationDrawingCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDrawingMapper;
import com.cyberpeace.situation.service.SituationDrawingService;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Author xuxz
 * @Date 4/1/2023 下午1:44
 */
@Service
public class SituationDrawingServiceImpl extends ServiceImpl<SituationDrawingMapper, SituationDrawing> implements SituationDrawingService {

    @Autowired
    private SituationDrawingMapper situationDrawingMapper;

    @Autowired
    private SituationDrawingCategoryMapper situationDrawingCategoryMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public String createSituationDrawing(SituationDrawingReq situationDrawingReq) {
        SituationDrawing situationDrawing = changeToSituationDrawing(situationDrawingReq);
        String resourceId = IDGeneratorUtil.getID();
        situationDrawing.setResourceId(resourceId);
        situationDrawing.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationDrawingMapper.insert(situationDrawing);
        return resourceId;
    }

    @Override
    public String updateSituationDrawing(SituationDrawingReq situationDrawingReq) {
        SituationDrawing situationDrawing = changeToSituationDrawing(situationDrawingReq);
        situationDrawing.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationDrawingMapper.updateById(situationDrawing);
        return situationDrawing.getResourceId();
    }

    @Override
    public SituationDrawing getSituationDrawingInfo(String resourceId) {
        SituationDrawing situationDrawing = situationDrawingMapper.selectById(resourceId);
        if (situationDrawing == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return situationDrawing;
    }

    @Override
    public Integer deleteSituationDrawing(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            return situationDrawingMapper.deleteBatchIds(idList);
        }
        return null;
    }

    @Override
    public ListRsp<SituationDrawingPageRes> getSituationDrawingPage(SituationDrawingPageReq situationDrawingPageReq) {
        Page page = situationDrawingPageReq.toPageParam();
        IPage<SituationDrawingPageRes> situationDrawingPageResIPage = situationDrawingMapper.selectPage(page, situationDrawingPageReq);

        List<SituationDrawingPageRes> situationDrawingPageResList = situationDrawingPageResIPage.getRecords();
        List<SituationDrawingCategory> situationDrawingCategories = situationDrawingCategoryMapper.selectList(new LambdaQueryWrapper<>());

        if (CollectionUtils.isNotEmpty(situationDrawingPageResList)) {
            for (SituationDrawingPageRes situationDrawingPageRes : situationDrawingPageResList) {
                String type = situationDrawingPageRes.getType();
                //设置类型
                situationDrawingCategories.forEach(situationDrawingCategory -> {
                    if (ObjectUtil.equal(situationDrawingCategory.getResourceId(), type)) {
                        situationDrawingPageRes.setType(situationDrawingCategory.getName());
                    }
                });
            }
            situationDrawingPageResIPage.setRecords(situationDrawingPageResList);
            return PageUtil.getListResult(situationDrawingPageResIPage);
        }

        return null;
    }

    @Override
    public List<SituationDrawingCategory> getSituationCategory() {
        return situationDrawingCategoryMapper.selectList(new LambdaQueryWrapper<>());
    }

    private SituationDrawing changeToSituationDrawing(SituationDrawingReq situationDrawingReq) {
        String resourceId = situationDrawingReq.getResourceId();
        String name = situationDrawingReq.getName();
        String oldResourceId = situationDrawingMapper.selectByName(name);
        if (StringUtils.isNotBlank(oldResourceId)) {
            if ((StringUtils.isNotBlank(resourceId) && !ObjectUtil.equal(oldResourceId, resourceId)) || StringUtils.isBlank(resourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        }
        return BeanUtils.copy(situationDrawingReq, SituationDrawing.class);
    }
}
