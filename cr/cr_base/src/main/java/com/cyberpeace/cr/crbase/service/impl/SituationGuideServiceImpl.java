package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.AttackTopFiveRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SituationGuideRes;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideSchemeMapper;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.WsMultiNodeGuide;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SituationGuideServiceImpl extends ServiceImpl<SituationGuideSchemeMapper, SituationGuideScheme> implements SituationGuideService {

    @Autowired
    private SituationGuideHisMapper situationGuideHisMapper;

    @Autowired
    private TopoHandlerService topoHandlerService;

    @Autowired
    private SituationHandlerService situationHandlerService;

    public Integer MIN_STR_LEN = 1;

    public Integer MAX_NAME_STR_LEN = 255;

    @Override
    public ListRsp<SituationGuideScheme> listBySceneId(ListGuideSchemeReq req) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getSceneId, req.getSceneId());
        Page<SituationGuideScheme> page = page(req.toPageParam(), queryWrapper);
        return PageUtil.getListResult(page);
    }

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
        SituationGuideRes resp = BeanUtils.copy(situationGuideScheme, SituationGuideRes.class);
        SituationGuideScheme.EventType eventType = SituationGuideScheme.EventType.getEventTypeByCode(resp.getEventType());

        switch (eventType) {
            case ATTACK:
                SituationGuideScheme.AttackUrlEnum attackUrlEnum = SituationGuideScheme.AttackUrlEnum.getEnumByCode(resp.getCategory());
                resp.setSuccessUrl(attackUrlEnum.getSuccessUrl());
                resp.setFailUrl(attackUrlEnum.getFailUrl());
                break;
            case DEFENSE:
                SituationGuideScheme.DefenseUrlEnum defenseUrlEnum = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(resp.getCategory());
                resp.setSuccessUrl(defenseUrlEnum.getSuccessUrl());
                resp.setFailUrl(defenseUrlEnum.getFailUrl());
            case BLOCKING:
                String oneUrl = SituationGuideScheme.BlockingAndBelongingUrlEnum.EXCITATION.getOneUrl();
                resp.setSuccessUrl(oneUrl);
                break;
            case BELONGING:
                SituationGuideScheme.BlockingAndBelongingUrlEnum belongingUrlEnum = SituationGuideScheme.BlockingAndBelongingUrlEnum.ANALYZE;
                resp.setRedUrl(belongingUrlEnum.getOneUrl());
                resp.setBlueUrl(belongingUrlEnum.getTwoUrl());
                break;
            default:
        }
        return resp;
    }

    @Override
    public UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq) {
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(updateGuideSchemeReq, SituationGuideScheme.class);
        updateById(situationGuideScheme);
        return updateGuideSchemeReq;
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
        validateNameLen(name, SituationErrorCode.INVALID_GUIDE_SCHEME_NAME);
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getEventName, name);
        queryWrapper.eq(SituationGuideScheme::getSceneId, sceneId);
        if (StringUtils.isNotBlank(resourceId)) {
            queryWrapper.ne(SituationGuideScheme::getResourceId, resourceId);
        }
        List<SituationGuideScheme> situationGuideSchemes = list(queryWrapper);
        if (!CollectionUtils.isEmpty(situationGuideSchemes)) {
            log.error("dynamic scheme name is duplicated, name:{}", name);
            throw new BusinessException(SituationErrorCode.DUPLICATED_GUIDE_SCHEME_NAME);
        }
    }

    @Override
    public void guideImplement(GuideImplementReq req) {
        SituationGuideScheme guideScheme = getById(req.getResourceId());

        //增加历史记录
        SituationGuideHistory his = BeanUtils.copy(guideScheme, SituationGuideHistory.class);
        his.setResourceId(IDGeneratorUtil.getID());
        his.setCreateTime(new Date());
        his.setUpdateTime(new Date());
        situationGuideHisMapper.insert(his);

        pushDataToRoomByGuide(req.getIsInstance(), his);
    }

    @Override
    public void pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his) {
        String sceneId = his.getSceneId();
        List<String> elementIds = getElementIds(sceneId, isInstance);
        WsMultiNodeGuide wsMultiNodeGuide = new WsMultiNodeGuide();
        wsMultiNodeGuide.setSceneId(his.getSceneId());
        wsMultiNodeGuide.setDuration(3);
        wsMultiNodeGuide.setName(his.getEventName());
        wsMultiNodeGuide.setCategory(his.getCategory());
        wsMultiNodeGuide.setEventType(his.getEventType());
        wsMultiNodeGuide.setSourceId(his.getSource());
        wsMultiNodeGuide.setTargetId(his.getTarget());
        wsMultiNodeGuide.setResult(his.getResult());
        wsMultiNodeGuide.setRole(his.getRole());

        WsMessage<WsMultiNodeGuide> wsMessage;
        if (elementIds.contains(his.getTarget())) {
            wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeGuide);
        } else {
            wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeGuide);
        }
        situationHandlerService.pushDataToRoom(wsMessage, wsMultiNodeGuide.getSceneId());
    }

    @Override
    public AttackTopFiveRsp attackTop5(String sceneId) {
        return new AttackTopFiveRsp();
    }

    public List<String> getElementIds(String sceneId, Boolean isInstance) {

        SceneData sceneData;
        if (isInstance != null && !isInstance) {
            sceneData = topoHandlerService.getSceneData(null, sceneId);
        } else {
            sceneData = topoHandlerService.getSceneData(sceneId, null);
        }
        SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
        List<ElementInfo> elementInfoList = Lists
                .newArrayList(situationTopo.getElements().values());
        return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
    }

    public void validateLength(String target, Integer minLen, Integer maxLen, String errorCode) {
        if (StringUtils.isBlank(target)) {
            log.error("length of name is null, minLen is {}, maxLen is {}", minLen, maxLen);
            throw new BusinessException(errorCode, minLen, maxLen);
        }
        try {
            String targetStr = new String(target.getBytes("GBK"), StandardCharsets.ISO_8859_1);
            int len = targetStr.length();
            if (len < minLen || len > maxLen) {
                log.error("length of name is {}, minLen is {}, maxLen is {}", len, minLen, maxLen);
                throw new BusinessException(errorCode, minLen, maxLen);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("target.getBytes error");
        }
    }

    public void validateNameLen(String target, String errorCode) {
        validateLength(target, MIN_STR_LEN, MAX_NAME_STR_LEN, errorCode);
    }

}
