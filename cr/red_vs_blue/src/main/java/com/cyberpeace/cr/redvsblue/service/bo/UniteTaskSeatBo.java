package com.cyberpeace.cr.redvsblue.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniteTaskSeatBo {

    @ApiModelProperty(value = "任务关联ID", name = "taskId")
    private String taskId;

    /**
     * 席位ID
     */
    @ApiModelProperty(value = "席位ID", name = "seatId")
    private String seatId;
    /**
     * 席位名称
     */
    @ApiModelProperty(value = "席位名称", name = "seatName")
    private String seatName;

    /**
     * 席位人员ID
     */
    @ApiModelProperty(value = "席位人员ID", name = "seatUserId")
    private String seatUserId;
    /**
     * 席位人员姓名
     */
    @ApiModelProperty(value = "席位人员姓名", name = "seatUserName")
    private String seatUserName;

}
