package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class SceneBO extends BaseSceneBO implements BaseSceneEventModel {
    @ApiModelProperty(value = "事件")
    private Integer event;
    @ApiModelProperty(value = "事件状态")
    private Integer eventStatus;
    @ApiModelProperty(value = "事件进程")
    private String process;
    @ApiModelProperty(value = "事件进程状态")
    private Integer processStatus;
    @ApiModelProperty(value = "事件进程描述信息")
    private String processMessage;
    @ApiModelProperty(value = "事件进度百分比")
    private Integer progressPercentage;
    @ApiModelProperty(value = "创建消耗时间(秒)")
    private Long createConsumeSeconds;
    @ApiModelProperty(value = "当前场景的阶段")
    private String stage;
    @ApiModelProperty(value = "当前场景的阶段名称")
    private String stageName;
    @ApiModelProperty(value = "当前阶段执行的百分比")
    private Integer stagePercentage;
    @ApiModelProperty(value = "阶段列表")
    private List<SceneStageRecord> stageList;

    @Override
    public String genTarget() {
        return null;
    }

    @Getter
    @Setter
    public static class SceneStageRecord extends SceneStage {

        private String stageName;

        private volatile int stagePercentage;

        public SceneStageRecord() {
            super(StringUtils.EMPTY, StringUtils.EMPTY);
        }

        public SceneStageRecord(SceneStage stage) {
            super(stage.getStage(), stage.getIcon());
            this.stagePercentage = 0;
            this.stageName = stage.getStageName();
        }

        @Override
        public String getStageName() {
            return this.stageName;
        }
    }
}
