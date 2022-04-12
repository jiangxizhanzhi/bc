package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
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
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleSkillRadarBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleStandardBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleTrainBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 称号等级重复性校验
     * @param crTitleLevel entity
     */
    private void titleLevelIsExist(CrTitleLevel crTitleLevel) {
        LambdaQueryChainWrapper<CrTitleLevel> querySet = ChainWrappers.lambdaQueryChain(crTitleLevelMapper)
                .eq(CrTitleLevel::getName, crTitleLevel.getName());

        if (!StringUtils.isEmpty(crTitleLevel.getResourceId())) {
            querySet.ne(CrTitleLevel::getResourceId, crTitleLevel.getResourceId());
        }

        if (querySet.one() != null) {
            throw new BusinessException(CrBusinessErrorCode.TITLE_LEVEL_IS_EXIST);
        }
    }

    @Override
    public CrTitleLevel levelCreate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = new CrTitleLevel();
        crTitleLevel.setName(requestData.getName());
        crTitleLevel.setCreateUserId(UserInfoContext.getUserContext().getUserId());

        titleLevelIsExist(crTitleLevel);

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
        titleLevelIsExist(crTitleLevel);

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
        List<CrTitle> querySet = !StringUtils.isEmpty(levelId)
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

    /**
     * 称号重复性校验
     * @param crTitle entity
     */
    private void titleIsExist(CrTitle crTitle) {
        LambdaQueryChainWrapper<CrTitle> querySet = lambdaQuery()
                .eq(CrTitle::getLevelId, crTitle.getLevelId())
                .eq(CrTitle::getName, crTitle.getName());

        if (!StringUtils.isEmpty(crTitle.getResourceId())) {
            querySet.ne(CrTitle::getResourceId, crTitle.getResourceId());
        }

        if (querySet.one() != null) {
            CrTitleLevel crTitleLevel = levelGet(crTitle.getLevelId());
            String levelName = crTitleLevel != null ? crTitleLevel.getName() : "Undefined";
            throw new BusinessException(CrBusinessErrorCode.TITLE_IS_EXIST, levelName);
        }
    }

    @Override
    public CrTitle titleCreate(CrTitleReq requestData) {
        CrTitle crTitle = new CrTitle();
        BeanUtils.copyProperties(requestData, crTitle);
        crTitle.setCreateUserId(UserInfoContext.getUserContext().getUserId());

        titleIsExist(crTitle);

        List<String> pendingSaveTrains = requestData.getBusinessTemplateIds();
        if (pendingSaveTrains == null || pendingSaveTrains.isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_IS_NULL);
        } else {
            List<BusinessTemplate> activeTrains = businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds());
            if (activeTrains.isEmpty() || activeTrains.size() != pendingSaveTrains.size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }
        }

        // todo 新增称号时, 存在已完成称号条件用户
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

        titleIsExist(origin);

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

        // todo 检查对象更新属性，更新用户称号

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
        if (StringUtils.isEmpty(levelId) || StringUtils.isEmpty(title)) {
            return null;
        }
        CrTitle crTitle = titleDetail(levelId, title);
        if (crTitle == null) {
            // 非法请求
            throw new BusinessException();
        }

        return this.getBaseMapper().listTitleStandard(page, UserInfoContext.getUserContext().getUserId(), crTitle.getResourceId());
    }

    @Override
    public CrTitle titleDetail(String levelId, String title) {
        return this.getBaseMapper().titleDetail(levelId, title);
    }

    @Override
    public void saveScore(Integer businessType, String roomId, String businessTemplateId) {
        List<CrRole> crRoles;
        if (businessType.equals(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode())) {
            crRoles = crRoleService.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.RbRoleGroup.RED));
            crRoles.addAll(crRoleService.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.RbRoleGroup.BLUE)));
        } else if (businessType.equals(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode())) {
            crRoles = crRoleService.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.TeamRoleGroup.STUDENT));
        } else {
            crRoles = crRoleService.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.SINGLE_ROLE_GROUP_CODE));
        }

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
                    if (crTask.getScore() != null) {
                        score += crTask.getScore();
                    }

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
                        (int) Math.ceil((double) userSkillPoint.get(i) / missionSkillPoint.get(i) * 100)
                );
            }

            CrTitleTrainProgress crTitleTrainProgress = new CrTitleTrainProgress();
            crTitleTrainProgress.setUserId(userId);
            crTitleTrainProgress.setRoomId(roomId);
            crTitleTrainProgress.setBusinessTemplateId(businessTemplateId);
            crTitleTrainProgress.setScore((int) Math.ceil((double) score / maxScore * 100));
            crTitleTrainProgress.setSkillPointsScore(JsonUtil.toJsonString(userSkillPointScore));
            usersTrain.add(crTitleTrainProgress);
        });

        if (!usersTrain.isEmpty()) {
            this.getBaseMapper().insertScore(usersTrain);
            checkTitle(users, businessTemplateId);
        }
    }

    @Override
    public void checkTitle(List<String> users, String businessTemplateId) {
        List<CrTitleUser> pendingSaveTitle = Lists.newArrayList();
        users.forEach(user -> {
            Map<String, Boolean> userTitle = Maps.newHashMap();
            crTitleBusinessTemplateMapper.selectFinishTrain(user, businessTemplateId).forEach(item -> {
                if (item.getIsFinish()) {
                    userTitle.putIfAbsent(item.getTitleId(), true);
                } else {
                    userTitle.put(item.getTitleId(), false);
                }
            });
            userTitle.forEach((key, val) -> {
                if (val) {
                    CrTitleUser crTitleUser = new CrTitleUser();
                    crTitleUser.setTitleId(key);
                    crTitleUser.setUserId(user);
                    pendingSaveTitle.add(crTitleUser);
                }
            });
        });
        if (!pendingSaveTitle.isEmpty()) {
            this.getBaseMapper().insertTitle(pendingSaveTitle);
        }
    }

    @Override
    public CrTitleSkillRadarBo userSkillRadar() {
        List<CrTitleTrainProgress> crTitleTrainProgresses = this.getBaseMapper().listUserTrainSkill(
                UserInfoContext.getUserContext().getUserId()
        );
        List<Integer> score = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        crTitleTrainProgresses.forEach(crTitleTrainProgress -> {
            List<Integer> perTrain = JsonUtil.toJsonObjectArray(crTitleTrainProgress.getSkillPointsScore(), Integer.class);
            for (int i = 0; i < score.size(); i++) {
                score.set(i, score.get(i) + perTrain.get(i));
            }
        });
        CrTitleSkillRadarBo crTitleSkillRadarBo = new CrTitleSkillRadarBo();
        crTitleSkillRadarBo.setValue(
                score.stream().map(item -> (int) Math.ceil((double) item / crTitleTrainProgresses.size())).collect(Collectors.toList())
        );
        crTitleSkillRadarBo.setLabel(StreamApiUtils.getColumn(missionService.getRadarLabel(), RadarLabel::getLabel));

        return crTitleSkillRadarBo;
    }
}
