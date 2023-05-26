package com.cyberpeace.cr.crbase.service.context.score;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ScoreTrend extends NameScore{

    @ApiModelProperty("得分时间")
    private Date scoreTime;
}
