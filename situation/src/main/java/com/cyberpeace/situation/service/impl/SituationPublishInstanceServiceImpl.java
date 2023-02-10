package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.SubjectContext;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.cyberpeace.integratation.model.situation.req.SubjectListReq;
import com.cyberpeace.integratation.model.situation.req.TopoReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.SchemeVisualizationBo;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.ThirdPartyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class SituationPublishInstanceServiceImpl extends ServiceImpl<SituationPublishInstanceMapper, SituationPublishInstance> implements SituationPublishInstanceService {


    @Autowired
    private SituationPublishInstanceMapper situationPublishInstanceMapper;

    @Autowired
    private DashboardTemplateService dashboardTemplateService;

    @Autowired
    private SituationCategoryMapper situationCategoryMapper;

    @Autowired
    private DashboardTemplateMapper dashboardTemplateMapper;

    @Autowired
    private DashboardTemplateRefMapper dashboardTemplateRefMapper;

    @Autowired
    private DashboardTemplateRefService dashboardTemplateRefService;

    @Autowired
    private RemoteSituationService remoteSituationService;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DashboardAttributeMapper dashboardAttributeMapper;

    @Autowired
    private TopoConfigMapper topoConfigMapper;

    @Override
    public SituationPublishInstance selectReleasedBySubjectId(String taskId, String subjectId) {

        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectPublicedBySubjectId(taskId, subjectId);
        if (null == situationPublishInstance) {
            throw new BusinessException(SituationErrorCode.NO_RELEASED_DASHBOARD);
        }

        return situationPublishInstance;
    }

    @Override
    public SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq, Boolean isXd) {

        // 拷贝属性
        SituationPublishInstance situationPublishInstance = BeanUtils.copy(situationPublishInstanceAddReq,
                SituationPublishInstance.class);

        // 校验参数
//        checkParams(situationPublishInstanceAddReq);

        if (StringUtils.isBlank(situationPublishInstance.getDescription())) {
            situationPublishInstance.setDescription("默认描述");
        }

        String userId = "";
        situationPublishInstance.setCreateUserId(userId);
        situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());
        situationPublishInstance.setPublicStatus(PublicStatus.OFF.getCode());

        // 构造大屏属性表
        DashboardAttribute dashboardAttribute = BeanUtils.copy(situationPublishInstanceAddReq, DashboardAttribute.class);
        dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
        dashboardAttribute.setDashboardMode(DashboardMode.SPG.getCode());

        // 从可视化方案中拷贝属性
        SchemeVisualizationBo scheme = remoteSituationService.getScheme(situationPublishInstanceAddReq.getSchemeId());

        situationPublishInstance.setSubRangeId(scheme.getSubRangeId());
        situationPublishInstance.setSubRangeName(scheme.getSubRangeName());
        situationPublishInstance.setTaskName(scheme.getTaskName());
        situationPublishInstance.setSubjectName(scheme.getSubjectName());

        dashboardAttribute.setEnvId(scheme.getEnvId());
        dashboardAttribute.setStartTime(TimeUtil.toDate1(scheme.getStartTime()));
        dashboardAttribute.setSchemeName(scheme.getSchemeName());
        dashboardAttribute.setSchemeDesc(scheme.getSchemeDesc());
        dashboardAttribute.setTaskType(scheme.getTaskType());
        dashboardAttribute.setViewAngle(JsonUtil.toJsonString(scheme.getGisView()));
        if (TaskType.SYZZ.getCode().equals(scheme.getTaskType())) {
            dashboardAttribute.setTaskSubType("1");
        }
        String rangeName = StringUtils.isBlank(scheme.getSubRangeName()) ? "LG中心" : scheme.getSubRangeName();
        String taskName = scheme.getTaskName();
        dashboardAttribute.setTitle(rangeName + "任务态势");
        dashboardAttribute.setSubTitle(taskName + "任务");

        // 如果大屏名称不存在，默认生成
        if (StringUtils.isBlank(situationPublishInstance.getName())) {
            situationPublishInstance.setName("想定大屏-" + taskName);
        }

        // 从大屏模板中拷贝属性
        String templateId = situationPublishInstanceAddReq.getTemplateId();
        // 模板不存在时获取默认模板
        if (templateId == null) {
            String defaultTemplateId = dashboardTemplateService.getDefaultTemplateId(scheme.getTaskType());
            templateId = dashboardTemplateRefService.createDashboardTemplate(defaultTemplateId).getResourceId();
        }

        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, null, Boolean.FALSE);
        Integer level = dashboardTemplateInfo.getLevel();
        Integer refresh = dashboardTemplateInfo.getRefresh();
        Boolean editStatus = dashboardTemplateInfo.getEditStatus();
        dashboardAttribute.setLevel(level == null ? DashboardLevel.RW.getCode() : level);
        dashboardAttribute.setRefresh(refresh == null ? 5 : refresh);
        dashboardAttribute.setDashboardType(editStatus ? DashboardType.ZB.getCode() : DashboardType.DZ.getCode());
        situationPublishInstance.setEdit(!isXd);
        situationPublishInstance.setThumbnail(dashboardTemplateInfo.getThumbnail());
        situationPublishInstance.setTemplateId(dashboardTemplateInfo.getResourceId());

        // 处理实例类型
        String taskType = scheme.getTaskType();
        SituationCategory situationCategory = situationCategoryMapper.selectByTaskType(taskType);
        String category;

        if (situationCategory != null) {
            category = situationCategory.getResourceId();
        } else {
            TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
                    .filter(t -> t.getCode().equals(taskType)).findFirst().orElse(TaskType.QB);
            SituationCategory newCategory = new SituationCategory(type.getName(), type.getCode(), userId);
            category = IDGeneratorUtil.getID();
            newCategory.setResourceId(category);
            situationCategoryMapper.insert(newCategory);
        }
        situationPublishInstance.setCategory(category);

        // 建立实例与属性关联关系
        situationPublishInstance.setAttributeId(dashboardAttribute.getResourceId());

        // 写入数据库
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    situationPublishInstanceMapper.insert(situationPublishInstance);
                    dashboardAttributeMapper.insert(dashboardAttribute);
                    positionAdjust(situationPublishInstance.getSubRangeId(), situationPublishInstance.getTaskId(),
                            situationPublishInstance.getSubjectId(), dashboardAttribute.getEnvId());
                } catch (DuplicateKeyException e) {
                    throw new BusinessException(ErrorCode.NAME_DUPLICATE);
                }
            }
        });
        return situationPublishInstance;
    }

    private void positionAdjust(String subRangeId, String taskId, String subjectId, String envId) {

        // 不重复处理拓扑坐标
        TopoConfig topoConfig = topoConfigMapper.selectByEnvId(envId);
        if (topoConfig != null) {
            return;
        }

        TopoReq req = new TopoReq(subRangeId, taskId, subjectId, envId);
        TopoContext topoContext = remoteSituationService.getTopo(req, true);
        topoConfig = new TopoConfig(subRangeId, taskId, subjectId, envId, topoContext);
        topoConfigMapper.insert(topoConfig);
    }

    private void checkParams(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
        // 校验模板
        checkTemplate(situationPublishInstanceAddReq);

    }

    private void checkTemplate(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {

        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectById(situationPublishInstanceAddReq.getTemplateId());
        if (null == dashboardTemplateRef) {
            throw new BusinessException(SituationErrorCode.TEMPLATE_INVALID_ERROR);
        }
    }

    @Override
    public SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(situationPublishInstanceUpdateReq.getResourceId());
        if (null == situationPublishInstance) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 校验变更项，暂不允许修改任务信息
        if (!Objects.equals(situationPublishInstance.getSchemeId(), situationPublishInstanceUpdateReq.getSchemeId())) {
            throw new BusinessException(SituationErrorCode.CAN_NOT_MODIFY_TASK);
        }

        // 只允许修改名称、描述和缩略图
        situationPublishInstance.setName(situationPublishInstanceUpdateReq.getName());
        situationPublishInstance.setDescription(situationPublishInstanceUpdateReq.getDescription());
        situationPublishInstance.setThumbnail(situationPublishInstanceUpdateReq.getThumbnail());

        situationPublishInstanceMapper.updateById(situationPublishInstance);
        return null;
    }

    @Override
	public void deleteUnusedSituation() {
        List<String> list = dashboardTemplateRefMapper.selectAllId();
        dashboardTemplateRefService.deleteDashboardTemplate(list);
    }

    @Override
    public ListRsp<SituationPublishInstancePageRes> getSituationPublishInstancePage(SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {

        Page page = situationPublishInstanceFilterReq.toPageParam();

        //获取分页数据
        IPage<SituationPublishInstancePageRes> situationPublishInstancePage = situationPublishInstanceMapper.getSituationPublishInstancePage(page, situationPublishInstanceFilterReq);
        List<SituationPublishInstancePageRes> situationPublishInstancePageResList = situationPublishInstancePage.getRecords();

        //任务类型id列表
        List<SituationCategory> categories = situationCategoryMapper.selectList(new LambdaQueryWrapper<>());
        List<LocalType> statusList = SituationInstanceStatus.getMsgList();

        //模板列表
        List<DashboardTemplateRef> dashboardTemplateRefs = dashboardTemplateRefMapper.selectList(new LambdaQueryWrapper<>());

        //任务科目列表
        List<SubjectContext> subjectList = remoteSituationService.getSubjectList(new SubjectListReq());

        //填充相关名称
        if (CollectionUtils.isNotEmpty(situationPublishInstancePageResList)) {
            for (SituationPublishInstancePageRes situationPublishInstancePageRes : situationPublishInstancePageResList) {
                String category = situationPublishInstancePageRes.getCategory();
                String templateId = situationPublishInstancePageRes.getTemplateId();
                String taskId = situationPublishInstancePageRes.getTaskId();
                String subjectId = situationPublishInstancePageRes.getSubjectId();

                // 赋值大屏类型
                categories.forEach(situationCategory -> {
                    if (situationCategory.getResourceId().equals(category)) {
                        situationPublishInstancePageRes.setCategory(situationCategory.getName());
                    }
                });

                // 赋值大屏状态
                statusList.forEach(localType -> {
                    if (Objects.equals(localType.getCode(), situationPublishInstancePageRes.getStatus())) {
                        situationPublishInstancePageRes.setStatus(localType.getMsg());
                    }
                });

                //模板编辑状态
                dashboardTemplateRefs.forEach(dashboardTemplateRef -> {
                    if (StringUtils.isNotBlank(templateId) && Objects.equals(templateId, dashboardTemplateRef.getResourceId())) {
                        situationPublishInstancePageRes.setTemplateEdit(dashboardTemplateRef.getEditStatus());
                    }
                });

                //设置任务科目信息
                if (StringUtils.isNotBlank(taskId)) {
                    subjectList.forEach(subjectContext -> {
                        //设置任务信息
                        if (subjectContext.getTaskId().equals(taskId)) {
                            situationPublishInstancePageRes.setTaskName(subjectContext.getTaskName());
                            situationPublishInstancePageRes.setTaskStatus(subjectContext.getTaskStatus());

                            //设置科目信息
                            if (StringUtils.isNotBlank(subjectId) && subjectContext.getSubjectId().equals(subjectId)) {
                                situationPublishInstancePageRes.setSubjectName(subjectContext.getSubjectName());
                                situationPublishInstancePageRes.setSubjectStatus(subjectContext.getSubjectStatus());
                                situationPublishInstancePageRes.setStartTime(subjectContext.getStartTime());
                                situationPublishInstancePageRes.setEndTime(subjectContext.getEndTime());
                            }
                        }
                    });
                }
            }
        }
        situationPublishInstancePage.setRecords(situationPublishInstancePageResList);
        return PageUtil.getListResult(situationPublishInstancePage);
    }

    @Override
    public String getEnvIdByDashboardId(String dashboardId) {
        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(dashboardId);
        if (null == situationPublishInstance) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getByResourceId(situationPublishInstance.getAttributeId());

        return dashboardAttribute.getEnvId();
    }

}
