package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TrainingScore;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.EvalRbRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EvalRbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements EvalRbRoomService {

    @Autowired
    private CrTaskService crTaskService;

    @Autowired
    private CrScoreService crScoreService;

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
