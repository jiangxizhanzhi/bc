package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务模板服务impl 业务模板服务实现
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
@Slf4j
public class BusinessTemplateServiceImpl extends ServiceImpl<BusinessTemplateMapper, BusinessTemplate> implements BusinessTemplateService {

    @Autowired
    private BusinessTemplateMapper businessTemplateMapper;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionTemplateService missionTemplateService;

    @Autowired
    private RemoteTopoService remoteTopoService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    @Qualifier("businessTemplateHandler")
    private BusinessTemplateHandler businessTemplateHandler;

    /**
     * 检查权限
     *
     * @param businessId 业务标识
     * @param userId     用户id
     */
    @Override
    public void checkPermission(String businessId, String userId) {
        List<String> businessIds = selectAuthIdsByUserId(userId);

        if (!businessIds.contains(businessId)) {
            throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
        }
    }

    /**
     * 校验名字存在
     *
     * @param templateName 模板名称
     * @return {@link Boolean}
     */
    @Override
    public Boolean existByName(String templateName) {
        return businessTemplateMapper.existByName(templateName);
    }

    /**
     * 获取存在的名字
     *
     * @param names 名字
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> getExistedNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Lists.newArrayList();
        }
        return businessTemplateMapper.getExistedNames(names);
    }

    /**
     * 通过名字获取id
     *
     * @param templateName 模板名称
     * @return {@link String}
     */
    @Override
    public String getIdByName(String templateName) {
        return businessTemplateMapper.getIdByName(templateName);
    }

    /**
     * 更新模板
     *
     * @param businessTemplate 业务模板
     * @param baseInfo         基础信息
     */
    @Override
    public void updateTemplate(BusinessTemplate businessTemplate, BaseInfo baseInfo) {
        // validate length
        baseInfo.validate();
        // base info
        businessTemplate.setName(baseInfo.getName());
        businessTemplate.setDescription(baseInfo.getDescription());
        businessTemplate.setDifficulty(baseInfo.getDifficult());
        businessTemplate.setLogo(baseInfo.getLogo());
        // validate name
        validateName(businessTemplate.getName(), businessTemplate.getBusinessType(),
                businessTemplate.getResourceId());
        // update topo
        SceneConfigData topoData = baseInfo.getSceneConfigData();
        if (topoData != null) {
            JSONObject topoJson = new JSONObject();
            topoJson.put("scene_config", topoData.getSceneConfig());
            topoJson.put("logo", topoData.getLogo());
            localSceneService.saveTopo(topoJson, businessTemplate.getTopoId());
        }
        // save
        updateById(businessTemplate);
    }

