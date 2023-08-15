package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagQuery;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrFlagTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TrainingScore;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.evaluation.controller.req.EvalStudentScoreReq;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskSubmit;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTrainTask;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalScoreMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTaskSubmitMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTrainTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalRbRoomService;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.context.RoleUserIds;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.mission.ObjWithTime;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
public class EvalRbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements EvalRbRoomService {

    @Autowired
    private CrScoreService crScoreService;

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

        if (CollectionUtils.isEmpty(data.getRecords())){

        }
        //获取一血, 二血, 三血及额外分数
        Map<String, MissionEvaluationContext> rankMap =CollectionUtils.isEmpty(data.getRecords())? Maps.newHashMap(): getRankContextMap(data);
        //获取动态任务对应flagValue
        LambdaQueryWrapper<CrFlagTask> flagTaskWrapper = new LambdaQueryWrapper<>();
        flagTaskWrapper.in(CrFlagTask::getMissionId, missionIds);
        List<CrFlagTask> flagTaskList = CollectionUtils.isEmpty(data.getRecords()) ? new ArrayList<>() : crFlagTaskMapper.selectList(flagTaskWrapper);
        Map<String, String> taskFlagValueMap = StreamApiUtils.toMap(flagTaskList, CrFlagTask::getTaskId, CrFlagTask::getFlagValue);

        List<Mission> missions = Lists.newArrayList();
        if (missionIds.size() > 0) {
            missions = missionMapper.batchSelect(missionIds);
        }

        Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,pc -> pc);
        data.convert(missionEvaluationContext -> {

            MissionEvaluationContext rankContext = rankMap.get(missionEvaluationContext.getResourceId());
            missionEvaluationContext.setRank(rankContext.getRank());
            missionEvaluationContext.setRankScore(rankContext.getRankScore());

            Mission mission = missionMap.get(missionEvaluationContext.getMissionHash());
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

        LambdaQueryWrapper<CrEvalTaskSubmit> rankQueryWrapper = new LambdaQueryWrapper<>();
        rankQueryWrapper.eq(CrEvalTaskSubmit::getRoomId, data.getRecords().get(0).getRoomId());
        rankQueryWrapper.orderByAsc(CrEvalTaskSubmit::getCreateTime);
        List<CrEvalTaskSubmit> crTaskSubmits = crEvalTaskSubmitMapper.selectList(rankQueryWrapper);

        Map<String, MissionEvaluationContext> rankMap = new HashMap<>();
        List<String> rankList = Arrays.asList("一血", "二血", "三血");
        List<Double> rankScoreList = Arrays.asList(0.2, 0.1, 0.05);
        for (int i = 0; i < crTaskSubmits.size(); i++) {
            CrEvalTaskSubmit crTaskSubmit = crTaskSubmits.get(i);
            MissionEvaluationContext rankContext = new MissionEvaluationContext();
            rankContext.setResourceId(crTaskSubmit.getResourceId());
            if (i < 3) {
                BigDecimal rankScore = BigDecimal.valueOf(crTaskSubmit.getMissionScore() * rankScoreList.get(i))
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                rankContext.setRankScore(Integer.parseInt(rankScore.toString()));
                rankContext.setRank(rankList.get(i));
            } else {
                rankContext.setRankScore(0);
                rankContext.setRank("否");
            }
            rankMap.put(crTaskSubmit.getResourceId(), rankContext);
        }
        return rankMap;
    }

    @Override
    public JSONObject getRbScoreProgress(String roomId, Integer roleType) {
        // 获取实例实际开始时间和理论结束时间
        TimeData timeData = rbRoomScoreService.getRbTimeData(roomId);
        RoleUserIds roleUserIds = getRoleInfo(new RoleQuery(roomId, roleType));
        List<Integer> yData = getScoreProgress(timeData, roomId, roleUserIds);

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

    private List<Integer> getScoreProgress(TimeData timeData, String roomId, RoleUserIds roleUserIds) {
        // 一次取出所有用户的得分纪录
        List<CrEvalTrainTask> crEvalTrainTasks = evalTrainTaskMapper.selectByRoomIdAndUserIdsAndStatusList(roomId, roleUserIds.getUserIds(), CrTask.TaskStatus.FINISHED_STATUS_LIST);
        List<ObjWithTime> taskTimes = crEvalTrainTasks.stream().map(taskContext -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(taskContext.getLastSubmitTime());
            objWithTime.setValue(taskContext.getScore());
            return objWithTime;
        }).collect(Collectors.toList());
        return timeData.calculateCountByTime(taskTimes);
    }

    private RoleUserIds getRoleInfo(RoleQuery roleQuery) {
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

        Integer getScoreObjective = 0;
        Integer getScoreSubjective = 0;
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
    public void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason, Integer evaluateStatus, String currentUserId) {
        // check permission??
        CrEvalTaskSubmit crEvalTaskSubmit = crEvalTaskSubmitMapper.selectById(taskSubmitId);
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

        // 驳回??
        if (score == null) {
            score = 0;
        }
        // 校验分数
        if (crEvalTaskSubmit.getMissionScore() < score || score < 0) {
            throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
        }

        crEvalTaskSubmit.setScore(score);
        crEvalTaskSubmit.setDetail(detail);
        crEvalTaskSubmit.setReason(reason);
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
    public JSONObject getRbMissionProgress(String evalTemplateId,String roomId, Integer roleType) {

        // 获取角色组数据
        RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

        // 获取实例实际开始时间和理论结束时间
        TimeData timeData = rbRoomScoreService.getRbTimeData(roomId);

        // 获取组内已下发的任务
        List<CrEvalTrainTask> crEvalTrainTasks = evalTrainTaskMapper.selectByRoomIdAndUserIds(evalTemplateId, roleUserIds.getUserIds());

        List<ObjWithTime> taskTimes = crEvalTrainTasks.stream().map(taskContext -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(taskContext.getReleaseTime());

            return objWithTime;
        }).collect(Collectors.toList());

        List<Integer> totalProgressYData = timeData.calculateCountByTime(taskTimes);

        // 获取已完成的任务
        List<CrEvalTrainTask> finishedTasks = evalTrainTaskMapper.selectByRoomIdAndUserIdsAndStatusList(roomId, roleUserIds.getUserIds(), CrTask.TaskStatus.FINISHED_STATUS_LIST);

        List<ObjWithTime> finishedTimes = finishedTasks.stream().map(crTask -> {
            ObjWithTime objWithTime = new ObjWithTime();
            objWithTime.setTime(crTask.getLastSubmitTime());
            return objWithTime;
        }).collect(Collectors.toList());

        List<Integer> completedProgressYData = timeData.calculateCountByTime(finishedTimes);

        JSONObject json = new JSONObject();

        json.put("total_mission", crEvalTrainTasks.size());
        json.put("completed_mission", finishedTasks.size());
        json.put("total_members", roleUserIds.getUserIds().size());
        json.put("x_data", timeData.getXData());
        json.put("y_total", totalProgressYData);
        json.put("y_complete", completedProgressYData);

        return json;
    }

    private RoleUserIds getRoleInfo(String roomId, Integer roleType) {
        return getRoleInfo(new RoleQuery(roomId, roleType));
    }


}
