/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码定义了一个名为 `TaskMatrix` 的类，用于管理任务矩阵的相关信息。它主要用于统计任务的状态（如成功、失败、拒绝等），以及任务的详细信息，并提供了一些初始化和排序的功能。 
 * #### 主要功能模块 
 * 1. **属性定义** 
 * - `successCount`, `failCount`, `rejectCount`, `allCount`, `finishCount`, `score`: 分别表示任务的成功数、失败数、拒绝数、总数、完成数和总得分。 
 * - `taskList`: 存储任务列表，每个任务由 `TaskStage` 对象表示。 
 * - `timeCost`: 表示任务的训练时长。 
 * - `logo`: 用户头像链接。 
 * - `finalScore`: 综合得分。 
 * 2. **构造方法** 
 * - 提供默认无参构造方法。 
 * 3. **初始化方法** 
 * - `init(List<CrTask> tasks, Map<String, Mission> missionMap)`: 初始化任务矩阵的基本信息，包括任务状态统计、任务名称和描述的填充，以及是否显示题解等。 
 * 4. **任务列表排序** 
 * - `initTaskListStage(CrRole crRole, String code)`: 根据角色信息和排序规则对任务列表进行排序。支持两种排序方式： 
 * - `MATRIX`: 按任务创建时间和提交时间排序。 
 * - `REPLAY`: 按任务提交时间和创建时间排序。 
 * 5. **枚举类** 
 * - `SORT`: 定义了两种排序规则：`MATRIX` 和 `REPLAY`，并实现了 `GenericEnum` 接口。 
 * #### 代码特点 
 * - 使用了 Lombok 注解 (`@Getter`, `@Setter`) 来简化 getter 和 setter 方法的生成。 
 * - 利用了 Java 8 的流式操作（如 `stream()`）来进行数据处理和统计。 
 * - 通过 `BeanUtils.deepCopyList` 实现了对象的深拷贝。 
 * - 支持国际化注解 `@ApiModelProperty`，便于 API 文档生成。 
 * #### 总结 
 * 该类的设计目的是为了管理和展示任务矩阵的信息，提供了丰富的统计和排序功能，适合用于任务管理系统中。代码结构清晰，逻辑明确，利用了现代 Java 特性提高了开发效率。 
**/
package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleSimple;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务矩阵
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskMatrix {
    /**
     * 成功计数
     */
    @ApiModelProperty(value = "成功数")
    private Integer successCount;

    /**
     * 失败计数
     */
    @ApiModelProperty(value = "失败数")
    private Integer failCount;

    /**
     * 拒绝计数
     */
    @ApiModelProperty(value = "驳回数")
    private Integer rejectCount;

    /**
     * 都计数
     */
    @ApiModelProperty(value = "总数")
    private Integer allCount;

    /**
     * 完成计数
     */
    @ApiModelProperty(value = "完成数")
    private Integer finishCount;

    /**
     * 得分
     */
    @ApiModelProperty(value = "得分")
    private Integer score;


    /**
     * 任务列表
     */
    @ApiModelProperty(value = "任务列表")
    private List<TaskStage> taskList;

    /**
     * 时间成本
     */
    @ApiModelProperty(value = "训练时长")
    private String timeCost;

    /**
     * 图标
     */
    @ApiModelProperty(value = "用户头像")
    private String logo;

    /**
     * 最终比分
     */
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
