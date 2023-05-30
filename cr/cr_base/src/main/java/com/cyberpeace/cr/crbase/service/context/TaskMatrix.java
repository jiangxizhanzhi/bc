package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleSimple;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public TaskMatrix() {
    }

    public TaskMatrix init(List<CrTask> tasks,Map<Integer, Mission> missionMap) {
        this.taskList = BeanUtils.deepCopyList(tasks,TaskStage.class);
        this.allCount = tasks==null?0:tasks.size();
        this.successCount = tasks==null?0:(int)tasks.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).count();
        this.failCount = tasks==null?0:(int)tasks.stream().filter(a->CrTask.TaskStatus.FAIL.getCode().equals(a.getStatus())).count();
        this.rejectCount =tasks==null?0: (int)tasks.stream().filter(a->CrTask.TaskStatus.REJECT.getCode().equals(a.getStatus())).count();
        this.finishCount =successCount+failCount;
        this.score =tasks==null?0:tasks.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).map(CrTask::getScore).mapToInt(Integer::intValue).sum();
        for (TaskStage task : taskList) {
            task.setName(missionMap.getOrDefault(task.getMissionId(),new Mission()).getTitle());
        }
        return this;
    }
    public void initTaskListStage(CrRole crRole) {
        if (crRole==null) {
            return;
        }
        CrRoleSimple crRoleSimple = new CrRoleSimple(crRole);
        Map<String, String> stageMap = crRoleSimple.getMissionIdStageMap();
        for (TaskStage taskStage : taskList) {
            taskStage.setStage(stageMap.get(taskStage.getMissionId()));
        }
        List<TaskStage> submit = taskList.stream().filter(TaskStage::isSubmitted).sorted(Comparator.comparing(CrTask::getLastSubmitTime,Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());
        List<TaskStage> notSubmit = taskList.stream().filter(a->!a.isSubmitted()).sorted(Comparator.comparing(CrTask::getCreateTime,Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());
        submit.addAll(notSubmit);
        taskList = submit;
    }
}
