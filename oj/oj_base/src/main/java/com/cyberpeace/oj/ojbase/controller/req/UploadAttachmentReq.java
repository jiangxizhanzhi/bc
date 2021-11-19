package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class UploadAttachmentReq implements Serializable {

	@ApiModelProperty("是否将数据入库 true入库 false不入库，仅上传")
	private Boolean insert;

	@ApiModelProperty("文件类型 1=附件 2=视频 3=附件")
	@NotNull
	private Integer type;

	public Boolean getInsert() {
		return null == insert ? CommonConstant.FALSE : insert;
	}

}
