package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class SaveGroupInfoReq implements Serializable {

	@ApiModelProperty("课堂编号")
	private String classroomId;

	@ApiModelProperty("分组模板名称")
	@NotBlank
	private String name;

	@ApiModelProperty("分组信息")
	@NotEmpty
	private List<GroupInfoBo> data;

}
