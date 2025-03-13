package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EvalRecordLoadListContext {
    @ApiModelProperty(value = "数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3" ,required = false)
    private Integer type;

    @ApiModelProperty(value = "值")
    private Integer value;

    @ApiModelProperty(value = "关联的五级指标载荷设置")
    private List<EvalRecordLoadFifthIndexContext> loadIndexes = new ArrayList<>(); // 初始化空集合

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
