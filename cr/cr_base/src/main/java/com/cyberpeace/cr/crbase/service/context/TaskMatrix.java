package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private List<CrTask> taskList;

    @ApiModelProperty(value = "训练时长")
    private String timeCost;

    public TaskMatrix() {
    }

    public TaskMatrix(List<CrTask> taskList) {
        this.taskList = taskList;
        this.allCount = taskList==null?0:taskList.size();
        this.successCount = taskList==null?0:(int)taskList.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).count();
        this.failCount = taskList==null?0:(int)taskList.stream().filter(a->CrTask.TaskStatus.FAIL.getCode().equals(a.getStatus())).count();
        this.rejectCount =taskList==null?0: (int)taskList.stream().filter(a->CrTask.TaskStatus.REJECT.getCode().equals(a.getStatus())).count();
        this.finishCount =successCount+failCount;
        this.score =taskList==null?0:taskList.stream().filter(a->CrTask.TaskStatus.SUCCESS.getCode().equals(a.getStatus())).map(CrTask::getScore).mapToInt(Integer::intValue).sum();
    }
}
