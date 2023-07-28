package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.common.context.role.CrRoleSimple;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class TaskMatrix {
    @ApiModelProperty(value = "成功数")
    private Integer successCount;

    @ApiModelProperty(value = "失败数")
    private Integer failCount;

    @ApiModelProperty(value = "驳回数")
    private Integer rejectCount;

    @ApiModelProperty(value = "总数")
    private Integer allCount;

    @ApiModelProperty(value = "完成数")
    private Integer finishCount;

    @ApiModelProperty(value = "得分")
    private Integer score;


    @ApiModelProperty(value = "任务列表")
    private List<TaskStage> taskList;

    @ApiModelProperty(value = "训练时长")
    private String timeCost;

    @ApiModelProperty(value = "用户头像")
    private String logo;

    @ApiModelProperty(value = "综合得分")
    private Integer finalScore;

    public TaskMatrix() {
    }

    public TaskMatrix init(List<CrTask> tasks,Map<String, Mission> missionMap) {
        this.taskList = BeanUtils.deepCopyList(tasks,TaskStage.class);
        this.allCount = tasks==null?0:tasks.size();
        this.successCount = tasks==null?0:(int)tasks.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).count();
        this.failCount = tasks==null?0:(int)tasks.stream().filter(a->CrTask.TaskStatus.FAIL.getCode().equals(a.getStatus())).count();
        this.rejectCount =tasks==null?0: (int)tasks.stream().filter(a->CrTask.TaskStatus.REJECT.getCode().equals(a.getStatus())).count();
        this.finishCount =successCount+failCount;
        this.score =tasks==null?0:tasks.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).map(CrTask::getScore).mapToInt(Integer::intValue).sum();
        for (TaskStage task : taskList) {
            Mission mission = missionMap.getOrDefault(task.getMissionId(), new Mission());
            task.setName(mission.getTitle());
            task.setDescription(mission.getDescription());
            //题解处理
            if (Boolean.TRUE.equals(mission.getShowWriteup())) {
                task.setWriteUp(missionMap.getOrDefault(task.getMissionId(),new Mission()).getWriteUp());
                task.setShowWriteup(true);
            } else {
                task.setWriteUp("");
                task.setShowWriteup(false);
            }
        }
        this.logo = UserInfoContext.getUserContext().getAvatarUrl();
        return this;
    }
    public void initTaskListStage(CrRole crRole,String code) {
        if (crRole==null) {
            return;
        }
        CrRoleSimple crRoleSimple = new CrRoleSimple(crRole);
        Map<String, String> stageMap = crRoleSimple.getMissionIdStageMap();
        for (TaskStage taskStage : taskList) {
            taskStage.setStage(stageMap.get(taskStage.getMissionId()));
        }
        //复盘排序规则
        if (SORT.REPLAY.getDesc().equals(code)) {
            List<TaskStage> submit = taskList.stream().filter(TaskStage::isSubmitted).sorted(Comparator.comparing(CrTask::getLastSubmitTime,Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());
            List<TaskStage> notSubmit = taskList.stream().filter(a->!a.isSubmitted()).sorted(Comparator.comparing(CrTask::getCreateTime,Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());
            submit.addAll(notSubmit);
            taskList = submit;
        } else {//任务矩阵排序规则
            List<TaskStage> notSubmit = taskList.stream().filter(a->!a.isSubmitted()).sorted(Comparator.comparing(CrTask::getCreateTime,Comparator.nullsFirst(Date::compareTo)).reversed()).collect(Collectors.toList());
            List<TaskStage> submit = taskList.stream().filter(TaskStage::isSubmitted).sorted(Comparator.comparing(CrTask::getLastSubmitTime,Comparator.nullsLast(Date::compareTo)).reversed()).collect(Collectors.toList());
            notSubmit.addAll(submit);
            taskList = notSubmit;
        }

    }
    public enum SORT implements GenericEnum {
        /***/
        MATRIX(1,  "matrix"),
        REPLAY(2,  "replay");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;



        @Override
        public String getDesc() {
            return desc;
        }

        SORT(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }
}
