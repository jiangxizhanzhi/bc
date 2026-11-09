package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.teamtrain.dao.entity.CourseLessonUserRelation;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.CourseLessonUserRelationMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.XdDemoDataMapper;
import com.cyberpeace.cr.teamtrain.service.XdAutomaticDataService;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/** 每日复制已结束实验的业务记录，仅用于演示，不创建运行场景。 */
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

    void generate(LocalDate businessDate) {
        transactionTemplate.execute(status -> {
            // 唯一业务日期同时防止定时、手动及多实例重复写入。
            try {
                demoDataMapper.insertRun(businessDate);
            } catch (DuplicateKeyException e) {
                log.info("Demo data already generated, businessDate=[{}]", businessDate);
                return null;
            }
            List<TeamRoom> sources = new ArrayList<>(demoDataMapper.selectSources());
            int count = random.nextInt(10) < 7 ? 1 : 2;
            int generated = 0;
            while (generated < count && !sources.isEmpty()) {
                TeamRoom source = sources.get(random.nextInt(sources.size()));
                if (copySample(source, businessDate)) {
                    generated++;
                } else {
                    log.info("Skipping incomplete demo source, roomId=[{}]", source.getResourceId());
                    sources.remove(source);
                }
            }
            if (generated != count) {
                status.setRollbackOnly();
                log.warn("No valid demo source, businessDate=[{}]", businessDate);
                return null;
            }
            demoDataMapper.completeRun(businessDate, generated);
            log.info("Demo data generated, businessDate=[{}], count=[{}]", businessDate, generated);
            return null;
        });
    }

    private boolean copySample(TeamRoom source, LocalDate businessDate) {
        String sourceId = source.getResourceId();
        TrainRecord sourceRecord = trainRecordMapper.getByRoomId(sourceId);
        List<CrRole> roles = crRoleMapper.getRolesByRoomId(sourceId);
        List<Mission> missions = missionMapper.selectList(new QueryWrapper<Mission>().eq("room_id", sourceId));
        List<CrTask> tasks = crTaskMapper.selectList(new QueryWrapper<CrTask>().eq("room_id", sourceId));
        List<CrScore> scores = crScoreMapper.selectList(new QueryWrapper<CrScore>().eq("room_id", sourceId));
        List<CrTaskSubmit> submissions = crTaskSubmitMapper.selectByRoomId(sourceId);
        List<CrTaskLog> logs = crTaskLogMapper.selectList(new QueryWrapper<CrTaskLog>().eq("room_id", sourceId));
        Set<String> userIds = roles.stream().flatMap(role -> role.getUserIdList().stream()).collect(Collectors.toSet());
        Map<String, CrRole> roleMap = roles.stream().collect(Collectors.toMap(CrRole::getResourceId, role -> role));
        Set<String> missionIds = missions.stream().map(Mission::getResourceId).collect(Collectors.toSet());
        Set<String> taskIds = tasks.stream().map(CrTask::getResourceId).collect(Collectors.toSet());
        if (roles.stream().noneMatch(role -> role.isTeamTeacher() && !role.getUserIdList().isEmpty())
                || roles.stream().noneMatch(role -> role.isTeamStudent() && !role.getUserIdList().isEmpty())
                || missions.isEmpty() || tasks.isEmpty() || scores.isEmpty()
                || tasks.stream().anyMatch(task -> !missionIds.contains(task.getMissionId())
                    || !roleMap.containsKey(task.getRoleId())
                    || !roleMap.get(task.getRoleId()).containsUser(task.getUserId()))
                || scores.stream().anyMatch(score -> !userIds.contains(score.getUserId()))
                || submissions.stream().anyMatch(submit -> !taskIds.contains(submit.getTaskId()))
                || logs.stream().anyMatch(entry -> (StringUtils.isNotBlank(entry.getTaskId()) && !taskIds.contains(entry.getTaskId()))
                    || (StringUtils.isNotBlank(entry.getMissionId()) && !missionIds.contains(entry.getMissionId())))) {
            return false;
        }
        if (!remoteUserService.getIdUserInfoMap(new ArrayList<>(userIds)).keySet().containsAll(userIds)) {
            return false;
        }
        TopoDigest digest = remoteTopoService.batchGetTopoDigest(Collections.singletonList(source.getCopyedTopoId()))
                .get(source.getCopyedTopoId());
        if (digest == null) {
            return false;
        }
        Map<String, String> ids = new HashMap<>();
        ids.put(sourceId, IDGeneratorUtil.getID());
        roles.forEach(role -> ids.put(role.getResourceId(), IDGeneratorUtil.getID()));
        missions.forEach(mission -> ids.put(mission.getResourceId(), IDGeneratorUtil.getID()));
        tasks.forEach(task -> ids.put(task.getResourceId(), IDGeneratorUtil.getID()));
        // 编排中缺失的任务意味着样本不能生成完整报告。
        for (CrRole role : roles) {
            if (role.getMissionStageSchemes().stream().flatMap(stage -> stage.getMissions().stream())
                    .anyMatch(id -> !missionIds.contains(id))) {
                return false;
            }
        }
        Date oldStart = sourceRecord.getStartTime();
        long oldDuration = sourceRecord.getEndTime().getTime() - oldStart.getTime();
        Date start = Date.from(businessDate.atTime(9, 0).plusMinutes(random.nextInt(421))
                .atZone(ZoneId.of(timeZone)).toInstant());
        Date end = new Date(start.getTime() + 7_200_000L);
        String roomId = ids.get(sourceId);
        TeamRoom room = BeanUtils.copy(source, TeamRoom.class);
        resetTimes(room, oldStart, oldDuration, start);
        room.setResourceId(roomId);
        room.setName(businessDate + " " + source.getName().substring(0, Math.min(source.getName().length(), 22))
                + " " + roomId.substring(0, 6));
        room.setStartTime(start);
        room.setEndTime(end);
        room.setLocalSceneId(null);
        room.setCopyedTopoId("");
        room.setReplaySceneId(null);
        room.setSceneIdRecord(null);
        room.setAreaPath(null);
        room.setTimeLineConfig("[]");
        room.setEnableRecording(false);
        teamRoomMapper.insert(room);
        demoDataMapper.insertRoom(roomId, sourceId, businessDate, JsonUtil.toJsonString(digest));
        for (Mission item : missions) {
            Mission copy = BeanUtils.copy(item, Mission.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(ids.get(item.getResourceId()));
            copy.setRoomId(roomId);
            missionMapper.insert(copy);
        }
        for (CrRole item : roles) {
            CrRole copy = BeanUtils.copy(item, CrRole.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(ids.get(item.getResourceId()));
            copy.setRoomId(roomId);
            copy.setParentResourceId(ids.getOrDefault(item.getParentResourceId(), item.getParentResourceId()));
            List<MissionStageScheme> stages = copy.getMissionStageSchemes();
            for (MissionStageScheme stage : stages) {
                stage.setMissions(stage.getMissions().stream().map(ids::get).collect(Collectors.toList()));
            }
            copy.setMissionStageSchemes(stages);
            crRoleMapper.insert(copy);
        }
        for (CrTask item : tasks) {
            CrTask copy = BeanUtils.copy(item, CrTask.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(ids.get(item.getResourceId()));
            copy.setRoomId(roomId);
            copy.setRoleId(ids.get(item.getRoleId()));
            copy.setMissionId(ids.get(item.getMissionId()));
            copy.setReleaseTime(copyTime(item.getReleaseTime(), oldStart, oldDuration, start));
            copy.setTaskStartTime(copyTime(item.getTaskStartTime(), oldStart, oldDuration, start));
            copy.setLastSubmitTime(copyTime(item.getLastSubmitTime(), oldStart, oldDuration, start));
            crTaskMapper.insert(copy);
        }
        for (CrTaskSubmit item : submissions) {
            CrTaskSubmit copy = BeanUtils.copy(item, CrTaskSubmit.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(IDGeneratorUtil.getID());
            copy.setRoomId(roomId);
            copy.setTaskId(ids.get(item.getTaskId()));
            copy.setEvaluateTime(copyTime(item.getEvaluateTime(), oldStart, oldDuration, start));
            crTaskSubmitMapper.insert(copy);
        }
        for (CrTaskLog item : logs) {
            CrTaskLog copy = BeanUtils.copy(item, CrTaskLog.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(IDGeneratorUtil.getID());
            copy.setRoomId(roomId);
            copy.setTaskId(ids.get(item.getTaskId()));
            copy.setMissionId(ids.get(item.getMissionId()));
            crTaskLogMapper.insert(copy);
        }
        for (CrScore item : scores) {
            CrScore copy = BeanUtils.copy(item, CrScore.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(IDGeneratorUtil.getID());
            copy.setRoomId(roomId);
            crScoreMapper.insert(copy);
        }
        TrainRecord record = BeanUtils.copy(sourceRecord, TrainRecord.class);
        record.setId(null);
        record.setRoomId(roomId);
        record.setRoomName(room.getName());
        record.setStartTime(start);
        record.setEndTime(end);
        record.setExtra(null);
        trainRecordMapper.insert(record);
        List<CourseLessonUserRelation> relations = courseLessonUserRelationMapper.selectList(
                new QueryWrapper<CourseLessonUserRelation>().eq("lesson_id", sourceId));
        for (CourseLessonUserRelation item : relations) {
            CourseLessonUserRelation copy = BeanUtils.copy(item, CourseLessonUserRelation.class);
            resetTimes(copy, oldStart, oldDuration, start);
            copy.setResourceId(IDGeneratorUtil.getID());
            copy.setLessonId(roomId);
            courseLessonUserRelationMapper.insert(copy);
        }
        return true;
    }

    private void resetTimes(BaseTable record, Date oldStart, long oldDuration, Date start) {
        record.setId(null);
        record.setCreateTime(record.getCreateTime() == null ? start
                : copyTime(record.getCreateTime(), oldStart, oldDuration, start));
        record.setUpdateTime(record.getUpdateTime() == null ? new Date(start.getTime() + 7_200_000L)
                : copyTime(record.getUpdateTime(), oldStart, oldDuration, start));
    }

    private Date copyTime(Date value, Date oldStart, long oldDuration, Date start) {
        if (value == null) {
            return null;
        }
        double fraction = Math.max(0, Math.min(1, (double) (value.getTime() - oldStart.getTime()) / oldDuration));
        return new Date(start.getTime() + Math.round(fraction * 7_200_000L));
    }

}
