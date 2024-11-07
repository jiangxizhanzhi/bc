package com.cyberpeace.cr.crbase.service.context.score;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分数趋势
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ScoreTrend extends NameScore{

    /**
     * 得分时间
     */
    @ApiModelProperty("得分时间")
    private String scoreTime;
}
