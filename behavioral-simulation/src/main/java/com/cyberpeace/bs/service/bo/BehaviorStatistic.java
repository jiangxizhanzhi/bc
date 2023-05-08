package com.cyberpeace.bs.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 行为统计
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class BehaviorStatistic implements Serializable {

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 结束时间
     */
    private LocalTime endTime;

    /**
     * 行为计数
     */
    private Integer behaviorCount = 0;
}