    /**
     * 验证名字
     *
     * @param name       名字
     * @param type       类型
     * @param resourceId 资源id
     */
    @Override
    public void validateName(String name, Integer type, String resourceId) {
        CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        if (businessTemplateMapper.existByNameAndTypeAndId(name, type, resourceId)) {
            log.error("template name already exist！name=[{}]", name);
            throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE);
        }
    }

    /**
     * 更新任务模板关系
     *
     * @param businessTemplateId 业务模板id
     * @param before             之前
     * @param after              后
     */
    @Override
    public void updateMissionTemplateRelation(String businessTemplateId, String before,
                                              String after) {
        List<String> oldMissionTemplateIds = missionTemplateService
                .getIdsByMissionsSchemeJson(before);
        List<String> newMissionTemplateIds = missionTemplateService
                .getIdsByMissionsSchemeJson(after);
        // remove id in both list if both list contains the id
        int i = 0;
        while (i < oldMissionTemplateIds.size()) {
            if (newMissionTemplateIds.remove(oldMissionTemplateIds.get(i))) {
                oldMissionTemplateIds.remove(i);
            } else {
                i++;
            }
        }
        // delete
        missionTemplateService.batchDelete(oldMissionTemplateIds);
        // update
        missionTemplateService.batchUpdateBusinessTemplateId(businessTemplateId,
                newMissionTemplateIds);
    }

    /**
     * 获取攻击相关id
     *
     * @param businessTemplateIds 业务模板id
     * @return {@link List}<{@link String}>
     */
    public List<String> getAttackRelatedIds(List<String> businessTemplateIds) {
        if (businessTemplateIds.isEmpty()) {
            return businessTemplateIds;
        }
        return businessTemplateMapper.getAttackRelatedIds(businessTemplateIds);
    }

    /**
     * 获取Engage相关id
     *
     * @param businessTemplateIds 业务模板id
     * @return {@link List}<{@link String}>
     */
    public List<String> getEngageRelatedIds(List<String> businessTemplateIds) {
        if (businessTemplateIds.isEmpty()) {
            return businessTemplateIds;
        }
        return businessTemplateMapper.getEngageRelatedIds(businessTemplateIds);
    }

    /**
     * 分页页面
     *
     * @param btPageReq 英国电信页面请求
     * @return {@link ListRsp}<{@link BusinessTemplateSimple}>
     */
    @Override
    public ListRsp<BusinessTemplateSimple> pageSimple(BTPageReq btPageReq) {
        Page page = btPageReq.toPageParam();

        IPage<BusinessTemplateSimple> result = businessTemplateMapper.listSimple(page,
                btPageReq.getType(), btPageReq.getDifficulty(), btPageReq.getPattern());
        return PageUtil.getListResult(result);
    }

    /**
     * 通过用户id获取身份验证id
     *
     * @param userId 用户id
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> selectAuthIdsByUserId(String userId) {
        return businessTemplateMapper.selectIdsByUserId(userId);
    }

    /**
     * 页面
     *
     * @param businessTemplateQuery 业务模板查询
     * @return {@link IPage}<{@link BusinessTemplateData}>
     */
    @Override
    public IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery) {
        String userId = UserInfoContext.getUserContext().getUserId();
        Page page = businessTemplateQuery.toPageParam();
        businessTemplateQuery.setIds(businessTemplateMapper.selectIdsByUserId(userId));

        IPage<BusinessTemplateData> result = businessTemplateMapper.listTeamTemplate(page,
                businessTemplateQuery);

        // get node count
        List<String> topoIds = result.getRecords().stream().map(BusinessTemplate::getTopoId)
                .collect(Collectors.toList());
        Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
        for (BusinessTemplateData businessTemplate : result.getRecords()) {
            businessTemplate.setNodesNum(nodeCountMap
                    .getOrDefault(businessTemplate.getTopoId(), new TopoDigest()).getNodeCounts());
        }

        return result;

    }

    /**
     * 清除本地
     *
     * @param template 模板
     */
    @Override
    public void clearLocal(BusinessTemplate template) {
        String localSceneId = template.getLocalSceneId();
        template.setLocalSceneId("");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                localSceneService.removeById(localSceneId);
                updateById(template);
            }
        });
    }

    /**
     * 查看场景
     *
     * @param template 模板
     * @return {@link SceneViewContext}
     */
    @Override
    public SceneViewContext viewScene(BusinessTemplate template) {
        SceneViewContext ret = new SceneViewContext();
        ret.setRoomId(template.getResourceId());
        ret.setTopoId(template.getTopoId());
        // 1、尝试获取启动的远程场景
        JSONObject sceneData = getMpSceneData(template);
        if (sceneData != null) {
            ret.setSceneData(sceneData);
            ret.setLocalSceneId(template.getLocalSceneId());
            ret.setMpSceneId(sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY));
            return ret;
        }
        // 2、 远程场景不存在，返回topo数据
        JSONObject topoData = localSceneService.getTopoData(template.getTopoId())
                .getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
        ret.setTopoData(topoData);
        return ret;
    }

    /**
     * 类型转换成代码
     *
     * @param code 代码
     * @return {@link LocalScene.BusinessCode}
     */
    @Override
    public LocalScene.BusinessCode convertTypeToCode(Integer code) {
        BusinessTemplate.BusinessType type = EnumUtils.getEnumByCode(code,
                BusinessTemplate.BusinessType.class);

        if (type.equals(BusinessTemplate.BusinessType.TRAINING_SINGLE)) {
            return LocalScene.BusinessCode.SINGLE_TRAINING;
        }
        if (type.equals(BusinessTemplate.BusinessType.VULN_VALIDATE)) {
            return LocalScene.BusinessCode.LIVE_VULN;
        }
        if (type.equals(BusinessTemplate.BusinessType.TRAINING_TEAM)) {
            return LocalScene.BusinessCode.TEAM_TRAINING;
        }
        if (type.equals(BusinessTemplate.BusinessType.RED_VS_BLUE)) {
            return LocalScene.BusinessCode.RED_VS_BLUE;
        }
        if (type.equals(BusinessTemplate.BusinessType.WEAPON_ASSESS)) {
            return LocalScene.BusinessCode.WEAPON_TEST;
        }
        if (type.equals(BusinessTemplate.BusinessType.SYSTEM_ASSESS)) {
            return LocalScene.BusinessCode.SYSTEM_TEST;
        }
        throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
    }

    /**
     * 转储文件模板
     *
     * @param templateId 模板id
     * @return {@link ResourceDumpContext}
     */
    @Override
    public ResourceDumpContext dumpTemplate(String templateId) {
        // 1. 获取要导出的模板
        BusinessTemplate template = businessTemplateMapper.selectById(templateId);
        if (template == null) {
            throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
        }
        // 2. 生成导出模型, 包含 template, missions
        ResourceDumpContext<Object> context = businessTemplateHandler.dumpBusiness(template);
        context.setBusinessTemplate(template);

        // 3. 获取拓扑
        JSONObject topoData = localSceneService.getTopoData(template.getTopoId());
        context.setSceneData(topoData);
        // 4. 获取文件
        Map<String, String> fileMap = Maps.newHashMap();
        context.setFiles(fileMap);

        for (MissionTemplate missionTemplate : context.getMissions()) {
            List<String> attachmentIds = JSON.parseArray(missionTemplate.getAttachment(),
                    String.class);

            List<Attachment> attachments = attachmentService.batchGetByIds(attachmentIds);
            for (Attachment attachment : attachments) {
                fileMap.put(attachment.getResourceId(), attachment.getFileName());
            }
        }
        // 5. 设置业务类型
        context.setType(template.getBusinessType());

        return context;
    }

    /**
     * 批量加载
     *
     * @param unZipPath 联合国邮政路径
     * @return {@link Boolean}
     */
    @Override
    public Boolean batchLoad(String unZipPath) {
        // 1. read data.json
        String dataJson = FileUtil.getContent(
                new File(unZipPath + File.separator + CrCommonConstants.RESOURCE_FILE_NAME));
        List<ResourceDumpContext> resources = JsonUtil.toJsonObjectArray(dataJson,
                ResourceDumpContext.class);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (ResourceDumpContext resource : resources) {
                    // 2.load business
                    businessTemplateHandler.loadBusinessResource(resource);
                    // 3. load missions
                    List<MissionTemplate> missionTemplates = resource.getMissions();
                    missionService.batchLoadMissionTemplates(missionTemplates);
                    // 3. load businessTemplate
                    BusinessTemplate template = resource.getBusinessTemplate();
                    loadBusinessTemplate(template);

                    // 4. save topo
                    JSONObject topoData = resource.getSceneData();
                    remoteTopoService.save(topoData);

                    // 5. save images
                    Map<String, String> files = resource.getFiles();
                    loadImages(files,
                            unZipPath + File.separator + CrCommonConstants.RESOURCE_IMAGE_DIR);
                }
            }
        });
        return true;
    }

    /**
     * 加载图片
     *
     * @param files    文件
     * @param imageDir dir形象
     * @return {@link Boolean}
     */
    private Boolean loadImages(Map<String, String> files, String imageDir) {
        if (files == null || files.size() == 0) {
            return false;
        }
        for (Map.Entry<String, String> entry : files.entrySet()) {
            attachmentService.loadImage(entry.getKey(), entry.getValue(), imageDir);
        }

        return true;
    }

    /**
     * 加载业务模板
     *
     * @param template 模板
     */
    private void loadBusinessTemplate(BusinessTemplate template) {
        template.setId(null);
        template.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        template.setCreateTime(null);
        template.setUpdateTime(null);
        template.setLocalSceneId(null);
        template.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
        template.setOperationAuthIds("[]");
        if (getById(template.getResourceId()) != null) {
            updateById(template);
        } else {
            save(template);
        }
    }

    /**
     * 获取场景数据
     *
     * @param template 模板
     * @return {@link JSONObject}
     */
    private JSONObject getMpSceneData(BusinessTemplate template) {
        String localSceneId = template.getLocalSceneId();
        // localSceneId字段为空
        if (StringUtils.isBlank(localSceneId)) {
            return null;
        }
        // localScene不存在，localSceneId字段是脏数据
        LocalScene localScene = localSceneService.getById(localSceneId);
        if (null == localScene) {
            template.setLocalSceneId("");
            updateById(template);
            return null;
        }
        // localScene 的 mpSceneId为空，localScene是脏数据
        String mpSceneId = localScene.getMpSceneId();
        if (StringUtils.isBlank(mpSceneId)) {
            clearLocal(template);
            return null;
        }
        // 请求远程场景数据
        JSONObject sceneData = localSceneService.getSceneData(mpSceneId,
                UserInfoContext.getUserContext().getUserId());
        // 场景数据为空，清除本地脏数据
        if (null == sceneData) {
            clearLocal(template);
            return null;
        }
        // 场景实际已被删除，清除本地脏数据
        SceneData data = sceneData.toJavaObject(SceneData.class);
        if (data.isStopped()) {
            clearLocal(template);
            return null;
        }
        sceneData.put(CrCommonConstants.MP_SCENE_ID_KEY, mpSceneId);
        return sceneData;
    }
}
