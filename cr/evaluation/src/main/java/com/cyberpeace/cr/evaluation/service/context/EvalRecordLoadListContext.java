package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordLoadListContext {
    @ApiModelProperty(value = "数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3" ,required = false)
    private Integer type;

    @ApiModelProperty(value = "载荷执行记录id",required = false)
    private String loadExecutionId;

    @ApiModelProperty(value = "载荷执行记录名",required = false)
    private String loadExecutionName;

    @ApiModelProperty(value = "研判结果(0:未研判 1:成功 2:失败)",required = false)
    private Integer judgmentStatus;

    @ApiModelProperty(value = "值",required = false)
    private Integer value;

    public enum JudgmentResult {

        UNJUDGED(0),

        SUCCESS(1),

        FAILURE(2);

        private final int code;

        JudgmentResult(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
