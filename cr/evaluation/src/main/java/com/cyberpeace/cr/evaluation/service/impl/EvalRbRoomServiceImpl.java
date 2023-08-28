package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagQuery;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrFlagTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TrainingScore;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.evaluation.controller.req.EvalStudentScoreReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSubmitEvaluationReq;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskSubmit;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTrainTask;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalScoreMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTaskSubmitMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTrainTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalRbRoomService;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbSituationScoreRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.context.RoleUserIds;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.mission.ObjWithTime;
import com.cyberpeace.cr.redvsblue.service.context.mission.RankData;
import com.cyberpeace.cr.redvsblue.service.context.mission.UserCounter;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalRbRoomServiceImpl implements EvalRbRoomService {

    @Autowired
    private CrEvalScoreMapper crEvalScoreMapper;

    @Autowired
    private CrEvalTaskSubmitMapper crEvalTaskSubmitMapper;

    @Autowired
    private CrFlagTaskMapper crFlagTaskMapper;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private CrEvalTrainTaskMapper evalTrainTaskMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RbRoomScoreService rbRoomScoreService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private CrTaskService crTaskService;

    private final Map<Integer, Integer> evaluateStatusToActionCode = ImmutableMap.of(
            CrTaskSubmit.EvaluateStatus.SUCCESS.getCode(),
            CrTaskLog.ActionCode.MAN_SUCCESS.getCode(), CrTaskSubmit.EvaluateStatus.FAIL.getCode(),
            CrTaskLog.ActionCode.MAN_FAILURE.getCode(),
            CrTaskSubmit.EvaluateStatus.REJECT.getCode(),
            CrTaskLog.ActionCode.MAN_REJECT.getCode());

    @Override
    public IPage<MissionEvaluationContext> getRbMissionEvaluationList(EvalMissionEvaluationBo missionEvaluationQuery) {

        missionEvaluationQuery.setSort("-evaluate_time");
        missionEvaluationQuery.setPer_page(10000);

        Page page = missionEvaluationQuery.toPageParam();
        IPage<MissionEvaluationContext> data = crEvalTaskSubmitMapper.listRbCrTaskSubmit(page, missionEvaluationQuery);
        return setMission(data);
    }

    private IPage<MissionEvaluationContext> setMission(IPage<MissionEvaluationContext> data) {
        List<String> missionIds = data.getRecords().stream()
                .map(MissionEvaluationContext::getMissionHash).distinct()
                .collect(Collectors.toList());

        //获取一血, 二血, 三血及额外分数
        Map<String, MissionEvaluationContext> rankMap = CollectionUtils.isEmpty(data.getRecords()) ? Maps.newHashMap() : getRankContextMap(data);
        //获取动态任务对应flagValue
        LambdaQueryWrapper<CrFlagTask> flagTaskWrapper = new LambdaQueryWrapper<>();
        flagTaskWrapper.in(CrFlagTask::getMissionId, missionIds);
        List<CrFlagTask> flagTaskList = CollectionUtils.isEmpty(data.getRecords()) ? new ArrayList<>() : crFlagTaskMapper.selectList(flagTaskWrapper);
        Map<String, String> taskFlagValueMap = StreamApiUtils.toMap(flagTaskList, CrFlagTask::getTaskId, CrFlagTask::getFlagValue);

        List<Mission> missions = Lists.newArrayList();
        if (missionIds.size() > 0) {
            missions = missionMapper.batchSelect(missionIds);
        }

        Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId, pc -> pc);
        data.convert(missionEvaluationContext -> {

            MissionEvaluationContext rankContext = rankMap.get(missionEvaluationContext.getResourceId());
            missionEvaluationContext.setRank(rankContext.getRank());
            missionEvaluationContext.setRankScore(rankContext.getRankScore());

            Mission missionByMap = missionMap.get(missionEvaluationContext.getMissionHash());
            Mission mission = BeanUtils.copy(missionByMap, Mission.class);
            //任务答案 flag类型:静态类型取flagValue,动态根据任务查对应flagValue
            if (MissionBase.MissionType.FLAG.getCode().equals(mission.getType())) {
                MissionFlagQuery missionFlagQuery = JSON.parseObject(mission.getAnswer(), MissionFlagQuery.class);
                if (MissionFlagQuery.FlagType.STATIC_FLAG.getCode().equals(missionFlagQuery.getFlagType())) {
                    mission.setAnswer(missionFlagQuery.getFlagValue());
                } else {
                    mission.setAnswer(taskFlagValueMap.get(missionEvaluationContext.getResourceId()));
                }
            }
            missionEvaluationContext.setMission(mission);
            return missionEvaluationContext;
        });
        return data;
    }

    /**
     * 获取一血, 二血, 三血及额外分数
     */
    private Map<String, MissionEvaluationContext> getRankContextMap(IPage<MissionEvaluationContext> data) {

        List<MissionTaskSummitContext> missionTaskList= crEvalTaskSubmitMapper.listFinishMissionTaskSubmit(data.getRecords().get(0).getEvalTemplateId());
        //有序分组
        LinkedHashMap<String, List<MissionTaskSummitContext>> missionGroupMap = missionTaskList.stream().collect(Collectors.groupingBy(MissionTaskSummitContext::getMissionId, LinkedHashMap::new, Collectors.toList()));

        Map<String, MissionEvaluationContext> rankMap = Maps.newHashMap();
        for (Map.Entry<String, List<MissionTaskSummitContext>> entry : missionGroupMap.entrySet()) {
            List<String> rankList = new ArrayList<>(Arrays.asList("一血", "二血", "三血"));
            List<Double> rankScoreList = new ArrayList<>(Arrays.asList(0.2, 0.1, 0.05));
            for (MissionTaskSummitContext crTaskSubmit : entry.getValue()) {
                MissionEvaluationContext rankContext = new MissionEvaluationContext();
                rankContext.setResourceId(crTaskSubmit.getResourceId());
                boolean submitStatus = CrEvalTaskSubmit.EvaluateStatus.SUCCESS.getCode().equals(crTaskSubmit.getEvaluateStatus());
                if (rankList.size() > 0 && submitStatus) {
                    BigDecimal rankScore = BigDecimal.valueOf(crTaskSubmit.getMissionScore() * rankScoreList.remove(0))
                            .setScale(0, BigDecimal.ROUND_HALF_UP);
                    rankContext.setRankScore(Integer.parseInt(rankScore.toString()));
                    rankContext.setRank(rankList.remove(0));
                } else {
                    rankContext.setRankScore(0);
                    rankContext.setRank("否");
                }
                rankMap.put(crTaskSubmit.getResourceId(), rankContext);
            }
        }
        return rankMap;
    }

    @Override
    public JSONObject getRbScoreProgress(String evalTemplateId, String roomId, Integer roleType) {
        // 获取实例实际开始时间和理论结束时间
        TimeData timeData = rbRoomScoreService.getRbTimeData(roomId);
        RoleUserIds roleUserIds = getRoleInfo(new RoleQuery(roomId, roleType));
        List<Integer> yData = getScoreProgress(timeData, evalTemplateId, roleUserIds);

        JSONObject json = new JSONObject();
        json.put("status", timeData.getStatus());
        json.put("time_stamp", System.currentTimeMillis() / 1000);
        json.put("start_time", timeData.getStartTime());
        json.put("running", timeData.getRunning());
        json.put("total_score", timeData.getAccumulatedValue());
        json.put("x_data", timeData.getXData());
        json.put("y_data", yData);
        return json;
    }

    public List<Integer> getScoreProgress(TimeData timeData, String evalTemplateId, RoleUserIds roleUserIds) {
        // 一次取出所有用户的得分纪录
        List<CrEvalTrainTask> crEvalTrainTasks = evalTrainTaskMapper.selectByRoomIdAndUserIdsAndStatusList(evalTemplateId, roleUserIds.getUserIds(), CrTask.TaskStatus.FINISHED_STATUS_LIST);
        List<ObjWithTime> taskTimes = crEvalTrainTasks.stream().map(taskContext -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(taskContext.getLastSubmitTime());
            objWithTime.setValue(taskContext.getScore());
            return objWithTime;
        }).collect(Collectors.toList());
        return timeData.calculateCountByTime(taskTimes);
    }

    private RoleUserIds getRoleInfo(String roomId, Integer roleType) {
        return getRoleInfo(new RoleQuery(roomId, roleType));
    }

    public RoleUserIds getRoleInfo(RoleQuery roleQuery) {
        List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

        if (crRoles == null) {
            log.error("role does not exist！roleType=[{}]", roleQuery.getRoleGroup());
            throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
        }
        List<String> roleIds = Lists.newArrayList();
        List<String> userIds = Lists.newArrayList();

        for (CrRole crRole : crRoles) {
            roleIds.add(crRole.getResourceId());
            userIds.addAll(crRole.getUserIdList());
        }
        RoleUserIds roleUserIds = new RoleUserIds();
        roleUserIds.setRoleIds(roleIds);
        roleUserIds.setUserIds(userIds);

        return roleUserIds;
    }

    @Override
    public StudentScoreRsp getStudentScore(String resource_id, String user_hash) {

        List<CrEvalTrainTask> crEvalTrainTasks = evalTrainTaskMapper.selectByRoomIdAndUserId(resource_id, user_hash);
        List<TaskContext> taskContextList = new ArrayList<>();
        List<String> missionIds = new ArrayList<>();
        for (CrEvalTrainTask task : crEvalTrainTasks) {
            missionIds.add(task.getMissionId());
        }
        if (missionIds.size() > 0) {
            List<Mission> missionList = missionMapper.batchSelect(missionIds);
            Map<String, Mission> missionMap = Maps.newHashMap();
            for (Mission mission : missionList) {
                missionMap.put(mission.getResourceId(), mission);
            }

            for (CrEvalTrainTask task : crEvalTrainTasks) {
                TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
                taskContext.setMission(missionMap.get(task.getMissionId()));
                taskContextList.add(taskContext);
            }
        }

        int getScoreObjective = 0;
        int getScoreSubjective = 0;
        Integer sumScoreObjective = 0;
        Integer sumScoreSubjective = 0;
        TrainingScore trainingScore = new TrainingScore();

        for (TaskContext task : taskContextList) {
            Mission mission = task.getMission();
            int score = task.getScore() == null ? 0 : task.getScore();
            if (mission.isObjective()) {
                getScoreObjective += score;
                sumScoreObjective += mission.getScore();
            } else {
                getScoreSubjective += score;
                sumScoreSubjective += mission.getScore();
            }

        }

        trainingScore.setSumScoreSubjective(sumScoreSubjective);
        trainingScore.setSumScoreObjective(sumScoreObjective);
        trainingScore.setGetScoreSubjective(getScoreSubjective);
        trainingScore.setGetScoreObjective(getScoreObjective);

        CrEvalScore evalScore = crEvalScoreMapper.getByRoomIdAndUser(resource_id, user_hash);
        StudentScoreRsp studentScoreRsp = new StudentScoreRsp();
        if (evalScore != null) {
            studentScoreRsp = BeanUtils.copy(evalScore, StudentScoreRsp.class);
            studentScoreRsp.setAbilityScore(
                    JsonUtil.toJsonObjectArray(evalScore.getSkillScore(), Integer.class));
            studentScoreRsp.setAbilityPercentage(evalScore.getSkillPercentage());
        }
        studentScoreRsp.setTrainingScore(trainingScore);

        return studentScoreRsp;
    }

    @Override
    public void submitEvaluation(EvalSubmitEvaluationReq req, String currentUserId) {
        // check permission??
        CrEvalTaskSubmit crEvalTaskSubmit = crEvalTaskSubmitMapper.selectById(req.getSubmitId());
        if (crEvalTaskSubmit == null) {
            throw new BusinessException(CrBusinessErrorCode.MISSION_SUBMIT_NOT_FOUND);
        }
        if (crEvalTaskSubmit.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
            throw new BusinessException(CrBusinessErrorCode.TASK_REJECTED);
        }

        CrEvalTrainTask crEvalTrainTask = evalTrainTaskMapper.selectById(crEvalTaskSubmit.getTaskId());
        if (crEvalTrainTask == null) {
            log.error("task does not exist！roleId=[{}]", crEvalTaskSubmit.getTaskId());
            throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
        }

        Integer score = req.getScore();
        Integer evaluateStatus = req.getEvaluateStatus();
        if (Objects.isNull(req.getScoreResult())) {
            if (req.getScore() == null) {
                score = 0;
            }
            // 校验分数
            if (crEvalTaskSubmit.getMissionScore() < score || score < 0) {
                throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
            }
        } else {
            score = crEvalTaskSubmit.getMissionScore() * req.getScoreResult() / 100;
            evaluateStatus = req.getScoreResult() >= 60 ? CrEvalTaskSubmit.EvaluateStatus.SUCCESS.getCode() : CrEvalTaskSubmit.EvaluateStatus.FAIL.getCode();
        }
        crEvalTaskSubmit.setScore(score);
        crEvalTaskSubmit.setDetail(req.getDetail());
        crEvalTaskSubmit.setReason(req.getReason());
        crEvalTaskSubmit.setEvaluateStatus(evaluateStatus);
        crEvalTaskSubmit.setEvaluatorId(currentUserId);
        crEvalTaskSubmit.setEvaluateTime(new Date());

        crEvalTrainTask.setStatus(evaluateStatus);
        crEvalTrainTask.setScore(score);

        CrTaskLog evaluationLog = CrTaskLog.builder().roomId(crEvalTaskSubmit.getRoomId())
                .missionId(crEvalTrainTask.getMissionId())
                .actionCode(evaluateStatusToActionCode.get(evaluateStatus)).userId(currentUserId)
                .build();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalTrainTaskMapper.updateById(crEvalTrainTask);
                crEvalTaskSubmitMapper.updateById(crEvalTaskSubmit);
                evaluationLog.setTaskId(crEvalTrainTask.getResourceId());
            }
        });

    }

    @Override
    public void updateStudentScore(EvalStudentScoreReq studentScoreReq) {
        Integer trainingPercentage = Integer
                .parseInt(studentScoreReq.getTrainingPercentage().replace("%", ""));
        if (trainingPercentage < CrScore.ScoreValue.MIN_PERCENTAGE.getCode()
                || trainingPercentage > CrScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
            log.error("trainingPercentage out of range！value=[{}]", trainingPercentage);
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
        }
        Integer abilityPercentage = Integer
                .parseInt(studentScoreReq.getAbilityPercentage().replace("%", ""));
        if (abilityPercentage < CrScore.ScoreValue.MIN_PERCENTAGE.getCode()
                || abilityPercentage > CrScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
            log.error("abilityPercentage out of range！value=[{}]", abilityPercentage);
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
        }
        if (abilityPercentage + trainingPercentage != CrScore.ScoreValue.SUM_OF_PERCENTAGE_INT
                .getCode()) {
            log.error("sumPercentage is wrong！value=[{}],sum=[{}]",
                    abilityPercentage + trainingPercentage,
                    CrScore.ScoreValue.SUM_OF_PERCENTAGE_INT.getCode());
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);

        }

        List<Integer> abilityScoreList = JsonUtil
                .toJsonObjectArray(studentScoreReq.getAbilityScore(), Integer.class);

        if (abilityScoreList.size() != CrScore.ScoreValue.ABILITY_SCORE_NUM.getCode()) {
            log.error("abilityScoreListSize is wrong！value=[{}],size=[{}]", abilityScoreList.size(),
                    CrScore.ScoreValue.ABILITY_SCORE_NUM.getCode());
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
        }
        IntSummaryStatistics stats = abilityScoreList.stream().mapToInt((x) -> x)
                .summaryStatistics();
        if (stats.getMax() > CrScore.ScoreValue.MAX_ABILITY_SCORE_VALUE.getCode()
                || stats.getMin() < CrScore.ScoreValue.MIN_ABILITY_SCORE_VALUE.getCode()) {
            log.error("abilityScore out of range！maxValue=[{}],minValue=[{}]", stats.getMax(),
                    stats.getMin());
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
        }

        CrEvalScore oldScore = crEvalScoreMapper.getByRoomIdAndUser(studentScoreReq.getEvalTemplateId(), studentScoreReq.getUser());
        CrEvalScore newCrScore = getTeamScoreFromReq(studentScoreReq);

        if (oldScore == null) {
            crEvalScoreMapper.insert(newCrScore);
        } else {
            newCrScore.setResourceId(oldScore.getResourceId());
            crEvalScoreMapper.updateById(newCrScore);
        }
    }

    private CrEvalScore getTeamScoreFromReq(EvalStudentScoreReq studentScoreReq) {
        CrEvalScore crScore = BeanUtils.copy(studentScoreReq, CrEvalScore.class);
        crScore.setRoomId(studentScoreReq.getRoomId());
        crScore.setUserId(studentScoreReq.getUser());
        crScore.setSkillScore(studentScoreReq.getAbilityScore());
        crScore.setSkillPercentage(studentScoreReq.getAbilityPercentage());
        crScore.setEvalTemplateId(studentScoreReq.getEvalTemplateId());
        return crScore;
    }

    @Override
    public JSONObject getRbMembersRanking(String evalTemplateId, String roomId, Integer roleType) {

        List<RankData> rankDataList = getUserScoreMissionData(evalTemplateId, roomId, roleType);

        Collections.sort(rankDataList);

        int ranking = 1;
        Integer total = 0;

        for (RankData rankData : rankDataList) {
            rankData.setRanking(ranking++);
            total += rankData.getScore();
        }

        JSONObject json = new JSONObject();
        json.put("ranking_data", rankDataList);
        json.put("time_stamp", System.currentTimeMillis() / 1000);
        json.put("total_score", total);

        return json;
    }


    @Override
    public JSONObject getRbLeaderBoard(String evalTemplateId, String roomId, Integer roleType) {

        List<RankData> rankDataList = getUserScoreMissionData(evalTemplateId, roomId, roleType);
        Collections.sort(rankDataList);
        int ranking = 1;
        List<RankData> missionDataList = Lists.newArrayList();
        for (RankData rankData : rankDataList) {
            rankData.setRanking(ranking++);
            RankData missionData = BeanUtils.deepCopy(rankData, RankData.class);
            missionData.setSortType("missionRatio");
            missionDataList.add(missionData);
        }
        Collections.sort(missionDataList);
        ranking = 1;
        for (RankData missionData : missionDataList) {
            missionData.setRanking(ranking++);
        }
        JSONObject json = new JSONObject();
        json.put("ranking_data", rankDataList);
        json.put("time_stamp", System.currentTimeMillis() / 1000);
        json.put("mission_data", missionDataList);
        return json;
    }

    private List<TaskContext> getTaskContext(List<CrEvalTrainTask> taskList) {
        List<TaskContext> taskContextList = new ArrayList<>();

        List<String> missionIds = new ArrayList<>();

        for (CrEvalTrainTask task : taskList) {
            missionIds.add(task.getMissionId());
        }
        if (missionIds.size() > 0) {
            List<Mission> missionList = missionMapper.batchSelect(missionIds);
            Map<String, Mission> missionMap = Maps.newHashMap();
            for (Mission mission : missionList) {
                missionMap.put(mission.getResourceId(), mission);
            }

            for (CrEvalTrainTask task : taskList) {
                TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
                taskContext.setMission(missionMap.get(task.getMissionId()));
                taskContextList.add(taskContext);
            }
        }
        return taskContextList;
    }

    private List<RankData> getUserScoreMissionData(String evalTemplateId, String roomId, Integer roleType) {

        // 获取角色组数据
        RoleQuery roleQuery = new RoleQuery(roomId, roleType);
        List<CrRole> studentRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

        Map<String, UserCounter> userCounterHashMap = Maps.newHashMap();
        // 获取角色组用户
        List<String> userIds = Lists.newArrayList();
        for (CrRole crRole : studentRoles) {
            List<String> users = crRole.getUserIdList();
            userIds.addAll(users);

            // 获取用户
            for (String userId : users) {
                UserCounter userCounter = userCounterHashMap.getOrDefault(userId, new UserCounter());
                userCounter.setRoleId(crRole.getResourceId());
                userCounterHashMap.put(userId, userCounter);
            }
        }

        List<CrEvalTrainTask> finishedTasks = evalTrainTaskMapper.selectByRoomIdAndUserIdsAndStatusList(evalTemplateId, userIds, CrTask.TaskStatus.FINISHED_STATUS_LIST);

        for (CrEvalTrainTask crTask : finishedTasks) {
            String submitUser = crTask.getUserId();
            // 获取用户每一个已完成的任务
            UserCounter userCounter = userCounterHashMap.getOrDefault(submitUser, new UserCounter());
            // 更新用户的分数和完成任务数量
            userCounter.getRemoteMissionIds().add(crTask.getMissionId());
            userCounter.setScore(userCounter.getScore() + crTask.getScore());
            userCounter.setRoleId(crTask.getRoleId());
            userCounterHashMap.put(submitUser, userCounter);
        }

        List<RankData> rankDataList = Lists.newArrayList();
        Map<String, CrRole> rolesMap = StreamApiUtils.toMap(studentRoles, CrRole::getResourceId, pc -> pc);
        Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
        for (String key : userCounterHashMap.keySet()) {
            if (!userInfoMap.containsKey(key)) {
                continue;
            }
            UserCounter userCounter = userCounterHashMap.get(key);
            Integer roleMissionCount = crRoleService.getMissionCount(rolesMap.get(userCounter.getRoleId()));
            Integer missionRatio = roleMissionCount == 0 ? 0 : userCounter.getRemoteMissionIds().size() * 100 / roleMissionCount;
            RankData rankData = new RankData();
            rankData.setName(userInfoMap.get(key).getName());
            rankData.setLogo(userInfoMap.get(key).getAvatarUrl());
            rankData.setScore(userCounter.getScore());
            rankData.setCompletedMission(userCounter.getRemoteMissionIds().size());
            rankData.setMissionRatio(missionRatio);
            rankDataList.add(rankData);
        }
        return rankDataList;
    }


    @Override
    public RbSituationScoreRsp getTeamScore(String evalTemplateId, String roomId, Integer roleType) {
        checkRole(roleType);

        Map<String, Mission> missionMap = missionService.batchGetByRoom(roomId).stream().collect(Collectors.toMap(MissionBase::getResourceId, mission -> mission));

        List<CrRole> crRoleList = crRoleService.selectRbStudentsRoleByRoomIdAndRoleGroup(roomId, roleType);

        LambdaQueryWrapper<CrEvalTrainTask> taskQuery = new LambdaQueryWrapper<>();
        taskQuery.in(CrEvalTrainTask::getRoleId, crRoleList.stream().map(CrRole::getResourceId).collect(Collectors.toList()));
        taskQuery.eq(CrEvalTrainTask::getEvalTemplateId, evalTemplateId);
        List<CrEvalTrainTask> crTaskList = evalTrainTaskMapper.selectList(taskQuery);
        crTaskList.sort(Comparator.comparing((CrEvalTrainTask crTask) -> crTask.getScore() == null ? 0 : crTask.getScore()).reversed());

        Set<String> tmpFilter = Sets.newHashSet();
        crTaskList = crTaskList.stream().filter(crTask -> tmpFilter.add(crTask.getMissionId())).collect(Collectors.toList());

        Map<String, RbSituationScoreRsp.RoleScoreInfo> roleScoreInfoMap = crRoleList.stream().collect(Collectors.toMap(CrRole::getResourceId, crRole -> {
            RbSituationScoreRsp.RoleScoreInfo roleScoreInfo = new RbSituationScoreRsp.RoleScoreInfo();
            roleScoreInfo.setRoleId(crRole.getResourceId());
            roleScoreInfo.setRoleName(crRole.getName());
            roleScoreInfo.setScore(0);
            return roleScoreInfo;
        }));

        Integer score = 0;
        Integer residualScore = 0;
        for (CrEvalTrainTask crTask : crTaskList) {
            if (crTask.isFinished()) {
                Mission mission = missionMap.get(crTask.getMissionId());
                if (!crTask.getScore().equals(mission.getScore())) {
                    int diffScore = mission.getScore() - crTask.getScore();
                    residualScore += diffScore;
                }
                score += crTask.getScore();
                RbSituationScoreRsp.RoleScoreInfo roleScoreInfo = roleScoreInfoMap.get(crTask.getRoleId());
                roleScoreInfo.setScore(roleScoreInfo.getScore() + crTask.getScore());

            } else {
                residualScore += missionMap.get(crTask.getMissionId()).getScore();
            }
        }

        RbSituationScoreRsp rbSituationScoreRsp = new RbSituationScoreRsp();
        rbSituationScoreRsp.setScore(score);
        rbSituationScoreRsp.setResidualScore(residualScore);

        rbSituationScoreRsp.setRoleScoreInfoList(
                roleScoreInfoMap.values().stream().peek(
                        roleScoreInfo -> roleScoreInfo.setPercentage(
                                roleScoreInfo.getScore() == 0
                                        ? (double) 0 : (double) roleScoreInfo.getScore() / (double) rbSituationScoreRsp.getScore() * 100
                        )
                ).collect(Collectors.toList())
        );

        return rbSituationScoreRsp;
    }

    private void checkRole(Integer roleGroup) {
        if (!CrRole.RbRoleGroup.isRvbStudentRole(roleGroup)) {
            throw new BusinessException(CrBusinessErrorCode.INVALID_ROLE_INFO);
        }
    }

    @Override
    public JSONObject getRbMissionEvaluation(String evalTemplateId, String roomId, Integer roleType) {

        RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

        Integer unreportedCount = crTaskService.countUnreportedByUsers(roomId,
                roleUserIds.getUserIds());
        // 获取任务状态数据
        List<CrEvalTrainTask> taskList = getEvalTrainTaskList(evalTemplateId, roleUserIds.getUserIds());
        List<TaskContext> crTaskSubmits = getTaskContext(taskList);
        Integer waitCount = 0;
        Integer successCount = 0;
        Integer failCount = 0;
        Integer rejectCount = 0;

        for (TaskContext taskSubmit : crTaskSubmits) {
            if (CrTaskSubmit.EvaluateStatus.SUCCESS.getCode().equals(taskSubmit.getStatus())) {
                successCount++;
            } else if (CrTaskSubmit.EvaluateStatus.FAIL.getCode().equals(taskSubmit.getStatus())) {
                failCount++;
            } else if (CrTaskSubmit.EvaluateStatus.REJECT.getCode() .equals(taskSubmit.getStatus())) {
                rejectCount++;
            } else if (CrTaskSubmit.EvaluateStatus.WAIT.getCode().equals(taskSubmit.getStatus())) {
                waitCount++;
            }
        }

        JSONObject json = new JSONObject();
        json.put("total", taskList.size());
        json.put("wait", waitCount);
        json.put("success", successCount);
        json.put("fail", failCount);
        json.put("reject", rejectCount);
        json.put("unreported", unreportedCount);
        return json;
    }


    @Override
    public JSONObject getRbMissionProgress(String evalTemplateId, String roomId, Integer roleType) {

        // 获取角色组数据
        RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

        // 获取实例实际开始时间和理论结束时间
        TimeData timeData = rbRoomScoreService.getRbTimeData(roomId);

        // 一次取出所有用户的得分纪录
        List<CrEvalTrainTask> crTasks = getEvalTrainTaskList(evalTemplateId, roleUserIds.getUserIds());

        List<ObjWithTime> taskTimes = crTasks.stream().map(taskContext -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(taskContext.getReleaseTime());

            return objWithTime;
        }).collect(Collectors.toList());

        List<Integer> totalProgressYData = timeData.calculateCountByTime(taskTimes);

        // 获取已完成的任务
        LambdaQueryWrapper<CrEvalTrainTask> finishQuery = new LambdaQueryWrapper<>();
        finishQuery.eq(CrEvalTrainTask::getEvalTemplateId, evalTemplateId);
        finishQuery.in(CrEvalTrainTask::getUserId,  roleUserIds.getUserIds());
        finishQuery.eq(CrEvalTrainTask::getStatus, CrTask.TaskStatus.FINISHED_STATUS_LIST);
        List<CrEvalTrainTask> finishedTasks =   evalTrainTaskMapper.selectList(finishQuery);
        List<ObjWithTime> finishedTimes = finishedTasks.stream().map(crTask -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(crTask.getLastSubmitTime());

            return objWithTime;
        }).collect(Collectors.toList());

        List<Integer> completedProgressYData = timeData.calculateCountByTime(finishedTimes);

        JSONObject json = new JSONObject();

        json.put("total_mission", crTasks.size());
        json.put("completed_mission", finishedTasks.size());
        json.put("total_members", roleUserIds.getUserIds().size());
        json.put("x_data", timeData.getXData());
        json.put("y_total", totalProgressYData);
        json.put("y_complete", completedProgressYData);

        return json;
    }

    @Override
    public JSONObject getAllRbScoreProgress(String roomId, Integer roleType, String template) {

        // 获取实例实际开始时间和理论结束时间
        TimeData timeData = rbRoomScoreService.getRbTimeData(roomId);

        JSONObject json = new JSONObject();

        json.put("status", timeData.getStatus());
        json.put("time_stamp", System.currentTimeMillis() / 1000);
        json.put("start_time", timeData.getStartTime());
        json.put("running", timeData.getRunning());
        json.put("total_score", 0);
        json.put("x_data", timeData.getXData());
        RoleUserIds roleUserIds = getRoleInfo(new RoleQuery(roomId, CrRole.RbRoleGroup.RED));

        List<Integer> yRedData = getScoreProgress(timeData, template, roleUserIds);

        json.put("red_total_score", timeData.getAccumulatedValue());
        json.put("y_red_score", yRedData);

        roleUserIds = getRoleInfo(new RoleQuery(roomId, CrRole.RbRoleGroup.BLUE));
        List<Integer> yBlueData = getScoreProgress(timeData, template, roleUserIds);

        json.put("blue_total_score", timeData.getAccumulatedValue());
        json.put("y_blue_score", yBlueData);
        return json;
    }

    private List<CrEvalTrainTask> getEvalTrainTaskList(String evalTemplateId,List<String> ids){
        LambdaQueryWrapper<CrEvalTrainTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CrEvalTrainTask::getEvalTemplateId, evalTemplateId);
        queryWrapper.in(CrEvalTrainTask::getUserId, ids);
        return evalTrainTaskMapper.selectList(queryWrapper);
    }

}
