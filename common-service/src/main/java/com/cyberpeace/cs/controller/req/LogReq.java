package com.cyberpeace.cs.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/14 16:57
 * @desc :
 **/
@Data
public class LogReq extends PageReqBase implements Serializable {

    @ApiModelProperty("模块")
    Integer module;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    Date end;
}
