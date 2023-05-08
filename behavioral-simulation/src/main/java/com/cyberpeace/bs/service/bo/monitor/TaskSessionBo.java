package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 任务会话数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class TaskSessionBo implements Serializable {

    /**
     * 横轴时间，间隔半小时
     */
    @ApiModelProperty(value = "横轴时间，间隔半小时")
    private List<String> xHalfHour = Lists.newArrayList();

    /**
     * 纵轴会话数
     */
    @ApiModelProperty(value = "纵轴会话数")
    private List<Integer> ySession = Lists.newArrayList();
}
