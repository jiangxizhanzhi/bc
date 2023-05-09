package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 活跃度数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class ActivityBo implements Serializable {

    /**
     * 横轴小时
     */
    @ApiModelProperty(value = "横轴小时")
    private List<String> xHour = Lists.newArrayList();

    /**
     * 纵轴步骤数
     */
    @ApiModelProperty(value = "纵轴步骤数")
    private List<Integer> yStep = Lists.newArrayList();
}
