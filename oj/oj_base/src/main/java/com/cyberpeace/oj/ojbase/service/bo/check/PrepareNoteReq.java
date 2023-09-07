package com.cyberpeace.oj.ojbase.service.bo.check;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PrepareNoteReq implements Serializable {

	@ApiModelProperty(value = "课堂id")
	private String classRoomId;

	@ApiModelProperty(value = "备课笔记")
	private String note;

}
