package com.cyberpeace.cs.controller.res;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpirationCheckRes {

	@ApiModelProperty(value = "过期日，过期当日的0点")
	private Date expirationDate;

    @ApiModelProperty(value = "是否过期")
	private Boolean expire;
}
