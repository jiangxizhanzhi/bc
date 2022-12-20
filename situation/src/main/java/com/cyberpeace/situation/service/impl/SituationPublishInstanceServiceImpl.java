package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;
import com.cyberpeace.integratation.model.situation.SubjectContext;
import com.cyberpeace.integratation.model.situation.TaskContext;
import com.cyberpeace.integratation.model.situation.req.TaskListReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import com.cyberpeace.situation.constant.TaskType;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceListRes;
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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        SituationPublishInstance situationPublishInstance = BeanUtils.copy(situationPublishInstanceAddReq,
                SituationPublishInstance.class);
        checkParams(situationPublishInstanceAddReq);

        String userId = UserInfoContext.getUserContext().getUserId();
        situationPublishInstance.setCreateUserId(userId);
        situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());

        //插入situation_dashboard_attribute
        DashboardAttribute dashboardAttribute = BeanUtils.copy(situationPublishInstance, DashboardAttribute.class);
        String templateId = situationPublishInstanceAddReq.getTemplateId();

        String name = situationPublishInstanceAddReq.getName();
        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, null);
        Integer level = dashboardTemplateInfo.getLevel();
        Integer refresh = dashboardTemplateInfo.getRefresh();

        dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
        situationPublishInstance.setAttributeId(dashboardAttribute.getResourceId());
        dashboardAttribute.setDashboardId(dashboardAttribute.getResourceId());
        dashboardAttribute.setDashboardType(2);
        dashboardAttribute.setTitle(name);
        dashboardAttribute.setLevel(level == null ? 3 : level);
        dashboardAttribute.setRefresh(refresh == null ? 5 : refresh);
        dashboardAttribute.setDashboardMode(1);
        dashboardAttribute.setTaskName(situationPublishInstanceAddReq.getTaskName());
        dashboardAttribute.setSubjectName(situationPublishInstanceAddReq.getSubjectName());

        //TODO 分靶场类型设置

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
        checkTemplate(situationPublishInstanceAddReq);

        checkPlan(situationPublishInstanceAddReq);

    }

    public void checkPlan(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
        // TODO 校验输入 任务方案 科目等id字段 根据任务id获取对应类型并设置大屏任务类型
//        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(situationPublishInstanceAddReq.getTemplateId());
//        if(null == dashboardTemplate){
//            throw new BusinessException(SituationErrorCode.TEMPLATE_INVALID_ERROR);
//        }
    }

    private void checkTemplate(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {

        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectById(situationPublishInstanceAddReq.getTemplateId());
        if (null == dashboardTemplateRef) {
            throw new BusinessException(SituationErrorCode.TEMPLATE_INVALID_ERROR);
        }
    }

    @Override
    public SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {
        SituationPublishInstance patchObj = BeanUtils.copy(situationPublishInstanceUpdateReq, SituationPublishInstance.class);
        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(situationPublishInstanceUpdateReq.getResourceId());
        if (null == situationPublishInstance) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

//        LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SituationPublishInstance::getName, situationPublishInstanceUpdateReq.getName());
//        SituationPublishInstance one = situationPublishInstanceService.getOne(queryWrapper);
//        if (null != one && !Objects.equals(one.getResourceId(), situationPublishInstanceUpdateReq.getResourceId())) {
//            throw new BusinessException(SituationErrorCode.RELEASE_SITUATION_INSTANCE_CANNOT_DELETE_ERROR);
//        }
        SituationPublishInstanceAddReq situationPublishInstanceAddReq = BeanUtils.copy(situationPublishInstanceUpdateReq, SituationPublishInstanceAddReq.class);
        checkParams(situationPublishInstanceAddReq);

        situationPublishInstanceMapper.updateById(patchObj);
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
        HashMap<String, String> taskTypeMap = TaskType.getTaskType();
        List<LocalType> statusList = SituationInstanceStatus.getMsgList();

        //填充相关名称
        if (CollectionUtils.isNotEmpty(situationPublishInstancePageResList)) {
            for (SituationPublishInstancePageRes situationPublishInstancePageRes : situationPublishInstancePageResList) {

                DashboardAttribute attribute = dashboardAttributeMapper.getByResourceId(situationPublishInstancePageRes.getAttributeId());
                situationPublishInstancePageRes.setTaskName(attribute.getTaskName());
                situationPublishInstancePageRes.setSubjectName(attribute.getSubjectName());

                String category = situationPublishInstancePageRes.getCategory();
                if (taskTypeMap.containsKey(category)) {
                    situationPublishInstancePageRes.setCategory(taskTypeMap.get(category));
                }

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
