package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.impl.HikEnvService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.CommonComponentsReq;
import com.cyberpeace.situation.controller.req.HikVisionReq;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import com.cyberpeace.situation.dao.mapper.SituationCommonComponentsMapper;
import com.cyberpeace.situation.service.SituationCommonComponentsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

import static com.cyberpeace.situation.dao.entity.DashboardComponents.ComponentType.COMMON_CONTROLS;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:11
 */
@Service
public class SituationCommonComponentsServiceImpl extends ServiceImpl<SituationCommonComponentsMapper, SituationCommonComponents> implements SituationCommonComponentsService {

    @Autowired
    private SituationCommonComponentsMapper situationCommonComponentsMapper;

    @Autowired
    private HikEnvService hikEnvService;

    private String HIK_DEVICE_CHANNELS;

    private String HIK_DEVICE_HISTORY_CHANNELS;

    private String HIK_HTTP_FLV;

    private String HIK_HTTP_FLV_EXIT;

    @PostConstruct
    public void init() {
        HIK_DEVICE_CHANNELS = hikEnvService.getHIKHost()
                + ConfigUtil.getContextProperty("hik.device.channels");
        HIK_DEVICE_HISTORY_CHANNELS = hikEnvService.getHIKHost()
                + ConfigUtil.getContextProperty("hik.device.history.channels");
        HIK_HTTP_FLV = hikEnvService.getHIKHost()
                + ConfigUtil.getContextProperty("hik.http.flv");
        HIK_HTTP_FLV_EXIT = hikEnvService.getHIKHost() + ConfigUtil.getContextProperty("hik.http.flv.exit");

    }

    @Override
    public ListRsp<SituationCommonComponentsPageRes> getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
        Page page = situationCommonComponentsPageReq.toPageParam();
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, situationCommonComponentsPageReq));
    }

    @Override
    public SituationCommonComponentsRes getComponentsInfo(String resourceId) {
        SituationCommonComponents situationCommonComponents = situationCommonComponentsMapper.selectById(resourceId);
        if (situationCommonComponents == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationCommonComponentsRes copy = BeanUtils.copy(situationCommonComponents, SituationCommonComponentsRes.class);
        HashMap<Integer, String> commonComponentsType = SituationCommonComponents.getCommonComponentsType();
        Integer type = situationCommonComponents.getType();
        if (commonComponentsType.containsKey(type)) {
            copy.setTypeName(commonComponentsType.get(type));
        }
        return copy;
    }

    @Override
    public String createComponents(CommonComponentsReq commonComponentsReq) {
        String name = commonComponentsReq.getName();
        List<SituationCommonComponents> situationCommonComponentsList = situationCommonComponentsMapper.selectByName(name);
        if (CollectionUtils.isNotEmpty(situationCommonComponentsList)) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        SituationCommonComponents commonComponents = BeanUtils.copy(commonComponentsReq, SituationCommonComponents.class);
        String resourceId = IDGeneratorUtil.getID();
        commonComponents.setResourceId(resourceId);
        commonComponents.setComponentType(COMMON_CONTROLS.getCode());
        situationCommonComponentsMapper.insert(commonComponents);
        return resourceId;
    }

    @Override
    public String updateComponents(CommonComponentsReq commonComponentsReq) {
        String resourceId = commonComponentsReq.getResourceId();
        SituationCommonComponents commonComponents = situationCommonComponentsMapper.selectById(resourceId);
        if (commonComponents == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationCommonComponents copy = BeanUtils.copy(commonComponentsReq, SituationCommonComponents.class);
        situationCommonComponentsMapper.updateById(copy);
        return resourceId;
    }

    @Override
    public Integer deleteComponents(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            situationCommonComponentsMapper.deleteBatchIds(idList);
        }
        return idList.size();
    }

    @Override
    public JSONObject getHikVisionRtsp(HikVisionReq hikVisionReq) {
        String deviceIp = hikVisionReq.getDeviceIp();
        String devicePort = hikVisionReq.getDevicePort();
        String username = hikVisionReq.getUsername();
        String password = hikVisionReq.getPassword();
        String endTime = hikVisionReq.getEndTime();
        String startTime = hikVisionReq.getStartTime();
        JSONObject jsonObject;
        String url;
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            url = String.format(HIK_DEVICE_HISTORY_CHANNELS, deviceIp, devicePort, username, password, startTime, endTime);
        } else {
            url = String.format(HIK_DEVICE_CHANNELS, deviceIp, devicePort, username, password);
        }
        jsonObject = HttpClientUtil.rawSpecialGet(url, JSONObject.class);
        if (jsonObject == null) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_RESPONSE_JSON);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getHikVisionFLv(String rtspKey, Double setpts) {
        String url = String.format(HIK_HTTP_FLV, rtspKey, setpts);
        JSONObject jsonObject = HttpClientUtil.rawGet(url, JSONObject.class);
        if (jsonObject == null) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_RESPONSE_JSON);
        }
        return jsonObject;
    }

    @Override
    public JSONObject exitHikVisionFLv(String rtspKey) {
        String url = String.format(HIK_HTTP_FLV_EXIT, rtspKey);
        JSONObject jsonObject = HttpClientUtil.rawGet(url, JSONObject.class);
        if (jsonObject == null) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_RESPONSE_JSON);
        }
        return jsonObject;
    }

    @Override
    public List<SituationCommonComponentsPageRes> getComponentsPages(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {

        return this.getBaseMapper().selectPages(situationCommonComponentsPageReq);
    }
}
