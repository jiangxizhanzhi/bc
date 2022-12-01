package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateRefMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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

    @Override
    public SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
        SituationPublishInstance situationPublishInstance = BeanUtils.copy(situationPublishInstanceAddReq,
                SituationPublishInstance.class);
        checkParams(situationPublishInstanceAddReq);

        String userId = UserInfoContext.getUserContext().getUserId();
        situationPublishInstance.setCreateUserId(userId);
        situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());

        try{
            situationPublishInstanceMapper.insert(situationPublishInstance);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
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
        if(null == dashboardTemplateRef){
            throw new BusinessException(SituationErrorCode.TEMPLATE_INVALID_ERROR);
        }
    }

    @Override
    public SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {
        SituationPublishInstance patchObj = BeanUtils.copy(situationPublishInstanceUpdateReq, SituationPublishInstance.class);
        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(situationPublishInstanceUpdateReq.getResourceId());
        if(null == situationPublishInstance){
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
}
