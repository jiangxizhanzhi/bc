package com.cyberpeace.iov.index.service.bo.student;

import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawPacket {

	@ApiModelProperty(value = "")
	JSONArray can;

	JSONArray wifi;

	@ApiModelProperty(value = "蓝牙")
	JSONArray bluetooth;
}
