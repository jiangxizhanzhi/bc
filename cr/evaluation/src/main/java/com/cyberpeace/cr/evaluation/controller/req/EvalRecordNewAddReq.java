package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估记录新增功能新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalRecordNewAddReq implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "靶场测评实例ID", required = true)
    private String taskId;

    /**
     * 系统id
     */
    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）", required = true)
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）", required = true)
    private Date endTime;

    /**
     * 指标
     */
    @ApiModelProperty(value = "全量体系指标项", required = true)
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
