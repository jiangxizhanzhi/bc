package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeCreateReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeUpdateReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeDetailRsp;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTrainAction;
import com.cyberpeace.cr.crbase.dao.mapper.CrNoticeMapper;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.CrTrainActionService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CrNoticeServiceImpl extends ServiceImpl<CrNoticeMapper, CrNotice> implements CrNoticeService {

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private CrTrainActionService crTrainActionService;

    @Autowired
    private CrTaskService crTaskService;

    @Override
    public List<CrLabelGroup> noticeGroup(String roomId) {
        // 判断角色组
        CrRole oneCrRole = crRoleService.getCrRoleByRoomIdAndUserId(
                roomId, UserInfoContext.getUserContext().getUserId()
        );
        if (oneCrRole == null) {
            throw new BusinessException();
        }

        List<CrLabelGroup> crLabelGroupList = Lists.newArrayList();

        if (oneCrRole.isRvbRole()) {
            for (CrNotice.Group value : CrNotice.Group.values()) {
                crLabelGroupList.add(CrLabelGroup.builder()
                        .value(value.getCode())
                        .label(CommonContext.isChinese() ? value.getCnDesc() : value.getEnDesc())
                        .build());
            }

        } else if (oneCrRole.isTeamRole()) {
            crLabelGroupList.add(CrLabelGroup.builder()
                    .value(CrNotice.Group.ALL.getCode())
                    .label(CommonContext.isChinese() ? CrNotice.Group.ALL.getCnDesc() : CrNotice.Group.ALL.getEnDesc())
                    .build());

            crRoleService.getRolesByRoom(
                    roomId,
                    BusinessTemplate.BusinessType.TRAINING_TEAM.getCode()
            ).stream()
                    .filter(CrRole::isTeamStudent)
                    .forEach(crRole -> crLabelGroupList.add(CrLabelGroup.builder()
                            .value(crRole.getResourceId())
                            .label(crRole.getName())
                            .build()));
        }

        return crLabelGroupList;
    }

    private Map<String, AttachmentBo> convAttachmentBoMap(List<String> attachmentIds) {
        if (attachmentIds.isEmpty()) {
            return Maps.newHashMap();
        }

        String attachmentIdsString = String.join(CommonConstant.CODE_COMMA, attachmentIds);

        return attachmentService.selectByIds(attachmentIdsString, true)
                .stream().collect(Collectors.toMap(AttachmentBo::getResourceId, attachmentBo -> attachmentBo));
    }

    @Override
    public ListRsp<CrNoticeListRsp> pageAdmin(String roomId, Page page) {
        IPage<CrNoticeListRsp> crNoticeIPage = this.getBaseMapper().pageAdmin(roomId, page);

        List<String> attachmentIds = crNoticeIPage.getRecords()
                .stream().map(CrNoticeListRsp::getAttachmentId).collect(Collectors.toList());
        Map<String, AttachmentBo> attachmentBoMap = convAttachmentBoMap(attachmentIds);
        crNoticeIPage.getRecords().forEach(crNoticeListRsp -> {
            crNoticeListRsp.setIsTop(crNoticeListRsp.getStatus().equals(CrNotice.Status.TOP.getCode()));
            if (StringUtils.isNotEmpty(crNoticeListRsp.getAttachmentId())) {
                crNoticeListRsp.setAttachmentBo(attachmentBoMap.get(crNoticeListRsp.getAttachmentId()));
            }
        });

        return PageUtil.getListResult(crNoticeIPage);
    }

    @Override
    public List<CrNoticeListRsp> page(String roomId) {
        // 用户在训练中的角色
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, UserInfoContext.getUserContext().getUserId());
        if (crRole == null) {
            throw new BusinessException();
        }

        List<CrNoticeListRsp> result = Lists.newArrayList();
        String group;

        // 判断训练类型
        if (crRole.isTeamRole()) {
            group = crRole.getResourceId();
        } else if (crRole.isRvbRole()) {
            group = crRole.isRedStudent() ? CrNotice.Group.RED.getCode() : CrNotice.Group.BLUE.getCode();
        } else {
            throw new BusinessException();
        }

        // 获取用户可视公告
        List<CrNotice> crNoticeList = this.getBaseMapper().page(roomId, group);

        // 关联附件map
        List<String> attachmentIds = crNoticeList.stream().map(CrNotice::getAttachmentId).collect(Collectors.toList());
        Map<String, AttachmentBo> attachmentBoMap = convAttachmentBoMap(attachmentIds);

        crNoticeList.forEach(crNotice -> {
            CrNoticeListRsp crNoticeListRsp = new CrNoticeListRsp();

            BeanUtils.copyProperties(crNotice, crNoticeListRsp);
            crNoticeListRsp.setIsTop(crNotice.getStatus().equals(CrNotice.Status.TOP.getCode()));
            if (StringUtils.isNotEmpty(crNotice.getAttachmentId())) {
                crNoticeListRsp.setAttachmentBo(attachmentBoMap.get(crNotice.getAttachmentId()));
            }

            result.add(crNoticeListRsp);
        });

        return result;
    }

    @Override
    public CrNoticeDetailRsp get(String resourceId) {
        CrNotice crNotice = getById(resourceId);
        if (crNotice == null) {
            throw new BusinessException();
        }

        CrNoticeDetailRsp crNoticeDetailRsp = new CrNoticeDetailRsp();
        BeanUtils.copyProperties(crNotice, crNoticeDetailRsp);
        crNoticeDetailRsp.setIsTop(crNotice.getStatus().equals(CrNotice.Status.TOP.getCode()));

        if (StringUtils.isNotEmpty(crNoticeDetailRsp.getAttachmentId())) {
            crNoticeDetailRsp.setAttachmentBo(attachmentService.getById(crNoticeDetailRsp.getAttachmentId()));
        }

        return crNoticeDetailRsp;
    }

    @Override
    public CrNoticeDetailRsp create(CrNoticeCreateReq crNoticeReq) {
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(
                crNoticeReq.getRoomId(), UserInfoContext.getUserContext().getUserId()
        );

        if (crRole == null) {
            throw new BusinessException();
        }

        CrNotice crNotice = new CrNotice();

        BeanUtils.copyProperties(crNoticeReq, crNotice);
        crNotice.setBusinessType(crRole.getBusinessType());
        crNotice.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        crNotice.setReleaseTime(new Date());
        crNotice.setStatus(crNoticeReq.getIsTop() ? CrNotice.Status.TOP.getCode() : CrNotice.Status.NORMAL.getCode());

        save(crNotice);

        // 平台动态-发布公告
        if (crNotice.getBusinessType().intValue() == BusinessTemplate.BusinessType.RED_VS_BLUE.getCode()) {
            String content;
            Integer moduleEvent;
            if (crNotice.getGroup().equals(CrNotice.Group.RED.getCode())) {
                content = BusinessException.getMessageByLanguage(CrBusinessErrorCode.RED_NOTICE);
                moduleEvent = CrTrainAction.PlatformEvent.RED_NOTICE.getCode();
            } else if (crNotice.getGroup().equals(CrNotice.Group.BLUE.getCode())) {
                content = BusinessException.getMessageByLanguage(CrBusinessErrorCode.BLUE_NOTICE);
                moduleEvent = CrTrainAction.PlatformEvent.BLUE_NOTICE.getCode();
            } else {
                content = BusinessException.getMessageByLanguage(CrBusinessErrorCode.RELEASE_NOTICE);
                moduleEvent = CrTrainAction.PlatformEvent.RELEASE_NOTICE.getCode();
            }
            content = String.format(content, crNotice.getContent());

            crTrainActionService.savePlatformAction(crNotice.getRoomId(), content, moduleEvent);
        }

        crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.RELEASE_NOTICE);

        return get(crNotice.getResourceId());
    }

    @Override
    public CrNoticeDetailRsp update(CrNoticeUpdateReq crNoticeReq) {
        CrNotice crNotice = getById(crNoticeReq.getResourceId());
        if (crNotice == null) {
            throw new BusinessException("非法请求");
        }

        BeanUtils.copyProperties(crNoticeReq, crNotice);
        if (crNoticeReq.getIsTop() != null) {
            crNotice.setStatus(crNoticeReq.getIsTop() ? CrNotice.Status.TOP.getCode() : CrNotice.Status.NORMAL.getCode());
        }

        updateById(crNotice);
        crTaskService.pushMissionWS(crNotice.getRoomId(), MissionWsCode.RELEASE_NOTICE);

        return get(crNotice.getResourceId());
    }

    @Override
    public Boolean updateStatus(List<String> ids, Integer status) {
        List<CrNotice> crNotices = lambdaQuery().in(CrNotice::getResourceId, ids).list();
        crNotices.forEach(crNotice -> crNotice.setStatus(status));

        Boolean result = updateBatchById(crNotices);
        if (result) {
            crTaskService.pushMissionWS(crNotices.get(0).getRoomId(), MissionWsCode.RELEASE_NOTICE);
        }
        return result;
    }

}
