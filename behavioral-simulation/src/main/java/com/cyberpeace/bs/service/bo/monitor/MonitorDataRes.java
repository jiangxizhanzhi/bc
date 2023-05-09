package com.cyberpeace.bs.service.bo.monitor;

import com.cyberpeace.bs.constant.BsConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 监控数据结果
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class MonitorDataRes implements Serializable {

    /**
     * 仪表详情
     */
    @ApiModelProperty(value = "仪表详情")
    private MonitorSimulatorDetail detail;

    /**
     * 执行情况
     */
    @ApiModelProperty(value = "执行情况")
    private ExecutedBo executionState;

    /**
     * 时间横轴（分钟），用于带宽图和连接数图
     */
    @ApiModelProperty(value = "时间横轴（分钟），用于带宽图和连接数图")
    private List<String> xTimeMinutes = Lists.newArrayList();

    /**
     * 连接数统计
     */
    @ApiModelProperty(value = "连接数统计")
    private ConnectionBo connection;

    /**
     * 带宽和报文统计
     */
    @ApiModelProperty(value = "带宽和报文统计")
    private BandwidthAndPacket bandwidthAndPacket;

    /**
     * 活跃度
     */
    @ApiModelProperty(value = "活跃度")
    private ActivityBo activity;

    /**
     * 仿真任务会话数
     */
    @ApiModelProperty(value = "仿真任务会话数")
    private TaskSessionBo session;

    /**
     * 初始化
     * 根据当前和统计时长，初始化x轴数据
     *
     * @param timestamp 时间戳
     * @param hours     小时
     */
    public void init(long timestamp, int hours) {
        this.bandwidthAndPacket = new BandwidthAndPacket();
        this.connection = new ConnectionBo();
        this.activity = new ActivityBo();
        this.session = new TaskSessionBo();

        // 从传入时间往前推8 个小时，作为统计起点
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8))
                .toLocalDateTime();
        // 获取统计起点的小时和分钟值
        LocalTime start = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute());
        // 按分钟统计的数据固定展示半个小时
        for (int i = hours * BsConstants.MINUTE_PER_HOUR - BsConstants.MINUTE_HALF_HOUR; i < hours
                * BsConstants.MINUTE_PER_HOUR; i++) {
            // 填充按分钟统计的x轴，一分钟一个
            xTimeMinutes.add(start.plusMinutes(i).toString());
        }

        // 活跃度x轴数据
        for (int i = 0; i < hours; i++) {
            activity.getXHour().add(start.plusHours(i + 1L).getHour() + BsConstants.HOUR);
        }
        // 半小时一个数据时，分情况设置统计起点
        LocalTime halfHourStart;
        if (start.getMinute() < BsConstants.MINUTE_HALF_HOUR) {
            halfHourStart = LocalTime.of(start.getHour(), BsConstants.MINUTE_HALF_HOUR);
        } else {
            halfHourStart = LocalTime.of(start.getHour() + 1, 0);
        }

        for (int i = 0; i < hours * 2; i++) {
            session.getXHalfHour().add(
                    halfHourStart.plusMinutes((long) BsConstants.MINUTE_HALF_HOUR * i).toString());
        }
    }

    /**
     * 计算任务执行情况
     *
     * @param total    任务总数
     * @param exeCount 已执行数
     */
    public void calculateExecuted(Integer total, Integer exeCount) {
        ExecutedBo bo = new ExecutedBo();
        if (exeCount > total) {
            exeCount = total;
        }
        bo.setNotExecuted(total - exeCount);
        bo.setExecuted(exeCount);
        this.setExecutionState(bo);
    }
}

/**
 * 执行情况
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class ExecutedBo {

    /**
     * 未执行数
     */
    @ApiModelProperty(value = "未执行数")
    private Integer notExecuted;

    /**
     * 已执行数
     */
    @ApiModelProperty(value = "已执行数")
    private Integer executed;
}
