package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）", required = true)
    private Date startTime;

    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）", required = true)
    private Date endTime;

    @ApiModelProperty(value = "全量三级指标项", required = true)
    private List<EvalRecordIndexContext> indexes;

    public void validate() {
        if (name != null) {
            CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
        if (StringUtils.isNotEmpty(description)) {
            CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
        }
    }
}
