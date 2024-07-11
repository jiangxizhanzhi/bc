package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
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
import com.cyberpeace.cr.crbase.service.impl.bo.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
     *
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

        // 称号等级更新, 删除保留称号文件
        if (isUpdated) {
            deleteTitleCertFile(
                    lambdaQuery().eq(CrTitle::getLevelId, crTitleLevel.getResourceId()).list()
                            .stream().map(CrTitle::getResourceId).collect(Collectors.toList())
            );
        }
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
     *
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

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                save(crTitle);
                crTitleBusinessTemplateMapper.saveBatch(crTitle.getResourceId(), pendingSaveTrains);
            }
        });
        // 新增称号时, 已完成称号条件用户获得称号
        checkFinishTitle(requestData.getBusinessTemplateIds());

        return titleGet(crTitle.getResourceId());
    }

    @Async
    void checkFinishTitle(List<String> businessTemplateIds) {
        crTitleBusinessTemplateMapper.selectAlreadyTrainUser(businessTemplateIds).forEach(
                item -> checkTitle(Collections.singletonList(item.getUserId()), item.getBusinessTemplateId())
        );
    }

    @Override
    public CrTitle titleUpdate(CrTitleReq requestData) {
        CrTitle crTitle = titleGet(requestData.getResourceId());
        if (crTitle == null) {
            throw new BusinessException();
        }
        // 更新前对象属性
        CrTitle origin = new CrTitle();
        BeanUtils.copyProperties(crTitle, origin);

        BeanUtils.copyProperties(requestData, crTitle);

        titleIsExist(crTitle);

        boolean isAttrChanged = !origin.getName().equals(crTitle.getName())
                || !origin.getLevelId().equals(crTitle.getLevelId());
        boolean isStandardChanged = !origin.getScore().equals(crTitle.getScore());

        Set<String> addTrains = Sets.newHashSet();
        Set<String> delTrains = Sets.newHashSet();
        if (requestData.getBusinessTemplateIds() != null && requestData.getBusinessTemplateIds().size() > 0) {
            if (requestData.getBusinessTemplateIds().size()
                    != businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds()).size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }

            // 原训练
            Set<String> originTrains = crTitleBusinessTemplateMapper.listByTitleId(
                    crTitle.getResourceId()
            ).stream().map(CrTitleBusinessTemplate::getBusinessTemplateId).collect(Collectors.toSet());

            // 当前请求新提交训练
            Set<String> currentTrains = new HashSet<>(requestData.getBusinessTemplateIds());

            // 需新增训练
            addTrains.addAll(currentTrains);
            addTrains.removeAll(originTrains);

            // 需删除训练
            delTrains.addAll(originTrains);
            delTrains.removeAll(currentTrains);

            if (!addTrains.isEmpty() || !delTrains.isEmpty()) {
                isStandardChanged = true;
            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                updateById(crTitle);
                if (!addTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.saveBatch(crTitle.getResourceId(), addTrains);
                }
                if (!delTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.deleteBatch(crTitle.getResourceId(), delTrains);
                }
            }
        });

        List<String> title_ids = Collections.singletonList(crTitle.getResourceId());
        // 称号属性更新, 删除当前称号的证书文件
        if (isAttrChanged) {
            deleteTitleCertFile(title_ids);
        }
        // 及格标准或关联训练更新, 删除已获取并重新生成称号
        if (isStandardChanged) {
            this.getBaseMapper().deleteTitleUser(title_ids);
            checkFinishTitle(
                    crTitleBusinessTemplateMapper.listByTitleId(
                            crTitle.getResourceId()
                    ).stream().map(CrTitleBusinessTemplate::getBusinessTemplateId).collect(Collectors.toList())
            );
        }

        return titleGet(crTitle.getResourceId());
    }

    /**
     * 清除保留的称号证书文件
     *
     * @param title_ids 称号id
     */
    @Async
    void deleteTitleCertFile(List<String> title_ids) {
        title_ids.forEach(
                titleId -> FileUtil.deleteDir(Paths.get(AttachmentPath.TITLE_CERT_TEMPLATE.getDesc(), titleId).toFile())
        );
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
    public Boolean titleBatchDelete(List<String> ids) {
        Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                // 级联删除用户已经获得的此称号
                getBaseMapper().deleteTitleUser(ids);
                return removeByIds(ids);
            }
        });
        if (result != null && result) {
            deleteTitleCertFile(ids);
        }
        return result;
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
        CrTitleDetailBo crTitleDetailBo = titleDetail(levelId, title);
        if (crTitleDetailBo == null) {
            // 非法请求
            throw new BusinessException(CrBusinessErrorCode.INVALID_TITLE);
        }

        return this.getBaseMapper().listTitleStandard(page, UserInfoContext.getUserContext().getUserId(), crTitleDetailBo.getResourceId());
    }

    @Override
    public CrTitleDetailBo titleDetail(String levelId, String title) {
        return this.getBaseMapper().titleDetail(levelId, title);
    }

    @Override
    @Async
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
                        (int) Math.floor((double) userSkillPoint.get(i) / missionSkillPoint.get(i) * 100)
                );
            }

            CrTitleTrainProgress crTitleTrainProgress = new CrTitleTrainProgress();
            crTitleTrainProgress.setUserId(userId);
            crTitleTrainProgress.setRoomId(roomId);
            crTitleTrainProgress.setBusinessTemplateId(businessTemplateId);
            crTitleTrainProgress.setScore((int) Math.round((double) score / maxScore * 100));
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
            this.getBaseMapper().insertTitleUser(pendingSaveTitle, this.getBaseMapper().getTodayIndex());
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

    @Override
    public List<String> getTitleByTrain(String businessTemplateId) {
        return this.getBaseMapper().listTitleNameByTrain(businessTemplateId);
    }

    @Override
    public List<String> getTitleByTemplateId(Integer businessType, String templateId) {
        if (businessType.equals(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode())) {
            return this.getBaseMapper().listTitleNameByTeamTemplate(templateId);
        }
        return this.getBaseMapper().listTitleNameByRbTemplate(templateId);
    }

    @Override
    public CrTitleCertBo getTitleCertInfo(String titleId) {
        CrTitleUser crTitleUser = getBaseMapper().getUserTitle(
                UserInfoContext.getUserContext().getUserId(), titleId
        );
        if (crTitleUser == null) {
            return null;
        }

        CrTitle crTitle = titleGet(titleId);
        if (crTitle == null) {
            return null;
        }
        CrTitleLevel crTitleLevel = levelGet(crTitle.getLevelId());

        CrTitleCertBo crTitleCertBo = new CrTitleCertBo();

        // 日期格式化
        crTitleCertBo.setCreateTime(DateUtil.DateToCnDateString(crTitleUser.getCreateTime()));
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
        crTitleCertBo.setValidityTime(
                dateFormat1.format(DateUtils.addYears(crTitleUser.getCreateTime(), 3))
        );
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");

        // 生成证书编号
        String serialNumber = crTitleUser.getIdx().toString();
        if (serialNumber.length() < 3) {
            serialNumber = StringUtils.repeat("0", (3 - serialNumber.length())) + serialNumber;
        }
        crTitleCertBo.setCertNumber("CP" + dateFormat2.format(crTitleUser.getCreateTime()) + serialNumber);

        crTitleCertBo.setUserId(crTitleUser.getUserId());
        crTitleCertBo.setUsername(remoteUserService.getById(crTitleUser.getUserId()).getName());
        crTitleCertBo.setTitleId(crTitle.getResourceId());
        crTitleCertBo.setTitle(crTitle.getName());
        crTitleCertBo.setLevelId(crTitleLevel.getResourceId());
        crTitleCertBo.setLevel(crTitleLevel.getName());

        return crTitleCertBo;
    }

    @Override
    public boolean deleteTitleBusinessTemplate(List<String> businessTemplateIds) {
        if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
            return false;
        }
        List<CrTitleBusinessTemplate> titleBusinessTemplateList = crTitleBusinessTemplateMapper.selectByBusinessTemplateIds(businessTemplateIds);
        // 训练未关联称号
        if (titleBusinessTemplateList.isEmpty()) {
            return false;
        }

        // 删除训练关联称号
        Set<String> titleIdSet = Sets.newHashSet();
        List<Integer> titleBusinessTemplateIdList = Lists.newArrayList();

        titleBusinessTemplateList.forEach(crTitleBusinessTemplate -> {
            titleIdSet.add(crTitleBusinessTemplate.getTitleId());
            titleBusinessTemplateIdList.add(crTitleBusinessTemplate.getId());
        });

        boolean result = SqlHelper.retBool(crTitleBusinessTemplateMapper.deleteBatchIds(titleBusinessTemplateIdList));

        List<String> titleIdList = new ArrayList<>(titleIdSet);
        this.getBaseMapper().deleteTitleUser(titleIdList);

        // 称号下未被删除训练集合
        Set<String> businessTemplateIdSet = crTitleBusinessTemplateMapper.listByTitleIds(titleIdList)
                .stream().map(CrTitleBusinessTemplate::getBusinessTemplateId)
                .collect(Collectors.toSet());
        if (!businessTemplateIdSet.isEmpty()) {
            checkFinishTitle(new ArrayList<>(businessTemplateIdSet));
        }

        return result;
    }

    @Override
    public BaseRsp<ListRsp<CrTitleUserBo>> titleDepartment(String name, Page page) {
        List<UserInfo> users;
        List<CrTitleUserBo> res = new ArrayList<>();
        ListRsp<CrTitleUserBo> rsp = new ListRsp();
        users = remoteUserService.getMemberList(name);
        if (users.isEmpty()) {
            return new BaseRsp(rsp);
        }
        List<String> userIds = StreamApiUtils.getColumn(users, UserInfo::getUserId);
        List<CrTitleBo> crTitleBoIPage = this.baseMapper.listUserTitleByDepartment(userIds);
        Map<String, CrTitleBo> crTitleBoMap = StreamApiUtils.toMap(crTitleBoIPage, CrTitleBo::getCreateUserId, record -> record);
        users.forEach(userInfo -> {
            CrTitleUserBo crTitleUserBo = new CrTitleUserBo();
            if (StringUtils.isNotBlank(userInfo.getName())) {
                crTitleUserBo.setName(userInfo.getName());
            }
            if (StringUtils.isNotBlank(userInfo.getOrganizationData())) {
                String organizationData = userInfo.getOrganizationData();
                String[] split = organizationData.split("/");
                if (split.length > 0) {
                    crTitleUserBo.setDepartmentName(split[split.length - 1]);
                }
            }
            CrTitleBo crTitleBo = crTitleBoMap.get(userInfo.getUserId());
            crTitleUserBo.setResourceId(userInfo.getUserId());
            if (null != crTitleBo) {
                crTitleUserBo.setTitle(crTitleBo.getName());
            }
            res.add(crTitleUserBo);

        });
        List<CrTitleUserBo> list = getCrTitleUserBosByPage(page, res);
        rsp.setList(list);
        rsp.setTotal(res.size());
        rsp.setLastPage(page.getPages());
        rsp.setCurrentPage(page.getCurrent());
        return new BaseRsp(rsp);
    }

    private List<CrTitleUserBo> getCrTitleUserBosByPage(Page page, List<CrTitleUserBo> res) {
        long current = page.getCurrent();
        long start;
        if (current <= 1L) {
            start = 0L;
        } else {
            start = (current - 1) * page.getSize();
        }
        if (start > res.size()) {
            start = 0L;
        }
        long value = start + page.getSize();
        int end = value > res.size() ? res.size() : Math.toIntExact(value);

        List<CrTitleUserBo> list = res.subList(Math.toIntExact(start), end);
        return list;
    }

    @Override
    public IPage<CrTitleTrainByDepartmentBo> trainDepartment(String name, String title, String level, Page page) {
        List<UserInfo> users;
        users = remoteUserService.getMemberList(name);
        if (users.isEmpty()) {
            return null;
        }
        Map<String, UserInfo> userInfoMap = StreamApiUtils.toMap(users, UserInfo::getUserId, user -> user);
        List<String> userIds = StreamApiUtils.getColumn(users, UserInfo::getUserId);
        IPage<CrTitleTrainByDepartmentBo> crTitleStandardBoIPage = this.baseMapper.listTitleStandards(page, userIds, title, level);
        List<CrTitleDetailBo> crTitleDetailBos = this.baseMapper.titleDetails(level, title);
        Map<String, CrTitleDetailBo> crTitleDetailBoMap = StreamApiUtils.toMap(crTitleDetailBos, CrTitleDetailBo::getResourceId, tmp -> tmp);
        List<CrTitleTrainByDepartmentBo> records = new ArrayList<>();
        crTitleStandardBoIPage.convert(crTitleStandardBo -> {
            CrTitleDetailBo crTitleDetailBo = crTitleDetailBoMap.get(crTitleStandardBo.getTitleId());
            UserInfo userInfo = userInfoMap.get(crTitleStandardBo.getCreateUser());
            if (null != crTitleDetailBo) {
                crTitleStandardBo.setPassScore(crTitleDetailBo.getScore());
                crTitleStandardBo.setLevel(crTitleDetailBo.getLevel());
            }
            if (null != userInfo) {
                crTitleStandardBo.setCreateUser(userInfo.getName());
                records.add(crTitleStandardBo);
            }
            return crTitleStandardBo;
        });
        crTitleStandardBoIPage.setRecords(records);
        return crTitleStandardBoIPage;
    }

    @Override
    public List<CrTitleTrainByDepartmentBo> getTrainDepartment() {

        List<UserInfo> users;
        users = remoteUserService.getMemberList(null);
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        Map<String, UserInfo> userInfoMap = StreamApiUtils.toMap(users, UserInfo::getUserId, user -> user);
        List<String> userIds = StreamApiUtils.getColumn(users, UserInfo::getUserId);
        List<CrTitleTrainByDepartmentBo> crTitleStandardBoList = this.baseMapper.listTitleStandardByUsers(userIds);

        List<CrTitleDetailBo> crTitleDetailBos = this.baseMapper.titleDetails(null, null);
        Map<String, CrTitleDetailBo> crTitleDetailBoMap = StreamApiUtils.toMap(crTitleDetailBos, CrTitleDetailBo::getResourceId, tmp -> tmp);
        List<CrTitleTrainByDepartmentBo> records = new ArrayList<>();
        crTitleStandardBoList.forEach(crTitleStandardBo -> {
                    CrTitleDetailBo crTitleDetailBo = crTitleDetailBoMap.get(crTitleStandardBo.getTitleId());
                    UserInfo userInfo = userInfoMap.get(crTitleStandardBo.getCreateUser());
                    if (null != crTitleDetailBo) {
                        crTitleStandardBo.setPassScore(crTitleDetailBo.getScore());
                        crTitleStandardBo.setLevel(crTitleDetailBo.getLevel());
                    }
                    if (null != userInfo) {
                        crTitleStandardBo.setCreateUser(userInfo.getName());
                        records.add(crTitleStandardBo);
                    }
                }
        );
        return records;
    }
}
