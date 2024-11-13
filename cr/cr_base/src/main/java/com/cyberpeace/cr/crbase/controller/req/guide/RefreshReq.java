package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 刷新请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class RefreshReq extends ScreenGuideHistoryReq {

    /**
     * 事件名称
     */
    @ApiModelProperty("开始时间")
	private String eventName;

}
