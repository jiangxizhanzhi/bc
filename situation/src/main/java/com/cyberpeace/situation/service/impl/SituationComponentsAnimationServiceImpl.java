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
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponentsCategory;
import com.cyberpeace.situation.dao.mapper.SituationComponentAnimationMapper;
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

    @Override
    public String addComponentAnimation(SituationComponentsAnimationAddReq situationComponentsAnimationAddReq) {
        SituationComponentsAnimation situationComponentsAnimation = new SituationComponentsAnimation();
        BeanUtils.copyProperties(situationComponentsAnimationAddReq, situationComponentsAnimation);
//        checkChartId(situationComponentsAnimation);
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
//        checkChartId(situationComponentsAnimation);
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
    public Integer deleteComponentAnimation(String resourceId) {
        SituationComponentsAnimation situationComponentsAnimation = situationComponentAnimationMapper.selectById(resourceId);
        if(null == situationComponentsAnimation){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        int i = situationComponentAnimationMapper.deleteById(resourceId);
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
        String endTime = req.getEndtime();
        Date startTimeDate;
        Date endTimeDate;
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            endTimeDate = TimeUtil.toDate2(endTime);
            startTimeDate = TimeUtil.toDate2(startTime);
            if(endTimeDate.after(startTimeDate)){
                throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
            }
            queryWrapper.ge(SituationComponentsAnimation::getCreateTime, startTimeDate);
            queryWrapper.le(SituationComponentsAnimation::getCreateTime, endTimeDate);
        }
//        LambdaQueryWrapper<TwoDimensionalComponents> query = new LambdaQueryWrapper<>();
//        List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper.selectList(query);
//        Map<String, String> toMap = StreamApiUtils.toMap(twoDimensionalComponents, TwoDimensionalComponents::getResourceId, twoDimensionalComponent -> twoDimensionalComponent.getName());
        Map<String, String> toMap = new HashMap<>();
        List<SituationComponentsAnimation> filterRes = situationComponentAnimationMapper.selectList(queryWrapper);
        List<SituationComponentsAnimationRes> res = new ArrayList<>();
        filterRes.forEach(tmp -> {
            SituationComponentsAnimationRes situationComponentsAnimationRes = new SituationComponentsAnimationRes();
            BeanUtils.copyProperties(tmp, situationComponentsAnimationRes);
            String chartName = toMap.get(tmp.getChartId());
            chartName = StringUtils.isEmpty(chartName) ? "" : chartName;
            situationComponentsAnimationRes.setChartName(chartName);
            res.add(situationComponentsAnimationRes);
        });
        return res;
    }
}
