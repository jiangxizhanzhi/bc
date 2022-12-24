package com.cyberpeace.situation.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.SchemeVisualizationBo;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.ThirdPartyService;

import lombok.extern.slf4j.Slf4j;


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

    @Override
    public SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {

        // 拷贝属性
        SituationPublishInstance situationPublishInstance = BeanUtils.copy(situationPublishInstanceAddReq,
                SituationPublishInstance.class);

        // 校验参数
        checkParams(situationPublishInstanceAddReq);

        String userId = UserInfoContext.getUserContext().getUserId();
        situationPublishInstance.setCreateUserId(userId);
        situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());
        situationPublishInstance.setPublicStatus(PublicStatus.OFF.getCode());
        situationPublishInstance.setEdit(true);

        // 构造大屏属性表
        DashboardAttribute dashboardAttribute = BeanUtils.copy(situationPublishInstanceAddReq, DashboardAttribute.class);
        dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
        dashboardAttribute.setDashboardType(DashboardType.ZB.getCode());
        dashboardAttribute.setDashboardMode(DashboardMode.SPG.getCode());
        dashboardAttribute.setTitle("");
        dashboardAttribute.setSubTitle("");

        // 从大屏模板中拷贝属性
        String templateId = situationPublishInstanceAddReq.getTemplateId();
        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, null);
        Integer level = dashboardTemplateInfo.getLevel();
        Integer refresh = dashboardTemplateInfo.getRefresh();
        dashboardAttribute.setLevel(level == null ? DashboardLevel.RW.getCode() : level);
        dashboardAttribute.setRefresh(refresh == null ? 5 : refresh);

        // 从可视化方案中拷贝属性
        SchemeVisualizationBo scheme = remoteSituationService.getScheme(situationPublishInstanceAddReq.getSchemeId());
        situationPublishInstance.setSubRangeId(scheme.getSubRangeId());
        situationPublishInstance.setSubRangeName(scheme.getSubRangeName());

        dashboardAttribute.setEnvId(scheme.getEnvId());
        dashboardAttribute.setStartTime(TimeUtil.toDate1(scheme.getStartTime()));
        dashboardAttribute.setSchemeDesc(scheme.getSchemeDesc());
        dashboardAttribute.setTaskType(scheme.getTaskType());
        dashboardAttribute.setViewAngle(JsonUtil.toJsonString(scheme.getGisView()));

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
                } catch (DuplicateKeyException e) {
                    throw new BusinessException(ErrorCode.NAME_DUPLICATE);
                }
            }
        });
        return null;
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
        if (!Objects.equals(situationPublishInstance.getSchemeId(), situationPublishInstanceUpdateReq.getSchemeId())){
            throw new BusinessException(SituationErrorCode.CAN_NOT_MODIFY_TASK);
        }

        // 只允许修改名称、描述和缩略图
        situationPublishInstance.setName(situationPublishInstanceUpdateReq.getName());
        situationPublishInstance.setDescription(situationPublishInstanceUpdateReq.getDescription());
        situationPublishInstance.setThumbnail(situationPublishInstanceUpdateReq.getThumbnail());

        situationPublishInstanceMapper.updateById(situationPublishInstance);
        return null;
    }

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

        //填充相关名称
        if (CollectionUtils.isNotEmpty(situationPublishInstancePageResList)) {
            for (SituationPublishInstancePageRes situationPublishInstancePageRes : situationPublishInstancePageResList) {
                String category = situationPublishInstancePageRes.getCategory();

                // 赋值大屏类型
                categories.forEach(situationCategory -> {
                    if (situationCategory.getResourceId().equals(category)){
                        situationPublishInstancePageRes.setCategory(situationCategory.getName());
                    }
                });

                // 赋值大屏状态
                statusList.forEach(localType -> {
                    if (Objects.equals(localType.getCode(), situationPublishInstancePageRes.getStatus())) {
                        situationPublishInstancePageRes.setStatus(localType.getMsg());
                    }
                });
            }
        }
        situationPublishInstancePage.setRecords(situationPublishInstancePageResList);
        return PageUtil.getListResult(situationPublishInstancePage);
    }

}
