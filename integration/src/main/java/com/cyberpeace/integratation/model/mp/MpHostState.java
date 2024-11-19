package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中台主机状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpHostState implements Serializable {

	/**
	 * CPU核数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("CPU核数")
	private Integer cpuCount;

	/**
	 * CPU使用率
	 *
	 * @see Double
	 */
	@ApiModelProperty("CPU使用率")
	private Double cpuPercent;

	/**
	 * 磁盘使用率
	 *
	 * @see Double
	 */
	@ApiModelProperty("磁盘使用率")
	private Double diskPercent;

	/**
	 * 磁盘空间(GB)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("磁盘空间(GB)")
	private Integer diskSpace;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	/**
	 * 空闲内存(MB)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("空闲内存(MB)")
	private Integer memoryFree;

	/**
	 * 内存使用率
	 *
	 * @see Double
	 */
	@ApiModelProperty("内存使用率")
	private Double memoryPercent;

	/**
	 * 总内存(MB)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("总内存(MB)")
	private Integer memoryTotal;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("时间")
	private Date time;

}
