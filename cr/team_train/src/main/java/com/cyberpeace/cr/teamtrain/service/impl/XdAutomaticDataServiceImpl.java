package com.cyberpeace.cr.teamtrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.teamtrain.dao.entity.CourseLessonUserRelation;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.dao.mapper.CourseLessonUserRelationMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.XdDemoDataMapper;
import com.cyberpeace.cr.teamtrain.service.XdAutomaticDataService;
import com.cyberpeace.cr.teamtrain.service.context.RoleListContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/** 按年度、实验模板和学员补齐演示记录，不创建运行场景。 */
@Service
@Slf4j
public class XdAutomaticDataServiceImpl implements XdAutomaticDataService {

    @Autowired
    private XdDemoDataMapper demoDataMapper;
    @Autowired
    private TeamRoomMapper teamRoomMapper;
    @Autowired
    private CrRoleMapper crRoleMapper;
    @Autowired
    private MissionMapper missionMapper;
    @Autowired
    private MissionTemplateMapper missionTemplateMapper;
    @Autowired
    private CrTaskMapper crTaskMapper;
    @Autowired
    private CrTaskSubmitMapper crTaskSubmitMapper;
    @Autowired
    private CrTaskLogMapper crTaskLogMapper;
    @Autowired
    private CrScoreMapper crScoreMapper;
    @Autowired
    private TrainRecordMapper trainRecordMapper;
    @Autowired
    private CourseLessonUserRelationMapper courseLessonUserRelationMapper;
    @Autowired
    private RemoteTopoService remoteTopoService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Value("${TZ:Asia/Shanghai}")
    private String timeZone;

    private final Random random = new Random();

    @Override
    @Scheduled(cron = "0 10 0 * * ?", zone = "${TZ:Asia/Shanghai}")
    public void automatic() {
        String enabled = ConfigUtil.getContextProperty("ojj.demoDataEnabled");
        if (enabled != null && !Boolean.parseBoolean(enabled)) {
            return;
        }
        generate(LocalDate.now(ZoneId.of(timeZone)).minusDays(1));
    }

    void generate(LocalDate cutoff) {
        if (cutoff.getYear() < 2026) {
            return;
        }
        List<String> teacherIds = remoteUserService.getUserIdsByRoleName("教员");
        List<String> studentIds = remoteUserService.getUserIdsByRoleName("学员");
        Set<String> allIds = new HashSet<>(teacherIds);
        allIds.addAll(studentIds);
        Map<String, UserInfo> users = remoteUserService.getIdUserInfoMap(new ArrayList<>(allIds));
        List<String> students = studentIds.stream().distinct().filter(users::containsKey).collect(Collectors.toList());
        if (students.isEmpty()) {
            log.info("No active students for demo data");
            return;
        }
        Map<String, String> teachers = new HashMap<>();
        for (String name : Arrays.asList("shenjuan", "liuwenyan")) {
            List<String> matches = teacherIds.stream().distinct().filter(users::containsKey)
                    .filter(id -> name.equals(users.get(id).getUserName())).collect(Collectors.toList());
            if (matches.size() == 1) {
                teachers.put(name, matches.get(0));
            } else {
                log.warn("Demo teacher must match exactly once, account=[{}], count=[{}]", name, matches.size());
            }
        }
        int generated = 0;
        for (TeamTemplateData template : demoDataMapper.selectTemplates()) {
            String teacher = teachers.get(template.getXdTemplateType() >= 6 ? "shenjuan" : "liuwenyan");
            if (teacher == null) {
                continue;
            }
            List<MissionStageScheme> stages = templateStages(template.getRoleConfig());
            Set<String> missionIds = stages.stream().flatMap(stage -> stage.getMissions().stream())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            if (missionIds.isEmpty()) {
                log.warn("Demo template has no student tasks, templateId=[{}]", template.getResourceId());
                continue;
            }
            List<MissionTemplate> missions = missionTemplateMapper.selectBatchIds(missionIds);
            if (missions.size() != missionIds.size()) {
                log.warn("Demo template has missing tasks, templateId=[{}]", template.getResourceId());
                continue;
            }
            TopoDigest digest = remoteTopoService.batchGetTopoDigest(Collections.singletonList(template.getTopoId()))
                    .get(template.getTopoId());
            if (digest == null) {
                log.warn("Demo template has no topology digest, templateId=[{}]", template.getResourceId());
                continue;
            }
            for (int year = 2026; year <= cutoff.getYear(); year++) {
                Map<LocalDate, List<String>> batches = new TreeMap<>();
                for (String student : students) {
                    LocalDate date = scheduledDate(year, template.getResourceId(), student, template.getXdTemplateType());
                    if (!date.isAfter(cutoff)) {
                        batches.computeIfAbsent(date, key -> new ArrayList<>()).add(student);
                    }
                }
                for (Map.Entry<LocalDate, List<String>> batch : batches.entrySet()) {
                    generated += generateBatch(template, stages, missions, digest, teacher,
                            batch.getKey(), batch.getValue(), cutoff);
                }
            }
        }
        log.info("Demo backfill completed, cutoff=[{}], generatedRooms=[{}]", cutoff, generated);
    }

