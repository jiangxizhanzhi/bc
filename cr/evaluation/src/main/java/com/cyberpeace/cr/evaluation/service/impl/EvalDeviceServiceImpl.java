package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalDeviceService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class EvalDeviceServiceImpl implements EvalDeviceService {
    @Autowired
    private EvalTemplateMapper evalTemplateMapper;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private LocalSceneMapper localSceneMapper;

    /**
     * 通过模板id查询设备清单
     * @param resourceId
     * @param category
     * @param name
     * @return
     */
    @Override
    public JSONArray getDeviceList(String resourceId, Integer category, String name) {
        //根据模板id去查询
        EvalTemplate evalTemplate = evalTemplateMapper.selectByResourceId(resourceId);
        if (Objects.isNull(evalTemplate)){
            log.error("Param exception:{}",resourceId);
            throw new BusinessException("getDeviceList Param exception:{}",resourceId);
        }
        LocalScene localScene = localSceneMapper.selectLocalSceneByTopoId(evalTemplate.getTopoId());
        if (Objects.nonNull(localScene)) {
            return localSceneService.getAssetListByInstanceId(localScene.getBusinessInstanceId(), category, name, true);
        } else {
            return localSceneService.getAssetListByTopoId(evalTemplate.getTopoId(), category, name);
        }
    }

    @Override
    public JSONObject getDeviceStatistics(String resourceId) {
        //根据模板id查询
        EvalTemplate evalTemplate = evalTemplateMapper.selectByResourceId(resourceId);
        if (Objects.isNull(evalTemplate)){
            log.error("Param exception:{}",resourceId);
            throw new BusinessException("getDeviceList Param exception:{}",resourceId);
        }
        //根据模板id去查询场景ID
        String localSceneId = evalTemplateMapper.selectBysceneId(resourceId);
        if (StringUtils.isNotEmpty(localSceneId)) {
            return localSceneService.getNodeStatistics(localSceneId);
        } else {
            return localSceneService.getNodeStatisticsByTopoId(evalTemplate.getTopoId());
        }
    }
}
