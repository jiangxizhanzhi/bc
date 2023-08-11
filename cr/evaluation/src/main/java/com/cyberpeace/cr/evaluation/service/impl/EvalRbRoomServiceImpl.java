package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagQuery;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrFlagTask;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.dao.mapper.CrFlagTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TrainingScore;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskSubmit;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTaskSubmitMapper;
import com.cyberpeace.cr.evaluation.service.EvalRbRoomService;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalRbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements EvalRbRoomService {

    @Autowired
    private CrTaskService crTaskService;

    @Autowired
    private CrScoreService crScoreService;

    @Autowired
    private CrEvalTaskSubmitMapper crEvalTaskSubmitMapper;

    @Autowired
    private CrFlagTaskMapper crFlagTaskMapper;

    @Autowired
    private MissionMapper missionMapper;

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
        return null;
    }

    @Override
    public StudentScoreRsp getStudentScore(String resource_id, String user_hash) {

        List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(resource_id, user_hash);

        Integer getScoreObjective = 0;

        Integer getScoreSubjective = 0;

        Integer sumScoreObjective = 0;

        Integer sumScoreSubjective = 0;

        TrainingScore trainingScore = new TrainingScore();

        for (TaskContext task : taskList) {
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

        CrScore crScore = crScoreService.getByRoomIdAndUser(resource_id, user_hash);
        StudentScoreRsp studentScoreRsp = new StudentScoreRsp();
        if (crScore != null) {
            studentScoreRsp = BeanUtils.copy(crScore, StudentScoreRsp.class);
            studentScoreRsp.setAbilityScore(
                    JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class));
            studentScoreRsp.setAbilityPercentage(crScore.getSkillPercentage());
        }

        studentScoreRsp.setTrainingScore(trainingScore);

        return studentScoreRsp;
    }
}
