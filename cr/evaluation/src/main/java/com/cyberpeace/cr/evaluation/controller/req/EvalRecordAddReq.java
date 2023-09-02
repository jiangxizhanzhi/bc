package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EvalRecordAddReq implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty(value = "靶场测评实例ID", required = true)
    private String taskId;

    @ApiModelProperty(value = "靶场测评模板实例ID", required = true)
    private String templateId;

    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    @ApiModelProperty(value = "指标分数", required = true)
    @NotNull
    private Integer indexScore;

    @ApiModelProperty(value = "最终分数")
    private Integer finalScore;

    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）")
    private Date startTime;

    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）")
    private Date endTime;

    @ApiModelProperty(value = "全量三级指标项", required = true)
    private List<EvalRecordIndexContext> indexes;

    @ApiModelProperty(value = "评分等级")
    private List<String> gradeList;

    public void validate() {
        if (name != null) {
            CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
        if (StringUtils.isNotEmpty(description)) {
            CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
        }
        int lenMin = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.zero"));
        int categoryMax = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.score.max"));
        CrCommonValidator.validateRange(indexScore, lenMin, categoryMax, CrBusinessErrorCode.MAX_SCORE_10000);
    }
}