    static LocalDate scheduledDate(int year, String templateId, String userId, int type) {
        LocalDate start = LocalDate.of(year, type >= 6 ? 4 : 10, 1);
        int days = (int) ChronoUnit.DAYS.between(start, start.plusMonths(3));
        return start.plusDays(Math.floorMod(Objects.hash(year, templateId, userId), days));
    }

    private List<MissionStageScheme> templateStages(String roleConfig) {
        List<RoleListContext<AddTemplateMission>> roles = JSON.parseObject(roleConfig,
                new TypeReference<List<RoleListContext<AddTemplateMission>>>() {});
        List<MissionStageScheme> stages = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (RoleListContext<AddTemplateMission> role : roles) {
            if (role.getRoleGroup() != null && role.getRoleGroup() != 2) {
                continue;
            }
            if (role.getMissionsScheme() == null || role.getMissionsScheme().getSchemeData() == null) {
                continue;
            }
            for (MissionSchemeObject<AddTemplateMission> stage : role.getMissionsScheme().getSchemeData()) {
                List<String> ids = stage.getMissions().stream().map(AddTemplateMission::getResourceId)
                        .filter(seen::add).collect(Collectors.toList());
                if (!ids.isEmpty()) {
                    MissionStageScheme copy = new MissionStageScheme();
                    copy.setStage(stage.getStage());
                    copy.setDynamic(stage.getDynamic());
                    copy.setMissions(ids);
                    stages.add(copy);
                }
            }
        }
        return stages;
    }

    private int generateBatch(TeamTemplateData template, List<MissionStageScheme> stages,
                              List<MissionTemplate> missions, TopoDigest digest, String teacher,
                              LocalDate date, List<String> students, LocalDate cutoff) {
        return transactionTemplate.execute(status -> {
            try {
                demoDataMapper.insertRun(date);
            } catch (DuplicateKeyException e) {
                // 达梦并发首次创建日期行时，唯一键冲突后仍需加锁检查缺口。
            }
            demoDataMapper.lockRun(date);
            LocalDate windowStart = LocalDate.of(date.getYear(), template.getXdTemplateType() >= 6 ? 4 : 10, 1);
            LocalDate windowEnd = windowStart.plusMonths(3);
            if (windowEnd.isAfter(cutoff.plusDays(1))) {
                windowEnd = cutoff.plusDays(1);
            }
            List<CrTask> completedTasks = demoDataMapper.selectCompletedTasks(template.getResourceId(),
                    template.getXdTemplateType(), teacher, atStartOfDay(windowStart), atStartOfDay(windowEnd));
            Map<String, Map<String, Set<String>>> completed = new HashMap<>();
            for (CrTask task : completedTasks) {
                completed.computeIfAbsent(task.getRoomId(), key -> new HashMap<>())
                        .computeIfAbsent(task.getUserId(), key -> new HashSet<>()).add(task.getMissionTemplateId());
            }
            Set<String> missionIds = missions.stream().map(MissionTemplate::getResourceId).collect(Collectors.toSet());
            Set<String> finished = new HashSet<>();
            completed.values().forEach(room -> room.forEach((user, ids) -> {
                if (ids.containsAll(missionIds)) {
                    finished.add(user);
                }
            }));
            List<String> missing = students.stream().filter(user -> !finished.contains(user)).collect(Collectors.toList());
            if (missing.isEmpty()) {
                status.setRollbackOnly();
                return 0;
            }
            createRoom(template, stages, missions, digest, teacher, date, missing);
            demoDataMapper.completeRun(date);
            return 1;
        });
    }

