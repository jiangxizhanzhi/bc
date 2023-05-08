package com.cyberpeace.bs.service.bo.behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 行为简要信息
 * 行为列表json字符串只存储行为的id和访问时间
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class BehaviorSimple implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "编号")
    private String id;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "访问时间")
    private LocalTime startTime;
}
