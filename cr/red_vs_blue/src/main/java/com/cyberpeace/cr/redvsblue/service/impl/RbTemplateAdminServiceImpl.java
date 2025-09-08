package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateAdminService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.cyberpeace.common.enums.BusinessType.UNITE_TRAIN;

@Service
@Slf4j
public class RbTemplateAdminServiceImpl implements RbTemplateAdminService {


    /**
     * 业务模板Service
     */
    @Resource
    private BusinessTemplateService businessTemplateService;


    /**
     * 任务模板Service
     */
    @Resource
    private MissionTemplateService missionTemplateService;


    /**
     * 当地场景Service
     */
    @Resource
    private LocalSceneService localSceneService;

    /**
     * 红蓝对抗模板
     */
    @Resource
    private RbTemplateMapper rbTemplateMapper;



    @Override
    public RbTemplateRsp getRbTemplate(String resourceId) {
        BusinessTemplate businessTemplate = businessTemplateService
                .getById(resourceId);
        extracted(resourceId, businessTemplate);

        RbTemplate template = rbTemplateMapper.selectByBusinessTemplateId(resourceId);
        if (Objects.equals(businessTemplate.getBusinessType(), UNITE_TRAIN.getCode())) {
            if(template == null){
                BaseInfoRsp baseInfoRsp = new BaseInfoRsp(businessTemplate);
                // 获取返回对象
                RbTemplateRsp teamTrainTemplateRsp = new RbTemplateRsp();
                teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
                return teamTrainTemplateRsp;
            }
        } else {
            extracted(resourceId, template);
        }

        List<JSONObject> redRoleRsps = null;
        List<JSONObject> blueRoleRsps  = null;

        // 获取红蓝角色
        String roleString = template.getRoleConfig();
        JSONObject roleObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

        Map<String, MissionTemplate> mapping = missionTemplateService
                .getMappingBySchemeJson(roleString);
        if(Objects.nonNull(roleObject.getJSONArray("red_roles"))){
            redRoleRsps = getRoles(roleObject.getJSONArray("red_roles"), mapping);
        }
        if(Objects.nonNull(roleObject.getJSONArray("blue_roles"))){
            blueRoleRsps = getRoles(roleObject.getJSONArray("blue_roles"), mapping);
        }

        dealAssetNodesMap(redRoleRsps);
        dealAssetNodesMap(blueRoleRsps);
        // 获取基本信息
        BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

        baseInfoRsp.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
        String sceneManagerId = "";
        LocalScene localScene = localSceneService
                .getByBusinessInstanceId(businessTemplate.getResourceId());
        if (localScene != null) {
            sceneManagerId = localScene.getResourceId();
        }
        baseInfoRsp.setSceneManagerId(sceneManagerId);

        // 如果场景信息不为null，提取logo参数（默认为空字符串）
        if (baseInfoRsp.getSceneConfigData() != null) {
            baseInfoRsp.setTopo(baseInfoRsp.getSceneConfigData().getString("logo"));
        }

        // 获取返回对象
        RbTemplateRsp teamTrainTemplateRsp = new RbTemplateRsp();
        if(Objects.nonNull(roleObject.getJSONArray("white_role"))){
            List<String> whiteRole = JSONObject.parseArray(roleObject.getJSONArray("white_role").toString(), String.class);
            teamTrainTemplateRsp.setWhiteRole(whiteRole);
        }
        teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
        teamTrainTemplateRsp.setResourceId(template.getResourceId());
        teamTrainTemplateRsp.setRedRoles(redRoleRsps);
        teamTrainTemplateRsp.setBlueRoles(blueRoleRsps);
        return teamTrainTemplateRsp;
    }


    private static void extracted(String resourceId, Object object) {
        if (object == null) {
            log.error("business template not found, businessId:{}", resourceId);
            throw new ResourceNotFoundException(resourceId);
        }
    }


    /**
     * 交易资产节点映射
     *
     * @param roleRsps 岗位RSP
     */
    public void dealAssetNodesMap(List<JSONObject> roleRsps ) {
        if(CollectionUtils.isEmpty(roleRsps)){
            return;
        }
        for (JSONObject roleRsp : roleRsps) {
            List<Object> assetNodes = roleRsp.getObject("asset_nodes", ArrayList.class);
            Map<String,Integer>  assetMap = new HashMap<>();
            if (CollectionUtils.isEmpty(assetNodes)) {
                roleRsp.put("assetMap",assetMap);
                continue;
            }
            List<CrRoleNode> crRoleNodes = BeanUtils.deepCopyList(assetNodes, CrRoleNode.class);
            Map<Integer, List<CrRoleNode>> nodeMap = crRoleNodes.stream().collect(Collectors.groupingBy(CrRoleNode::getCategory));
            for (CrRoleNode.CategoryGroup value : CrRoleNode.CategoryGroup.values()) {
                assetMap.put(value.getEnglish(),nodeMap.getOrDefault(value.getCode(),new ArrayList<>()).size());
            }
            roleRsp.put("assetMap",assetMap);
        }
    }


    /**
     * 获取角色
     *
     * @param roleArray 岗位数组
     * @param mapping   映射
     * @return {@code List<JSONObject> }
     */
    private List<JSONObject> getRoles(JSONArray roleArray, Map<String, MissionTemplate> mapping) {
        List<JSONObject> result = Lists.newArrayList();
        // 逐角色逐阶段读取任务信息
        roleArray.forEach(role -> {
            CrRoleConfig roleRsp = ((JSONObject) role).toJavaObject(CrRoleConfig.class);
            roleRsp.setId(IDGeneratorUtil.getID());
            if(Objects.nonNull(roleRsp.getMissionsScheme()) && CollectionUtils.isNotEmpty(roleRsp.getMissionsScheme().getSchemeData())){
                roleRsp.getMissionsScheme().getSchemeData()
                        .forEach(datum -> datum.getMissions().forEach(mission -> {
                            MissionTemplate template = mapping.get(mission.getResourceId());
                            mission.setTitle(template.getTitle());
                            mission.setDescription(template.getDescription());
                        }));
            }
            // 转为蛇形
            result.add(JsonUtil.java2JsonObject(roleRsp, PropertyNamingStrategy.SnakeCase));
        });
        return result;
    }
}