    private Date atStartOfDay(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.of(timeZone)).toInstant());
    }

    private void createRoom(TeamTemplateData template, List<MissionStageScheme> stages,
                            List<MissionTemplate> missions, TopoDigest digest, String teacher,
                            LocalDate date, List<String> students) {
        Date start = Date.from(date.atTime(9, 0).plusMinutes(random.nextInt(421))
                .atZone(ZoneId.of(timeZone)).toInstant());
        Date end = new Date(start.getTime() + 7_200_000L);
        String roomId = IDGeneratorUtil.getID();
        TeamRoom room = BeanUtils.copy(template, TeamRoom.class);
        room.setResourceId(roomId);
        room.setTeamTemplateId(template.getResourceId());
        room.setTeamTemplateName(template.getName());
        room.setName(date + " " + template.getName().substring(0, Math.min(template.getName().length(), 22))
                + " " + roomId.substring(0, 6));
        room.setXdType(template.getXdTemplateType());
        room.setTrainingStatus(2);
        room.setStartTime(start);
        room.setEndTime(end);
        room.setCreateUserId(teacher);
        room.setModifyUserId(teacher);
        room.setCopyedTopoId("");
        room.setTimeLineConfig("[]");
        room.setEnableRecording(false);
        stamp(room, start, end);
        teamRoomMapper.insert(room);
        demoDataMapper.insertRoom(roomId, null, date, JsonUtil.toJsonString(digest));

        Map<String, Mission> copies = new LinkedHashMap<>();
        for (MissionTemplate item : missions) {
            Mission mission = BeanUtils.copy(item, Mission.class);
            mission.setResourceId(IDGeneratorUtil.getID());
            mission.setMissionTemplateId(item.getResourceId());
            mission.setRoomId(roomId);
            mission.setCreateUserId(teacher);
            mission.setShowWriteup(false);
            stamp(mission, start, end);
            missionMapper.insert(mission);
            copies.put(item.getResourceId(), mission);
        }
        CrRole teacherRole = createRole(roomId, "教员", 1, Collections.singletonList(teacher), start, end);
        crRoleMapper.insert(teacherRole);
        CrRole studentRole = createRole(roomId, "学员", 2, students, start, end);
        List<MissionStageScheme> roomStages = new ArrayList<>();
        for (MissionStageScheme stage : stages) {
            MissionStageScheme copy = BeanUtils.copy(stage, MissionStageScheme.class);
            copy.setMissions(stage.getMissions().stream().map(id -> copies.get(id).getResourceId()).collect(Collectors.toList()));
            roomStages.add(copy);
        }
        studentRole.setMissionStageSchemes(roomStages);
        crRoleMapper.insert(studentRole);
        for (String student : students) {
            int finalScore = sampleScore();
            int trainingScore = 0;
            for (Mission mission : copies.values()) {
                CrTask task = new CrTask();
                task.setResourceId(IDGeneratorUtil.getID());
                task.setRoomId(roomId);
                task.setMissionId(mission.getResourceId());
                task.setMissionTemplateId(mission.getMissionTemplateId());
                task.setRoleId(studentRole.getResourceId());
                task.setUserId(student);
                task.setBusinessType(3);
                task.setStatus(CrTask.TaskStatus.SUCCESS.getCode());
                task.setScore((int) Math.round(mission.getScore() * finalScore / 100.0));
                trainingScore += task.getScore();
                task.setUserAnswer(mission.getType() <= 2 ? mission.getAnswer() : mission.getWriteUp());
                task.setReleaseTime(start);
                task.setTaskStartTime(new Date(start.getTime() + 60_000L));
                task.setLastSubmitTime(new Date(start.getTime() + 3_600_000L));
                Date evaluated = new Date(end.getTime() - 60_000L);
                stamp(task, start, evaluated);
                crTaskMapper.insert(task);
                CrTaskSubmit submit = new CrTaskSubmit();
                submit.setResourceId(IDGeneratorUtil.getID());
                submit.setRoomId(roomId);
                submit.setTaskId(task.getResourceId());
                submit.setAnswer(task.getUserAnswer());
                submit.setEvaluatorId(teacher);
                submit.setEvaluateTime(evaluated);
                submit.setEvaluateStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
                submit.setScore(task.getScore());
                submit.setMissionScore(mission.getScore());
                submit.setReason(2);
                submit.setDetail("已完成");
                stamp(submit, task.getLastSubmitTime(), evaluated);
                crTaskSubmitMapper.insert(submit);
                insertLog(task, teacher, CrTaskLog.ActionCode.RELEASE, start);
                insertLog(task, student, CrTaskLog.ActionCode.SUBMIT, task.getLastSubmitTime());
                insertLog(task, teacher, CrTaskLog.ActionCode.MAN_SUCCESS, evaluated);
            }
            CrScore score = new CrScore();
            score.setResourceId(IDGeneratorUtil.getID());
            score.setRoomId(roomId);
            score.setUserId(student);
            score.setFinalScore(finalScore);
            score.setTrainingScore(trainingScore);
            score.setTrainingPercentage("30");
            score.setSkillPercentage("70");
            int abilityTotal = (int) Math.round(finalScore / 4.0);
            List<Integer> abilities = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                abilities.add(abilityTotal / 5 + (i < abilityTotal % 5 ? 1 : 0));
            }
            score.setSkillScore(JsonUtil.toJsonString(abilities));
            stamp(score, end, end);
            crScoreMapper.insert(score);
        }
        TrainRecord record = new TrainRecord();
        record.setRoomId(roomId);
        record.setRoomName(room.getName());
        record.setBusinessType(3);
        record.setBusinessTemplateId(template.getBusinessTemplateId());
        record.setUserId(teacher);
        record.setStartTime(start);
        record.setEndTime(end);
        trainRecordMapper.insert(record);
        for (CourseLessonUserRelation relation : demoDataMapper.selectCourseRelations(template.getResourceId())) {
            if (students.contains(relation.getUserId())) {
                relation.setResourceId(IDGeneratorUtil.getID());
                relation.setLessonId(roomId);
                relation.setScoredFlag(true);
                stamp(relation, start, end);
                courseLessonUserRelationMapper.insert(relation);
            }
        }
    }

    int sampleScore() {
        return (int) Math.round(Math.max(0, Math.min(100, 75 + 10 * random.nextGaussian())));
    }

    private CrRole createRole(String roomId, String name, int group, List<String> users, Date start, Date end) {
        CrRole role = new CrRole(name, group);
        role.setResourceId(IDGeneratorUtil.getID());
        role.setRoomId(roomId);
        role.setBusinessType(3);
        role.setUserIdList(users);
        role.setNodes("{\"assetNodes\":[],\"visibleNodes\":[]}");
        role.setNodesSnapshot(role.getNodes());
        role.setMissionStageSchemes(Collections.emptyList());
        stamp(role, start, end);
        return role;
    }

    private void insertLog(CrTask task, String user, CrTaskLog.ActionCode action, Date time) {
        CrTaskLog entry = new CrTaskLog();
        entry.setResourceId(IDGeneratorUtil.getID());
        entry.setRoomId(task.getRoomId());
        entry.setTaskId(task.getResourceId());
        entry.setMissionId(task.getMissionId());
        entry.setUserId(user);
        entry.setActionCode(action.getCode());
        stamp(entry, time, time);
        crTaskLogMapper.insert(entry);
    }

    private void stamp(BaseTable record, Date created, Date updated) {
        record.setId(null);
        record.setCreateTime(created);
        record.setUpdateTime(updated);
    }
}
