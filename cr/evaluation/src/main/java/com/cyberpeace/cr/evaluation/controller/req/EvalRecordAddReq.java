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
 * 评估记录新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordAddReq implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 任务id
     *
     * @see String
     */
    @ApiModelProperty(value = "靶场测评实例ID", required = true)
    private String taskId;

    /**
     * 系统id
     *
     * @see String
     */
    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）", required = true)
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）", required = true)
    private Date endTime;

    /**
     * 指标
     *
     * @see List<EvalRecordIndexContext>
     */
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
