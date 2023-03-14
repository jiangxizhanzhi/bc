package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsAnimationRes;
import com.cyberpeace.situation.controller.res.SituationComponentsChartDetailsRes;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponentsCategory;
import com.cyberpeace.situation.dao.mapper.SituationComponentAnimationMapper;
import com.cyberpeace.situation.dao.mapper.TwoDimensionalComponentsCategoryMapper;
import com.cyberpeace.situation.dao.mapper.TwoDimensionalComponentsMapper;
import com.cyberpeace.situation.service.SituationComponentAnimationService;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class SituationComponentsAnimationServiceImpl extends ServiceImpl<SituationComponentAnimationMapper, SituationComponentsAnimation> implements SituationComponentAnimationService {

    @Autowired
    private SituationComponentAnimationMapper situationComponentAnimationMapper;

    @Autowired
    private TwoDimensionalComponentsMapper twoDimensionalComponentsMapper;

    @Autowired
    private TwoDimensionalComponentsCategoryMapper twoDimensionalComponentsCategoryMapper;

    @Override
    public String addComponentAnimation(SituationComponentsAnimationAddReq situationComponentsAnimationAddReq) {
        SituationComponentsAnimation situationComponentsAnimation = new SituationComponentsAnimation();
        BeanUtils.copyProperties(situationComponentsAnimationAddReq, situationComponentsAnimation);
        checkChartId(situationComponentsAnimation);
        try {
            situationComponentAnimationMapper.insert(situationComponentsAnimation);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        return null;
    }

    private void checkChartId(SituationComponentsAnimation situationComponentsAnimation) {
        String chartId = situationComponentsAnimation.getChartId();
        if(StringUtils.isEmpty(chartId)){
            return;
        }
        TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper.selectById(chartId);
        if(null == twoDimensionalComponents){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public SituationComponentsAnimationRes updateComponentAnimation(SituationComponentsAnimationUpdateReq situationComponentsAnimationUpdateReq) {
        SituationComponentsAnimation situationComponentsAnimation = new SituationComponentsAnimation();
        BeanUtils.copyProperties(situationComponentsAnimationUpdateReq, situationComponentsAnimation);
        checkChartId(situationComponentsAnimation);
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("name", situationComponentsAnimationUpdateReq.getName());
        List<SituationComponentsAnimation> situationComponentsAnimations = situationComponentAnimationMapper.selectByMap(filterMap);
        if(!situationComponentsAnimations.isEmpty()){
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        int i = situationComponentAnimationMapper.updateById(situationComponentsAnimation);
        return null;
    }

    @Override
    public SituationComponentsAnimation getComponentAnimation(String resourceId) {

        SituationComponentsAnimation situationComponentsAnimation = situationComponentAnimationMapper.selectById(resourceId);
        if(null == situationComponentsAnimation){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return situationComponentsAnimation;
    }

    @Override
    public Integer deleteComponentAnimation(List<String> resourceIds) {
        List<SituationComponentsAnimation> situationComponentsAnimations = situationComponentAnimationMapper.selectBatchIds(resourceIds);
        if(situationComponentsAnimations.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        int i = situationComponentAnimationMapper.deleteBatchIds(resourceIds);
        return i;
    }

    @Override
    public List<SituationComponentsAnimationRes> getComponentAnimations(SituationComponentsAnimationReq req) {
        LambdaQueryWrapper<SituationComponentsAnimation> queryWrapper = new LambdaQueryWrapper<>();
        String name = req.getName();
        if (null != name) {
            queryWrapper.like(SituationComponentsAnimation::getName, name);

        }
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        Date startTimeDate;
        Date endTimeDate;
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            endTimeDate = TimeUtil.toDate2(endTime);
            startTimeDate = TimeUtil.toDate2(startTime);
            if(endTimeDate.before(startTimeDate)){
                throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
            }
            queryWrapper.ge(SituationComponentsAnimation::getCreateTime, startTimeDate);
            queryWrapper.le(SituationComponentsAnimation::getCreateTime, endTimeDate);
        }
        List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper.selectList(null);
        Map<String, TwoDimensionalComponents> toMap = StreamApiUtils.toMap(twoDimensionalComponents, TwoDimensionalComponents::getResourceId, twoDimensionalComponent -> twoDimensionalComponent);
        List<SituationComponentsAnimation> filterRes = situationComponentAnimationMapper.selectList(queryWrapper);
        List<SituationComponentsAnimationRes> res = new ArrayList<>();
        List<TwoDimensionalComponentsCategory> twoDimensionalComponentsCategories = twoDimensionalComponentsCategoryMapper.selectList(null);
        Map<String, String> categoryMap = StreamApiUtils.toMap(twoDimensionalComponentsCategories,
                TwoDimensionalComponentsCategory::getResourceId, twoDimensionalComponentsCategory -> twoDimensionalComponentsCategory.getName());

        filterRes.forEach(tmp -> {
            SituationComponentsAnimationRes situationComponentsAnimationRes = new SituationComponentsAnimationRes();
            BeanUtils.copyProperties(tmp, situationComponentsAnimationRes);
            TwoDimensionalComponents chart = toMap.get(tmp.getChartId());
            if(null != chart){
                String chartName = chart.getName();
                String type = chart.getType();
                String chartCategory = categoryMap.get(type);
                chartCategory = StringUtils.isEmpty(chartCategory) ? "" : chartCategory;
                situationComponentsAnimationRes.setChartCategoryName(chartCategory);
                situationComponentsAnimationRes.setChartName(chartName);

            }
            res.add(situationComponentsAnimationRes);
        });
        return res;
    }

    @Override
    public List<SituationComponentsChartDetailsRes> getChartDetails() {
        List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper.selectList(null);
        List<TwoDimensionalComponentsCategory> twoDimensionalComponentsCategories = twoDimensionalComponentsCategoryMapper.selectList(null);
        Map<String, String> categoryMap = StreamApiUtils.toMap(twoDimensionalComponentsCategories,
                TwoDimensionalComponentsCategory::getResourceId, twoDimensionalComponentsCategory -> twoDimensionalComponentsCategory.getName());
        List<SituationComponentsChartDetailsRes> res = new ArrayList<>();
        twoDimensionalComponents.forEach(twoDimensionalComponent -> {
            SituationComponentsChartDetailsRes situationComponentsChartDetailsRes = new SituationComponentsChartDetailsRes();
            String chartName = twoDimensionalComponent.getName();
            chartName = StringUtils.isEmpty(chartName) ? "" : chartName;
//            situationComponentsChartDetailsRes.setChartName(chartName);
            String chartCategory = categoryMap.get(twoDimensionalComponent.getType());
            chartCategory = StringUtils.isEmpty(chartCategory) ? "" : chartCategory;
            situationComponentsChartDetailsRes.setChartCategoryName(chartCategory);
            res.add(situationComponentsChartDetailsRes);
        });
        return res;
    }
}
