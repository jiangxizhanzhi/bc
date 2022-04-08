package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleBusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleLevelMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleStandardBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleTrainBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrTitleServiceImpl extends ServiceImpl<CrTitleMapper, CrTitle> implements CrTitleService {
    @Autowired
    private CrTitleLevelMapper crTitleLevelMapper;

    @Autowired
    private CrTitleBusinessTemplateMapper crTitleBusinessTemplateMapper;

    @Autowired
    private BusinessTemplateMapper businessTemplateMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private CrTaskService crTaskService;

    @Autowired
    private MissionService missionService;

    @Override
    public CrTitleLevel levelGet(String resourceId) {
        return crTitleLevelMapper.selectById(resourceId);
    }

    @Override
    public CrTitleLevel levelCreate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = new CrTitleLevel();
        crTitleLevel.setName(requestData.getName());
        crTitleLevel.setCreateUserId(UserInfoContext.getUserContext().getUserId());

        boolean isCreated = SqlHelper.retBool(crTitleLevelMapper.insert(crTitleLevel));
        return isCreated ? levelGet(crTitleLevel.getResourceId()) : null;
    }

    @Override
    public CrTitleLevel levelUpdate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = crTitleLevelMapper.selectById(requestData.getResourceId());
        if (crTitleLevel == null) {
            return null;
        }

        crTitleLevel.setName(requestData.getName());
        boolean isUpdated = SqlHelper.retBool(crTitleLevelMapper.updateById(crTitleLevel));
        return isUpdated ? levelGet(crTitleLevel.getResourceId()) : null;
    }

    @Override
    public IPage<CrTitleLevel> levelList(Page page, String name) {
        return crTitleLevelMapper.list(page, name);
    }

    @Override
    public Boolean levelBatchDelete(BatchDeleteReq requestData) {
        if (!lambdaQuery().in(CrTitle::getLevelId, requestData.getIds()).list().isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.USING_TITLE_LEVEL_DELETE_ERROR);
        }
        return SqlHelper.retBool(crTitleLevelMapper.deleteBatchIds(requestData.getIds()));
    }

    @Override
    public List<CrTitleLevel> levelFullList(String title) {
        return crTitleLevelMapper.selectLevelFullList(title);
    }

    @Override
    public List<CrTitle> titleFullList(String levelId, Boolean isDistinct) {
        List<CrTitle> querySet = (levelId != null && !"".equals(levelId))
                ? lambdaQuery().eq(CrTitle::getLevelId, levelId).orderByDesc(CrTitle::getId).list()
                : lambdaQuery().orderByDesc(CrTitle::getId).list();
        return isDistinct ? StreamApiUtils.distinctByColumn(querySet, CrTitle::getName) : querySet;
    }

    @Override
    public List<CrTitle> titleFullList(String levelId) {
        return titleFullList(levelId, true);
    }

    @Override
    public CrTitle titleGet(String resourceId) {
        return getById(resourceId);
    }

    @Override
    public CrTitle titleCreate(CrTitleReq requestData) {
        CrTitle crTitle = new CrTitle();
        BeanUtils.copyProperties(requestData, crTitle);
        crTitle.setCreateUserId(UserInfoContext.getUserContext().getUserId());

        List<String> pendingSaveTrains = requestData.getBusinessTemplateIds();
        if (pendingSaveTrains == null || pendingSaveTrains.isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_IS_NULL);
        } else {
            List<BusinessTemplate> activeTrains = businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds());
            if (activeTrains.isEmpty() || activeTrains.size() != pendingSaveTrains.size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                save(crTitle);
                crTitleBusinessTemplateMapper.saveBatch(crTitle.getResourceId(), pendingSaveTrains);
            }
        });

        return titleGet(crTitle.getResourceId());
    }

    @Override
    public CrTitle titleUpdate(CrTitleReq requestData) {
        CrTitle origin = titleGet(requestData.getResourceId());
        if (origin == null) {
            throw new BusinessException();
        }

        BeanUtils.copyProperties(requestData, origin);

        Set<String> addTrains = Sets.newHashSet();
        Set<String> delTrains = Sets.newHashSet();
        if (requestData.getBusinessTemplateIds() != null && requestData.getBusinessTemplateIds().size() > 0) {
            if (requestData.getBusinessTemplateIds().size()
                    != businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds()).size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }

            // 原训练
            Set<String> originTrains = crTitleBusinessTemplateMapper.listByTitleId(
                    origin.getResourceId()
            ).stream().map(CrTitleBusinessTemplate::getBusinessTemplateId).collect(Collectors.toSet());

            // 当前请求新提交训练
            Set<String> currentTrains = new HashSet<>(requestData.getBusinessTemplateIds());

            // 需新增训练
            addTrains.addAll(currentTrains);
            addTrains.removeAll(originTrains);

            // 需删除训练
            delTrains.addAll(originTrains);
            delTrains.removeAll(currentTrains);
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                updateById(origin);
                if (!addTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.saveBatch(origin.getResourceId(), addTrains);
                }
                if (!delTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.deleteBatch(origin.getResourceId(), delTrains);
                }
            }
        });

        return titleGet(origin.getResourceId());
    }

    private Map<String, String> generateUserMap(List<String> userIds) {
        return remoteUserService.getByIdList(userIds).stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getName));
    }

    @Override
    public IPage<CrTitleBo> titleList(Page page, String name) {
        IPage<CrTitleBo> origin = this.getBaseMapper().list(page, name);

        // 创建用户名称获取
        Map<String, String> usersMap = generateUserMap(
                origin.getRecords().stream().map(CrTitleBo::getCreateUserId).collect(Collectors.toList())
        );

        origin.getRecords().forEach(item -> item.setCreateUser(usersMap.get(item.getCreateUserId())));
        return origin;
    }

    @Override
    public Boolean titleBatchDelete(BatchDeleteReq requestData) {
        // todo 级联数据删除
        return removeByIds(requestData.getIds());
    }

    @Override
    public IPage<CrTitleTrainBo> eventsList(Page page, Integer businessType, String search) {
        return eventsExtraData(crTitleBusinessTemplateMapper.listTrain(page, businessType, search, null));
    }

    @Override
    public IPage<CrTitleTrainBo> usingEventsList(String resourceId, Page page, Integer businessType, String search) {
        return eventsExtraData(crTitleBusinessTemplateMapper.listTrain(page, businessType, search, resourceId));
    }

    private IPage<CrTitleTrainBo> eventsExtraData(IPage<CrTitleTrainBo> selectResult) {
        Map<String, String> usersMap = generateUserMap(
                selectResult.getRecords().stream().map(CrTitleTrainBo::getCreateUserId).collect(Collectors.toList())
        );

        selectResult.getRecords().forEach(crTitleTrainBo -> crTitleTrainBo.setCreateUser(usersMap.get(crTitleTrainBo.getCreateUserId())));

        return selectResult;
    }

    @Override
    public IPage<CrTitleBo> userTitle(Page page, String levelId, String title) {
        return this.getBaseMapper().listUserTitle(page, UserInfoContext.getUserContext().getUserId(), levelId, title);
    }

    @Override
    public IPage<CrTitleStandardBo> titleStandard(Page page, String levelId, String title) {
        CrTitle crTitle = titleDetail(levelId, title);
        if (crTitle == null) {
            // todo exception
            throw new BusinessException();
        }

        return this.getBaseMapper().listTitleStandard(page, UserInfoContext.getUserContext().getUserId(), crTitle.getResourceId());
    }

    @Override
    public CrTitle titleDetail(String levelId, String title) {
        return this.getBaseMapper().titleDetail(levelId, title);
    }

    @Override
    public void saveScore(Integer businessType, String roomId) {
        // todo 训练类型区分
        List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.TeamRoleGroup.STUDENT));

        // 全量训练任务
        Map<String, Mission> missionsMap = missionService.batchGetByRoom(roomId).stream().collect(
                Collectors.toMap(MissionBase::getResourceId, mission -> mission)
        );

        // 全量训练已下发任务
        List<CrTask> taskList = crTaskService.getByRoomId(roomId);

        List<String> users = Lists.newArrayList();
        crRoles.forEach(crRole -> users.addAll(crRole.getUserIdList()));

        List<CrTitleTrainProgress> usersTrain = Lists.newArrayList();

        users.forEach(userId -> {
            Integer maxScore = 0;
            Integer score = 0;
            List<Integer> missionSkillPoint = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
            List<Integer> userSkillPoint = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

            for (CrTask crTask : taskList) {
                if (crTask.getUserId().equals(userId)) {
                    Mission mission = missionsMap.get(crTask.getMissionId());

                    maxScore += mission.getScore();
                    score += crTask.getScore();

                    List<Integer> skillPoint = JsonUtil.toJsonObjectArray(mission.getSkillPoints(), Integer.class);

                    for (int i = 0; i < missionSkillPoint.size(); i++) {
                        missionSkillPoint.set(i, missionSkillPoint.get(i) + skillPoint.get(i));
                    }
                    if (crTask.getStatus().equals(CrTask.TaskStatus.SUCCESS.getCode())) {
                        for (int i = 0; i < userSkillPoint.size(); i++) {
                            userSkillPoint.set(i, userSkillPoint.get(i) + skillPoint.get(i));
                        }
                    }
                }
            }
            List<Integer> userSkillPointScore = Lists.newArrayList();
            for (int i = 0; i < userSkillPoint.size(); i++) {
                userSkillPointScore.add(
                        (int) Math.ceil((double) userSkillPoint.get(i) / missionSkillPoint.get(i))
                );
            }

            CrTitleTrainProgress crTitleTrainProgress = new CrTitleTrainProgress();
            crTitleTrainProgress.setUserId(userId);
            crTitleTrainProgress.setRoomId(roomId);
            // todo business_template_id
            crTitleTrainProgress.setScore((int) Math.ceil((double) score / maxScore));
            crTitleTrainProgress.setSkillPointsScore(JsonUtil.toJsonString(userSkillPointScore));
            usersTrain.add(crTitleTrainProgress);
        });

        this.getBaseMapper().insertScore(usersTrain);

    }

}
