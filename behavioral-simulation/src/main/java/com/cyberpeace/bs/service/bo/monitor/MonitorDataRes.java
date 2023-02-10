package com.cyberpeace.bs.service.bo.monitor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.constant.BsConstants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MonitorDataRes implements Serializable {

	@ApiModelProperty(value = "仪表详情")
	private MonitorSimulatorDetail detail;

	@ApiModelProperty(value = "执行情况")
	private ExecutedBo executionState;

	@ApiModelProperty(value = "时间横轴（分钟），用于带宽图和连接数图")
	private List<String> xTimeMinutes = Lists.newArrayList();

	@ApiModelProperty(value = "连接数统计")
	private ConnectionBo connection;

	@ApiModelProperty(value = "带宽和报文统计")
	private BandwidthAndPacket bandwidthAndPacket;

	@ApiModelProperty(value = "活跃度")
	private ActivityBo activity;

	@ApiModelProperty(value = "仿真任务会话数")
	private TaskSessionBo session;

	public void init(long timestamp, int hours) {
		this.bandwidthAndPacket = new BandwidthAndPacket();
		this.connection = new ConnectionBo();
		this.activity = new ActivityBo();
		this.session = new TaskSessionBo();

		LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8))
				.toLocalDateTime();
		LocalTime start = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute());
		for (int i = 0; i < hours * BsConstants.MINUTE_PER_HOUR; i++) {
			xTimeMinutes.add(start.plusMinutes(i).toString());
		}

		for (int i = 0; i < hours; i++) {
			activity.getXHour().add(start.plusHours(i + 1L).getHour() + BsConstants.HOUR);
		}
		LocalTime halfHourStart;
		if (start.getMinute() < 30) {
			halfHourStart = LocalTime.of(start.getHour(), 30);
		} else {
			halfHourStart = LocalTime.of(start.getHour() + 1, 0);
		}

		for (int i = 0; i < hours * 2; i++) {
			session.getXHalfHour().add(halfHourStart.plusMinutes(30L * i).toString());
		}
	}

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

@Data
class ExecutedBo {

	@ApiModelProperty(value = "未执行数")
	private Integer notExecuted;

	@ApiModelProperty(value = "已执行数")
	private Integer executed;
}
