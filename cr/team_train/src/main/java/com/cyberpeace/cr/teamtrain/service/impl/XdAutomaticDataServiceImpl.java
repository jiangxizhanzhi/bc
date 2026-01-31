package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.TeamRoleGroup;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.req.*;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.TrainTaskReleaseInfoRsp;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionRsp;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTemplateListReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRoleRes;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTrainTemplateRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.CourseLessonUserRelation;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.dao.mapper.CourseLessonUserRelationMapper;
import com.cyberpeace.cr.teamtrain.service.*;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class XdAutomaticDataServiceImpl implements XdAutomaticDataService {

    /**
     * 团队模板Service
     */
    @Autowired
    TeamTemplateService teamTemplateService;

    /**
     * 远程拓扑Service
     */
    @Autowired
    private RemoteTopoService remoteTopoService;

    /**
     * RTKS相关Service
     */
    @Autowired
    private RtksRelatedService rtksRelatedService;

    @Autowired
    private RemoteUserService remoteUserService;


    /**
     * 团队房间Service
     */
    @Autowired
    private TeamRoomService teamRoomService;

    /**
     * 靶场岗位Service
     */
    @Autowired
    private CrRoleService crRoleService;

    /**
     * 任务Service
     */
    @Autowired
    private MissionService missionService;

    /**
     * 靶场任务Service
     */
    @Autowired
    private CrTaskService crTaskService;

    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 团队教员Service
     */
    @Autowired
    private TeamTeacherService teamTeacherService;
    /**
     * 靶场得分Controller
     */
    @Autowired
    private CrScoreController crScoreController;

    @Autowired
    private CourseLessonUserRelationMapper courseLessonUserRelationMapper;

    /**
     * 每周二周四上午十点执行造假数据
     */
    @Scheduled(cron = "0 0 10 ? * MON,WED,FRI")
    public void automatic() {
        log.info("XdAutomaticDataServiceImpl.automatic begin.");
        LocalDate currentDate = LocalDate.now();
        // 获取月份（1-12）
        int monthValue = currentDate.getMonthValue();
        log.info("monthValue:" + monthValue);
        if (monthValue != 4 && monthValue != 5 && monthValue != 6 && monthValue != 11 && monthValue != 12) {
            log.info("该月份无需执行假数据");
            return;
        }
        //老师列表
//        List<String> laoshiList = remoteUserService.getUserIdsByRoleName("教员");
//        log.info("laoshiList:{}", laoshiList);
//        if (CollectionUtils.isEmpty(laoshiList)) {
//            log.info("laoshiList is empty");
//            return;
//        }
        List<String> laoshiList = new ArrayList<>();
        laoshiList.add("ddfe9c1af2ab46ce94b8a06c80b01d69");
        laoshiList.add("b6ca56794e604f52880da29a68a5e923");
        laoshiList.add("3641396de9994051910ac8574aac31d5");

        //学员列表
        List<String> xueshengList = remoteUserService.getUserIdsByRoleName("学员");
        log.info("xueshengList:{}", xueshengList);
        if (CollectionUtils.isEmpty(xueshengList)) {
            log.info("xueshengList is empty");
            return;
        }
        //老师随机挑选一个
        int randomIndex = ThreadLocalRandom.current().nextInt(laoshiList.size());
        String laoshiId = laoshiList.get(randomIndex);
//        laoshiId = "3f655d87112f4ae094bbaf70830e20aa";
        //查询该老师的模版列表
        TeamTemplateListReq listReq = new TeamTemplateListReq();
        listReq.setPage(1);
        listReq.setPer_page(100);
        BaseRsp<IPage<TeamTrainTemplateRsp>> templateListRsp = listTemplate(listReq, laoshiId);
        log.info("templateListRsp:{}", templateListRsp);
        if (CollectionUtils.isEmpty(templateListRsp.getData().getRecords())) {
            log.info("templateListRsp is empty");
            return;
        }
        List<TeamTrainTemplateRsp> templateDataListAll = templateListRsp.getData().getRecords();
        if (CollectionUtils.isEmpty(templateDataListAll)) {
            log.info("templateDataListAll is empty");
            return;
        }
        List<TeamTrainTemplateRsp> templateDataList = new ArrayList<>();
        for (TeamTrainTemplateRsp temp : templateDataListAll) {
            if (monthValue == 4 || monthValue == 5 || monthValue == 6) {
                if (temp.getXdTemplateType() == 6 || temp.getXdTemplateType() == 7) {
                    templateDataList.add(temp);
                }
            } else if (monthValue == 11 || monthValue == 12) {
                if (temp.getXdTemplateType() == 1 || temp.getXdTemplateType() == 2 || temp.getXdTemplateType() == 3 || temp.getXdTemplateType() == 4 || temp.getXdTemplateType() == 5) {
                    templateDataList.add(temp);
                }
            }
        }
        log.info("templateDataListAll.size:" +  templateDataListAll.size());
        log.info("templateDataList.size:" +  templateDataList.size());
        if (CollectionUtils.isEmpty(templateDataList)) {
            log.info("templateDataList is empty");
            return;
        }
        //随机挑选一个模板
        int randomIndexTemplate = ThreadLocalRandom.current().nextInt(templateDataList.size());
        TeamTrainTemplateRsp teamTrainTemplateRsp = templateDataList.get(randomIndexTemplate);
//        TeamTrainTemplateRsp teamTrainTemplateRsp = null;
//        for (TeamTrainTemplateRsp temp : templateDataList) {
//            if ("111111111".equals(temp.getBaseInfo().getName())) {
//                teamTrainTemplateRsp = temp;
//            }
//        }
        if (teamTrainTemplateRsp == null) {
            log.info("teamTrainTemplateRsp is empty");
            return;
        }
        log.info("teamTrainTemplateRsp:{}", teamTrainTemplateRsp);
        //创建实验或科目
        TeamTrainRoomReq teamTrainRoomReq = new TeamTrainRoomReq();
        teamTrainRoomReq.setTemplate(teamTrainTemplateRsp.getResourceId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        teamTrainRoomReq.setName(teamTrainTemplateRsp.getBaseInfo().getName() + sdf.format(now));
        teamTrainRoomReq.setStartTime(now);
        teamTrainRoomReq.setEndTime(Date.from(now.toInstant().plusSeconds(2 * 60 * 60)));
        teamTrainRoomReq.setCategory(1);
        teamTrainRoomReq.setLogo(teamTrainTemplateRsp.getBaseInfo().getLogo());
        teamTrainRoomReq.setPreSkill("[\"" + String.join("\",\"", teamTrainTemplateRsp.getBaseInfo().getPreSkill()) + "\"]");
        teamTrainRoomReq.setTrainSkill("[\"" + String.join("\",\"", teamTrainTemplateRsp.getBaseInfo().getTrainSkill()) + "\"]");
        teamTrainRoomReq.setDifficult(1);
        teamTrainRoomReq.setDescription(teamTrainRoomReq.getName());
        teamTrainRoomReq.setEnableRecording(true);
        if (teamTrainTemplateRsp.getXdTemplateCategory() != 1 && teamTrainTemplateRsp.getXdTemplateCategory() != 2) {
            log.info("teamTrainTemplateRsp.getXdTemplateCategory():{}", teamTrainTemplateRsp.getXdTemplateCategory());
            return;
        }
        if (teamTrainTemplateRsp.getXdTemplateType() != 1
                && teamTrainTemplateRsp.getXdTemplateType() != 2
                && teamTrainTemplateRsp.getXdTemplateType() != 3
                && teamTrainTemplateRsp.getXdTemplateType() != 4
                && teamTrainTemplateRsp.getXdTemplateType() != 5
                && teamTrainTemplateRsp.getXdTemplateType() != 6
                && teamTrainTemplateRsp.getXdTemplateType() != 7) {
            log.info("teamTrainTemplateRsp.getXdTemplateType():{}", teamTrainTemplateRsp.getXdTemplateType());
            return;
        }
        teamTrainRoomReq.setXdType(teamTrainTemplateRsp.getXdTemplateType() + (teamTrainTemplateRsp.getXdTemplateCategory()-1)*7);
        List<TeamTrainRoomReq.RoleConfig> roleConfigs = new ArrayList<>();
        TeamTrainRoomReq.RoleConfig laoshiRoleConfig = new TeamTrainRoomReq.RoleConfig();
        laoshiRoleConfig.setRoleGroup(1);
        List<String> laoshiRoleList = new ArrayList<>();
        laoshiRoleList.add(laoshiId);
        laoshiRoleConfig.setUsers(laoshiRoleList);
        roleConfigs.add(laoshiRoleConfig);
        if (teamTrainTemplateRsp.getRoleStructure().size() == 2) {
            for (int i=1; i<teamTrainTemplateRsp.getRoleStructure().size(); i++) {
                TeamTrainRoomReq.RoleConfig xueshengRoleConfig = new TeamTrainRoomReq.RoleConfig();
                xueshengRoleConfig.setRoleGroup(teamTrainTemplateRsp.getRoleStructure().get(i).getRoleGroup());
                xueshengRoleConfig.setUsers(xueshengList);
                roleConfigs.add(xueshengRoleConfig);
            }
        } else if (teamTrainTemplateRsp.getRoleStructure().size() == 3) {
            TeamTrainRoomReq.RoleConfig xueshengRoleConfig = new TeamTrainRoomReq.RoleConfig();
            xueshengRoleConfig.setRoleGroup(teamTrainTemplateRsp.getRoleStructure().get(1).getRoleGroup());
            xueshengRoleConfig.setUsers(xueshengList.subList(0,xueshengList.size()-1));
            roleConfigs.add(xueshengRoleConfig);

            TeamTrainRoomReq.RoleConfig xueshengRoleConfig2 = new TeamTrainRoomReq.RoleConfig();
            xueshengRoleConfig2.setRoleGroup(teamTrainTemplateRsp.getRoleStructure().get(2).getRoleGroup());
            xueshengRoleConfig2.setUsers(xueshengList.subList(xueshengList.size()-1, xueshengList.size()));
            roleConfigs.add(xueshengRoleConfig2);
        } else {
            log.info("teamTrainTemplateRsp.getRoleStructure().size() > 3");
            return;
        }

        teamTrainRoomReq.setRoleConfig(roleConfigs);
        log.info("teamTrainRoomReq:{}", teamTrainRoomReq);
        log.info("teamTrainRoomReq.getRoleConfig:{}", teamTrainRoomReq.getRoleConfig());
        BaseRsp<TeamRoomRsp> createRsp = create(teamTrainRoomReq, laoshiId);
        log.info("createRsp:{}", createRsp);
        //启动训练
        int status = teamRoomService.changeTrainingStatus(createRsp.getData().getResourceId());
        //查询任务列表
        BaseRsp<List<TrainTaskReleaseInfoRsp>> missionListRsp = getMissionList(createRsp.getData().getResourceId());
        //批量下发任务
        List<SubmitMissionsReq> submitMissionsReqs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(missionListRsp.getData())) {
            for (TrainTaskReleaseInfoRsp temp : missionListRsp.getData()) {
                if (CollectionUtils.isNotEmpty(temp.getMissionStages())) {
                    for (MissionReleaseStageInfo missionReleaseStageInfo : temp.getMissionStages()) {
                        if (CollectionUtils.isNotEmpty(missionReleaseStageInfo.getMissionInfos())) {
                            ReleaseMissionReq releaseMissionReq = new ReleaseMissionReq();
                            List<String> missionReleaseIds =  new ArrayList<>();
                            for (MissionReleaseInfo  missionReleaseInfo : missionReleaseStageInfo.getMissionInfos()) {
                                missionReleaseIds.add(missionReleaseInfo.getResourceId());
                                for (UserData userData : temp.getUserInfoList()) {
                                    //组装提交答案的请求
                                    SubmitMissionsReq submitMissionsReq = new SubmitMissionsReq();
                                    submitMissionsReq.setUserId(userData.getResourceId());
                                    submitMissionsReq.setMissionHash(missionReleaseInfo.getResourceId());
                                    submitMissionsReq.setResourceId(missionReleaseInfo.getRoomId());
                                    if (MissionBase.MissionType.JUDGE.getCode() == missionReleaseInfo.getType()
                                            || MissionBase.MissionType.SINGLE_CHOICE.getCode() == missionReleaseInfo.getType()
                                            || MissionBase.MissionType.MULTIPLE_CHOICE.getCode() == missionReleaseInfo.getType()) {
                                        submitMissionsReq.setAnswer(missionReleaseInfo.getAnswer());
                                        submitMissionsReq.setRemark(missionReleaseInfo.getAnswer());
                                    } else {
                                        submitMissionsReq.setAnswer(missionReleaseInfo.getWriteUp());
                                        submitMissionsReq.setRemark(missionReleaseInfo.getWriteUp());
                                    }
                                    submitMissionsReqs.add(submitMissionsReq);
                                }
                            }
                            releaseMissionReq.setMissionResourceIds(missionReleaseIds);
                            releaseMissionReq.setRoleResourceId(temp.getRoleId());
                            releaseBatchMissions(releaseMissionReq, laoshiId);
                        }
                    }
                }
            }
        }
        try {
            Thread.sleep(3000);  // 等待三秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            e.printStackTrace();
        }
        //提交答案
        if (CollectionUtils.isNotEmpty(submitMissionsReqs)) {
            for (SubmitMissionsReq temp :  submitMissionsReqs) {
                changeTrainingStatus(temp, temp.getUserId());
            }
        }
        try {
            Thread.sleep(3000);  // 等待三秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            e.printStackTrace();
        }
        //查询学员待评估列表
        EvaluationListReq evaluationListReq = new EvaluationListReq();
        evaluationListReq.setRoom_id(createRsp.getData().getResourceId());
        List<EvaluationListRsp> evaluationList = teamRoomService.getEvaluationList(evaluationListReq);
        log.info("evaluationList.size():{}", evaluationList.size());
        if (CollectionUtils.isNotEmpty(evaluationList)) {
            for (EvaluationListRsp temp : evaluationList) {
                if (temp.getEvaluateStatus() == 0) {
                    //获取详情
                    EvaluationMissionRsp evaluationMissionRsp = crTaskService.getEvaluationById(temp.getResourceId());
                    SubmitEvaluationReq submitEvaluationReq = new SubmitEvaluationReq();
                    submitEvaluationReq.setRoomId(createRsp.getData().getResourceId());
                    submitEvaluationReq.setSubmitId(temp.getResourceId());
                    submitEvaluationReq.setEvaluateStatus(1);
                    submitEvaluationReq.setReason(2);
                    submitEvaluationReq.setDetail("正确");
                    submitEvaluationReq.setScore(evaluationMissionRsp.getScore() == null ? "0" : String.valueOf(evaluationMissionRsp.getScore()));
                    //提交评估
                    updateSubmitEvaluation(submitEvaluationReq, laoshiId);
                }
            }
        }
        try {
            Thread.sleep(3000);  // 等待三秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            e.printStackTrace();
        }
        //获取评分列表
        List<StudentScoreListRsp> res = teamTeacherService.getScoreList(createRsp.getData().getResourceId());
        //逐个评分
        if  (CollectionUtils.isNotEmpty(res)) {
            for (StudentScoreListRsp temp : res) {
                StudentScoreReq studentScoreReq = new StudentScoreReq();
                studentScoreReq.setUser(temp.getUserId());
                studentScoreReq.setRoomId(createRsp.getData().getResourceId());
                studentScoreReq.setTrainingPercentage("30");
                studentScoreReq.setAbilityPercentage("70");
                if ((int) (Math.random() * 9) + 1 == 1) {
                    studentScoreReq.setAbilityScore("[5,5,5,5,5]");
                    studentScoreReq.setFinalScore(100);
                } else if ((int) (Math.random() * 9) + 1 == 2) {
                    studentScoreReq.setAbilityScore("[5,5,5,5,4]");
                    studentScoreReq.setFinalScore(97);
                } else if ((int) (Math.random() * 9) + 1 == 3) {
                    studentScoreReq.setAbilityScore("[5,5,5,4,5]");
                    studentScoreReq.setFinalScore(97);
                } else if ((int) (Math.random() * 9) + 1 == 4) {
                    studentScoreReq.setAbilityScore("[5,5,5,4,4]");
                    studentScoreReq.setFinalScore(94);
                } else if ((int) (Math.random() * 9) + 1 == 5) {
                    studentScoreReq.setAbilityScore("[5,5,4,4,4]");
                    studentScoreReq.setFinalScore(92);
                } else if ((int) (Math.random() * 9) + 1 == 6) {
                    studentScoreReq.setAbilityScore("[5,4,4,4,4]");
                    studentScoreReq.setFinalScore(89);
                } else if ((int) (Math.random() * 9) + 1 == 7) {
                    studentScoreReq.setAbilityScore("[4,4,4,4,4]");
                    studentScoreReq.setFinalScore(86);
                } else if ((int) (Math.random() * 9) + 1 == 8) {
                    studentScoreReq.setAbilityScore("[4,4,4,4,3]");
                    studentScoreReq.setFinalScore(83);
                } else {
                    studentScoreReq.setAbilityScore("[4,4,4,3,3]");
                    studentScoreReq.setFinalScore(80);
                }
                updateStudentScore(studentScoreReq, laoshiId);
            }
        }
        long min = 2_400_000L;
        long max = 7_200_000L;
        Random random = new Random();

        // 计算范围
        long range = max - min + 1;

        // 生成 [min, max] 范围内的随机long
        long randomNumber = min + (long)(random.nextDouble() * range);
        try {
            Thread.sleep(randomNumber);  // 等待40分钟到120分钟结束训练
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            e.printStackTrace();
        }
        //结束训练
        teamRoomService.changeTrainingStatus(createRsp.getData().getResourceId());
        log.info("XdAutomaticDataServiceImpl.automatic end.");
    }

    private BaseRsp<IPage<TeamTrainTemplateRsp>> listTemplate(TeamTemplateListReq listReq, String userId) {
        ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
        listQuery.setPer_page(listReq.getPerPage());
        // 默认时间排序
        listQuery.setSort("cr_business_template.name");

        IPage<TeamTemplateData> data = teamTemplateService.page2(listQuery, userId);
        // 训练场景拓扑节点数量获取
        Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
                data.getRecords().stream().map(teamTemplateData -> teamTemplateData.getTopoId())
                        .collect(Collectors.toList()));
        // get attack engage relations
        List<String> businessTemplateIds = data.getRecords().stream()
                .map(TeamTemplateData::getBusinessTemplateId).collect(Collectors.toList());
        Map<String, List<NameCount>> attackRelCount = rtksRelatedService
                .countAttackRel(businessTemplateIds);
        Map<String, List<NameCount>> icsRelCount = rtksRelatedService
                .countIcsRel(businessTemplateIds);
        Map<String, List<NameCount>> engageRelCount = rtksRelatedService
                .countEngageRel(businessTemplateIds);

        var rsp = data.convert(teamTemplateData -> getTeamTrainTemplateOutputByBaseInfoData(teamTemplateData,
                topologyNodesStats, attackRelCount, icsRelCount, engageRelCount));

        return new BaseRsp<>(rsp);
    }

    private TeamTrainTemplateRsp getTeamTrainTemplateOutputByBaseInfoData(
            TeamTemplateData teamTemplateData, Map<String, TopoDigest> topologyNodesStats,
            Map<String, List<NameCount>> attackRelCount, Map<String, List<NameCount>> icsRelCount,
            Map<String, List<NameCount>> engageRelCount) {

        BaseInfoRsp baseInfoRsp = new BaseInfoRsp(teamTemplateData);

        // 获取角色列表
        String roleString = teamTemplateData.getRoleConfig();

        List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
                BaseRole.class);
        teamTrainTemplateRoleList
                .forEach(role -> role.setRoleGroup(TeamRoleGroup.STUDENT.getCode()));

        BaseRole baseRole = new BaseRole();
        baseRole.setName(TeamRoleGroup.TEACHER.getDesc());
        baseRole.setRoleGroup(TeamRoleGroup.TEACHER.getCode());

        teamTrainTemplateRoleList.add(0, baseRole);

        // 设置返回对象
        TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();
        teamTrainTemplateRsp.setNodesCount(
                topologyNodesStats.get(teamTemplateData.getTopoId()).getNodeCounts());
        teamTrainTemplateRsp.setResourceOccupiedNums(
                topologyNodesStats.get(teamTemplateData.getTopoId()).getResourceOccupiedNums());
        teamTrainTemplateRsp.setResourceId(teamTemplateData.getResourceId());

        teamTrainTemplateRsp.setBusinessTemplateId(teamTemplateData.getBusinessTemplateId());

        teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
        teamTrainTemplateRsp.setRoleStructure(teamTrainTemplateRoleList);

        // set attack engage relation
        teamTrainTemplateRsp.setIsAttackRelated(
                attackRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
        teamTrainTemplateRsp.setAttackCount(attackRelCount
                .getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));
        teamTrainTemplateRsp
                .setIsIcsRelated(icsRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
        teamTrainTemplateRsp.setIcsCount(icsRelCount
                .getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));
        teamTrainTemplateRsp.setIsEngageRelated(
                engageRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
        teamTrainTemplateRsp.setEngageCount(engageRelCount
                .getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));
        teamTrainTemplateRsp.setXdTemplateType(teamTemplateData.getXdTemplateType());
        teamTrainTemplateRsp.setXdTemplateCategory(teamTemplateData.getXdTemplateCategory());
        return teamTrainTemplateRsp;
    }

    private BaseRsp<TeamRoomRsp> create(TeamTrainRoomReq teamTrainRoomReq, String userId) {
        // 校验
        teamTrainRoomReq.validate();
        TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,
                TeamTrainRoomContext.class);
        List<RoomRole> roles = BeanUtils.deepCopyList(teamTrainRoomReq.getRoleConfig(),
                RoomRole.class);
        teamTrainRoomContext.setRoleConfig(roles);
        teamTrainRoomContext.setPreSkill(
                JsonUtil.toJsonObjectArray(teamTrainRoomReq.getPreSkill(), String.class));
        teamTrainRoomContext.setTrainSkill(
                JsonUtil.toJsonObjectArray(teamTrainRoomReq.getTrainSkill(), String.class));
        TeamRoom teamRoom = teamRoomService.create(teamTrainRoomContext, userId);

        return getTeamRoom(teamRoom.getResourceId(), userId);
    }

    private BaseRsp<TeamRoomRsp> getTeamRoom(String resource_id, String userId) {
        crRoleService.checkPermission(resource_id, userId);

        TeamRoomData teamRoomData = teamRoomService.getTeamRoomDataByRoomId(resource_id);

        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setRoomId(teamRoomData.getResourceId());

        List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

        TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
        teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));

        teamRoomRsp.setRolesData(getTeamRoomRoleRes(crRoles));

        teamRoomRsp
                .setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
        teamRoomRsp.setTrainSkill(
                JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));

        return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
    }

    private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
        BaseRole baseRole = new BaseRole();
        crRoles.forEach(crRole -> {
            if (crRole.containsUser(userId)) {
                baseRole.setResourceId(crRole.getResourceId());
                baseRole.setName(crRole.getName());

                baseRole.setDescription(crRole.getDescription());
                baseRole.setRoleGroup(crRole.getRoleGroup());
            }
        });

        return baseRole;
    }

    private List<TeamRoomRoleRes> getTeamRoomRoleRes(List<CrRole> crRoles) {
        List<TeamRoomRoleRes> teamRoomRoleResList = com.google.common.collect.Lists.newArrayList();
        for (CrRole crRole : crRoles) {
            TeamRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, TeamRoomRoleRes.class);
            teamRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIdList()));
            teamRoomRoleRes.setUsers(crRole.getUserIdList());

            teamRoomRoleRes.setTrainingTeam(crRole.getRoomId());
            teamRoomRoleResList.add(teamRoomRoleRes);
        }
        return teamRoomRoleResList;
    }

    private BaseRsp<List<TrainTaskReleaseInfoRsp>> getMissionList(String resource_id) {
        List<TrainTaskReleaseInfoRsp> res = crRoleService.getTrainTaskReleaseInfoList(resource_id,
                TeamRoleGroup.STUDENT.getCode());
        return new BaseRsp<>(res);
    }

    private BaseRsp<Boolean> releaseBatchMissions(ReleaseMissionReq releaseMissionReq, String userId) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(releaseMissionReq.getMissionResourceIds())) {
            return new BaseRsp<>(true);
        }
        CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());
        teamRoomService.checkTeacherPermission(crRole.getRoomId(),
                userId);
        teamRoomService.checkRoomOpening(crRole.getRoomId());
        Boolean res = missionService.batchRelease(releaseMissionReq.getMissionResourceIds(),
                releaseMissionReq.getRoleResourceId(),
                userId);
        return new BaseRsp<>(res);
    }

    private BaseRsp<String> changeTrainingStatus(SubmitMissionsReq submitMissionsReq, String userId) {
        CrCommonValidator.checkStringExist(submitMissionsReq.getAnswer(),
                CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

        teamRoomService.checkRoomOpening(submitMissionsReq.getResourceId());
//        String userId = UserInfoContext.getUserContext().getUserId();
//        teamRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                crTaskService.submitMissions(submitMissionsReq.getResourceId(),
                        submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer(),
                        submitMissionsReq.getRemark(), userId);
                teamTeacherService.notifySubmission2(submitMissionsReq.getMissionHash());
            }
        });

        return new BaseRsp<>();
    }

    public BaseRsp<String> updateSubmitEvaluation(SubmitEvaluationReq submitEvaluationReq, String userId) {
        teamRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());

        crTaskService.submitEvaluation(submitEvaluationReq.getSubmitId(),
                submitEvaluationReq.getScore(), submitEvaluationReq.getDetail(),
                submitEvaluationReq.getReason(), submitEvaluationReq.getEvaluateStatus(),
                userId);
        return new BaseRsp<>();
    }

    public BaseRsp<Void> updateStudentScore(StudentScoreReq studentScoreReq, String userId) {

        crScoreController.updateStudentScore(studentScoreReq);

        LambdaUpdateWrapper<CourseLessonUserRelation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CourseLessonUserRelation::getScoredFlag, true)
                .set(CourseLessonUserRelation::getUpdateTime, new Date())
                .eq(CourseLessonUserRelation::getUserId, userId)
                .eq(CourseLessonUserRelation::getLessonId, studentScoreReq.getRoomId());

        courseLessonUserRelationMapper.update(null, updateWrapper);

        return new BaseRsp<>();
    }

}
