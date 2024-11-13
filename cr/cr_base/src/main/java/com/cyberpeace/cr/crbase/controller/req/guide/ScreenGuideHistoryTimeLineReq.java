package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.cr.crbase.service.context.guide.ScreenBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 屏幕导调历史时间线请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ScreenGuideHistoryTimeLineReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("训练id")
	@NotBlank
	private String roomId;

	/**
	 * 业务类型
	 */
	@ApiModelProperty("训练类型(3：团队，4：红蓝)")
	@NotNull
	private Integer businessType;

	/**
	 * 事件子类型列表
	 */
	@ApiModelProperty("事件类型")
	private List<Integer> eventSubTypeList;

	/**
	 * 攻击屏幕Bo
	 */
	@ApiModelProperty("攻击筛选条件")
	private ScreenBo attackScreenBo;

	/**
	 * 防御屏幕Bo
	 */
	@ApiModelProperty("防御筛选条件")
	private ScreenBo defenseScreenBo;

	/**
	 * 阻塞屏幕Bo
	 */
	@ApiModelProperty("阻断筛选条件")
	private ScreenBo blockingScreenBo;

	/**
	 * 归属感屏幕Bo
	 */
	@ApiModelProperty("归属筛选条件")
	private ScreenBo belongingScreenBo;
}
