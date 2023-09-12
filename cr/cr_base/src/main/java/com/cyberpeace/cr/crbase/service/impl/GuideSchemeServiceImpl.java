package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.SituationGuideRes;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideSchemeMapper;
import com.cyberpeace.cr.crbase.service.GuideSchemeService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
public class GuideSchemeServiceImpl extends ServiceImpl<SituationGuideSchemeMapper, SituationGuideScheme> implements GuideSchemeService {

    @Autowired
    private SituationGuideSchemeMapper situationGuideSchemeMapper;

    public Integer MIN_STR_LEN = 1;

    public Integer MAX_NAME_STR_LEN = 255;

    @Override
    public String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq) {
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(addGuideSchemeReq, SituationGuideScheme.class);
        situationGuideScheme.setResourceId(IDGeneratorUtil.getID());
        save(situationGuideScheme);
        return situationGuideScheme.getResourceId();
    }

    @Override
    public SituationGuideRes getGuideSchemeById(String resourceId) {
        SituationGuideScheme situationGuideScheme = getById(resourceId);
        if (situationGuideScheme == null) {
            log.error("get dynamic scheme by id ({}) error, dynamic scheme not found", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return BeanUtils.copy(situationGuideScheme, SituationGuideRes.class);
    }

    @Override
    public GuideSchemeReq updateGuideScheme(GuideSchemeReq guideSchemeReq) {
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(guideSchemeReq, SituationGuideScheme.class);
        updateById(situationGuideScheme);
        return guideSchemeReq;
    }

    @Override
    public void deleteGuideScheme(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            removeByIds(ids);
        }
    }

    /**
     * 校验导调方案是否重名
     */
    @Override
    public void validGuideSchemeName(String name, String sceneId, String resourceId) {
        validateNameLen(name, SituationErrorCode.INVALID_DYNAMIC_SCHEME_NAME);
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getName, name);
        queryWrapper.eq(SituationGuideScheme::getSceneId, sceneId);
        if (StringUtils.isNotBlank(resourceId)) {
            queryWrapper.ne(SituationGuideScheme::getResourceId, resourceId);
        }
        List<SituationGuideScheme> situationGuideSchemes = list(queryWrapper);
        if (!CollectionUtils.isEmpty(situationGuideSchemes)) {
            log.error("dynamic scheme name is duplicated, name:{}", name);
            throw new BusinessException(SituationErrorCode.DUPLICATED_DYNAMIC_SCHEME_NAME);
        }
    }


    public void validateLength(String target, Integer minLen, Integer maxLen, String errorCode) {
        if (target == null) {
            if (minLen == 0) {
                return;
            } else {
                log.error("length of name is null, minLen is {}, maxLen is {}", minLen, maxLen);
                throw new BusinessException(errorCode, minLen, maxLen);
            }
        }
        try {
            String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
            int len = targetStr.length();
            if (len < minLen || len > maxLen) {
                log.error("length of name is {}, minLen is {}, maxLen is {}", len, minLen, maxLen);
                throw new BusinessException(errorCode, minLen, maxLen);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void validateNameLen(String target, String errorCode) {
        validateLength(target, MIN_STR_LEN, MAX_NAME_STR_LEN, errorCode);
    }

}
