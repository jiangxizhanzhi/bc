package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.EventDataBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import com.cyberpeace.cs.service.LocalSceneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Slf4j
public class SingleTemplateServiceImpl extends ServiceImpl<SingleTemplateMapper, SingleTemplate> implements SingleTemplateService {

    @Autowired
    private SingleTemplateMapper singleTemplateMapper;

    @Autowired
    private BusinessTemplateService businessTemplateService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private MissionTemplateMapper missionTemplateMapper;

    /**
     * 单兵训练模板获取
     */
    @Override
    public SingleTemplateBo get(String businessTemplateId) {
        SingleTemplate trainObject = singleTemplateMapper.getByBusinessId(businessTemplateId);
        BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);

        EventDataBo eventData = new EventDataBo();
        BeanUtils.copyProperties(trainObject, eventData);
        BeanUtils.copyProperties(businessTemplate, eventData);

        // 属性 todo create_user, scene_config_data
        eventData.setDifficult(businessTemplate.getDifficulty());
        eventData.setCategory(trainObject.getSubType());
        eventData.setPreSkill(JsonUtil.toJsonObjectArray(trainObject.getPreSkills(), String.class));
        eventData.setTrainSkill(JsonUtil.toJsonObjectArray(trainObject.getTrainSkills(), String.class));
        eventData.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));

        SingleTemplateBo singleTemplateBo = new SingleTemplateBo();

        singleTemplateBo.setResourceId(businessTemplate.getResourceId());
        singleTemplateBo.setEventData(eventData);
        singleTemplateBo.setMissionsScheme(JsonUtil.toJsonObject(trainObject.getMissionsScheme(), JSONObject.class));

        return singleTemplateBo;
    }

    /**
     * 单兵训练模板更新
     * @param businessTemplateId cr_business_template.resource_id
     * @param baseInfo 训练基础数据
     * @param missionsScheme 训练任务方案
     * @return 更新后的单兵训练数据对象
     */
    @Override
    public SingleTemplateBo update(String businessTemplateId, BaseInfo baseInfo, JSONObject missionsScheme) {
        // business template
        BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);
        BeanUtils.copyProperties(baseInfo, businessTemplate);

        // single
        SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
        BeanUtils.copyProperties(baseInfo, singleTemplate);
        singleTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
        singleTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
        singleTemplate.setMissionsScheme(JsonUtil.toJsonString(missionsScheme));
        singleTemplate.setSubType(baseInfo.getCategory());

        // topology
        if (baseInfo.getSceneConfigData() != null) {
            JSONObject sceneConfigData = new JSONObject();
            sceneConfigData.put("scene_config", baseInfo.getSceneConfigData().getSceneConfig());
            localSceneService.saveTopo(sceneConfigData, businessTemplate.getTopoId());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                businessTemplateService.updateById(businessTemplate);
                singleTemplateMapper.updateById(singleTemplate);
            }
        });


        return get(businessTemplateId);
    }

    @Override
    public SingleTemplate getByBusinessTemplateId(String resourceId) {
        return singleTemplateMapper.getByBusinessId(resourceId);
    }

    @Override
    public void missionsSchemeValidator(JSONObject missionsScheme) {
        List<String> trainMissions = Lists.newArrayList();
        missionsScheme.getJSONArray("scheme_data").toJavaList(JSONObject.class).forEach(stage -> {
            stage.getJSONArray("missions").toJavaList(JSONObject.class).forEach(mission -> {
                String missionId = mission.getString("resource_id");
                if (missionId == null || missionId.isEmpty()) {
                    throw new BusinessException(CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE);
                }
                trainMissions.add(missionId);
            });
        });
        if (trainMissions.size() != missionTemplateMapper.selectBatchIds(trainMissions).size()) {
            throw new BusinessException(CrBusinessErrorCode.MISSION_TEMPLATE_INVALID);
        }
    }
}
